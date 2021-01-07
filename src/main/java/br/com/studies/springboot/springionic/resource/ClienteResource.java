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

import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.dto.ClienteDTO;
import br.com.studies.springboot.springionic.dto.ClienteNewDTO;
import br.com.studies.springboot.springionic.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		return ResponseEntity.ok().body(clienteService.buscarClientePorId(id));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscarTodasClientes() {
		List<Cliente> listaClientes = clienteService.buscarTodosClientes();
		List<ClienteDTO> listClienteDTOs = listaClientes.stream().map(cliente -> new ClienteDTO(cliente)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listClienteDTOs);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/page")
	public ResponseEntity<Page<ClienteDTO>> buscarClientesPorPagina(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> pageClientes = clienteService.buscarPageCliente(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> pageClienteDTOs = pageClientes.map(cliente -> new ClienteDTO(cliente));
		return ResponseEntity.ok().body(pageClienteDTOs);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insertCliente(@Valid @RequestBody ClienteNewDTO clienteDTO) {
		Cliente cliente = clienteService.fromClienteDTO(clienteDTO);
		clienteService.salvarCliente(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<Void> atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		Cliente cliente = clienteService.fromClienteDTO(clienteDTO);
		cliente.setId(id);
		clienteService.atualizarCliente(cliente);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {

		clienteService.deletarCliente(id);
		return ResponseEntity.noContent().build();

	}
	
}

