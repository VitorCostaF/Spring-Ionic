package br.com.studies.springboot.springionic.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.domain.Pedido;
import br.com.studies.springboot.springionic.repositories.PedidoRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjetctNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;

	public Pedido buscarPedidoPorId(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new DomainObjetctNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

}
