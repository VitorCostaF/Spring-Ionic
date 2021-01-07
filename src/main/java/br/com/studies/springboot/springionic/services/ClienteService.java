package br.com.studies.springboot.springionic.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Cidade;
import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.domain.Endereco;
import br.com.studies.springboot.springionic.domain.enums.TipoCliente;
import br.com.studies.springboot.springionic.dto.ClienteDTO;
import br.com.studies.springboot.springionic.dto.ClienteNewDTO;
import br.com.studies.springboot.springionic.repositories.ClienteRepository;
import br.com.studies.springboot.springionic.repositories.EnderecoRepository;
import br.com.studies.springboot.springionic.services.exception.DomainDataIntegrityException;
import br.com.studies.springboot.springionic.services.exception.DomainObjectNotFoundException;
import br.com.studies.springboot.springionic.services.exception.DomainObjectRepeatedException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente buscarClientePorId(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.orElseThrow(() -> new DomainObjectNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	public Cliente salvarCliente(Cliente cliente) {
		
		if (cliente.getId() != null) {
			if (clienteRepository.findById(cliente.getId()).isPresent()) {
				throw new DomainObjectRepeatedException(
						"Objeto já existe na base! Id: " + cliente.getId() + ", Tipo: " + Cliente.class.getName());
			}
		}
		cliente.setId(null);
		return clienteRepository.save(cliente);	
	}

	public List<Cliente> buscarTodosClientes() {
		return clienteRepository.findAll();
	}
	
	public Page<Cliente> buscarPageCliente(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);		
	}
	
	public Cliente atualizarCliente(Cliente cliente) {
		Cliente clienteBanco = buscarClientePorId(cliente.getId());
		atualizaCampos(clienteBanco, cliente);
		return clienteRepository.save(clienteBanco);
	}

	public void deletarCliente(Integer id) {
		buscarClientePorId(id);
		try {
			clienteRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DomainDataIntegrityException("Não é possível excluir clientes com pedidos relacionadas");
		}
		
	}
	
	public Cliente fromClienteDTO(ClienteDTO clienteDTO) {
		return new Cliente(clienteDTO.getId(), clienteDTO.getNome(), clienteDTO.getEmail(), null, null );
	}
	
	public Cliente fromClienteDTO(ClienteNewDTO clienteDTO) {
		Cliente cliente = new Cliente(null, clienteDTO.getNome(), clienteDTO.getEmail(), clienteDTO.getCpfOuCnpj() , TipoCliente.toEnum(clienteDTO.getTipo()));
		Cidade cidade = new Cidade(clienteDTO.getCidadeId(), null, null);
		Endereco endereco = new Endereco(null, clienteDTO.getLogradouro(), clienteDTO.getNumero(), clienteDTO.getComplemento(), clienteDTO.getBairro(), clienteDTO.getCep(), cliente, cidade);
		
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(clienteDTO.getTelefone1());
		
		if(clienteDTO.getTelefone2() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefone2());
		}
		
		if(clienteDTO.getTelefone3() != null) {
			cliente.getTelefones().add(clienteDTO.getTelefone3());
		}
		
		return cliente;
	}
	
	private void atualizaCampos(Cliente clienteBanco, Cliente cliente) {
		clienteBanco.setNome(cliente.getNome());
		clienteBanco.setEmail(cliente.getEmail());
	}
}


