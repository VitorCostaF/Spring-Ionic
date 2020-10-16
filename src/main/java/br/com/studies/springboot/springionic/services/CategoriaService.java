package br.com.studies.springboot.springionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.repositories.CategoriaRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjectRepeatedException;
import br.com.studies.springboot.springionic.services.exception.DomainObjetctNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Categoria buscar(Integer id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new DomainObjetctNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria salvarCategoria(Categoria categoria) {
		
		if(categoria.getId() != null) {			
			if (categoriaRepository.findById(categoria.getId()).isPresent()) {
				throw new DomainObjectRepeatedException(
						"Objeto já existe na base! Id: " + categoria.getId() + ", Tipo: " + Categoria.class.getName());
			}
		}

		categoria.setId(null);
		return categoriaRepository.save(categoria);

	}

}
