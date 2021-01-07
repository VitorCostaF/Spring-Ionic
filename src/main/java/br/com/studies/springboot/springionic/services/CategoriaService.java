package br.com.studies.springboot.springionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.dto.CategoriaDTO;
import br.com.studies.springboot.springionic.repositories.CategoriaRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjectRepeatedException;
import br.com.studies.springboot.springionic.services.exception.DomainDataIntegrityException;
import br.com.studies.springboot.springionic.services.exception.DomainObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new DomainObjectNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria salvarCategoria(Categoria categoria) {
		
		if (categoria.getId() != null) {
			if (categoriaRepository.findById(categoria.getId()).isPresent()) {
				throw new DomainObjectRepeatedException(
						"Objeto já existe na base! Id: " + categoria.getId() + ", Tipo: " + Categoria.class.getName());
			}
		}
		categoria.setId(null);
		return categoriaRepository.save(categoria);
	}

	public Categoria atualizarCategoria(Categoria categoria) {
		buscar(categoria.getId());
		return categoriaRepository.save(categoria);
	}

	public void deletarCategoria(Integer id) {
		buscar(id);
		try {
			categoriaRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DomainDataIntegrityException("Não é possível excluir categorias com produtos associados");
		}
		
	}
	
	public List<Categoria> buscarTodasCategorias() {
		return categoriaRepository.findAll();
	}
	
	public Page<Categoria> buscarPageCategoria(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}

	public Categoria fromCategoriaDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
}
