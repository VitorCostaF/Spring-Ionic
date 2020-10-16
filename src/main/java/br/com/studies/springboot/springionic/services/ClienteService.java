package br.com.studies.springboot.springionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.repositories.ClienteRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjetctNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente buscarClientePorId(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new DomainObjetctNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

}
