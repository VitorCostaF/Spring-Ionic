package br.com.studies.springboot.springionic.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.dto.CategoriaDTO;
import br.com.studies.springboot.springionic.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Categoria> buscarCategoria(@PathVariable Integer id) {
		Categoria categoria = categoriaService.buscar(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodasCategorias() {
		List<Categoria> listaCategorias = categoriaService.buscarTodasCategorias();
		List<CategoriaDTO> listCategoriaDTOs = listaCategorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listCategoriaDTOs);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> buscarCategoriasPorPagina(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Categoria> pageCategorias = categoriaService.buscarPageCategoria(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> pageCategoriaDTOs = pageCategorias.map(categoria -> new CategoriaDTO(categoria));
		return ResponseEntity.ok().body(pageCategoriaDTOs);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insertCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromCategoriaDTO(categoriaDTO);
		categoriaService.salvarCategoria(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> atualizarCategoria(@RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id) {
		Categoria categoria = categoriaService.fromCategoriaDTO(categoriaDTO);
		categoria.setId(id);
		categoriaService.atualizarCategoria(categoria);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id) {

		categoriaService.deletarCategoria(id);
		return ResponseEntity.noContent().build();

	}

	
}
