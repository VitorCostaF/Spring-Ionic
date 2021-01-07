package br.com.studies.springboot.springionic.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.domain.Produto;
import br.com.studies.springboot.springionic.repositories.CategoriaRepository;
import br.com.studies.springboot.springionic.repositories.ProdutoRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CategoriaRepository categoriaRepository;

	public Produto buscarProdutoPorId(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		return produto.orElseThrow(() -> new DomainObjectNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}
	
	public Page<Produto> buscarProdutos(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return produtoRepository.searchProdutoCategoria(nome, categorias, pageRequest);
	}

}
