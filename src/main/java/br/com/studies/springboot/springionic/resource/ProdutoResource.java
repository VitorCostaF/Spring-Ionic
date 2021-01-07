package br.com.studies.springboot.springionic.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.studies.springboot.springionic.domain.Produto;
import br.com.studies.springboot.springionic.dto.ProdutoDTO;
import br.com.studies.springboot.springionic.resource.utils.URL;
import br.com.studies.springboot.springionic.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {
	
	@Autowired
	ProdutoService produtoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		return ResponseEntity.ok().body(produtoService.buscarProdutoPorId(id));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscarProdutos(
			@RequestParam(value = "nome", defaultValue = "") String nome, 
			@RequestParam(value = "categorias", defaultValue = "0") String categorias, 
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> pageProdutos = produtoService.buscarProdutos(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> pageProdutoDTOs = pageProdutos.map(produto -> new ProdutoDTO(produto));
		return ResponseEntity.ok().body(pageProdutoDTOs);
	}

}
