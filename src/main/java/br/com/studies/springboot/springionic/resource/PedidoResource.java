package br.com.studies.springboot.springionic.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.studies.springboot.springionic.domain.Pedido;
import br.com.studies.springboot.springionic.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	PedidoService pedidoService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		return ResponseEntity.ok().body(pedidoService.buscarPedidoPorId(id));
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insertPedido(@Valid @RequestBody Pedido pedido) {
		pedidoService.salvarPedido(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

}
