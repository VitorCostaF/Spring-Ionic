package br.com.studies.springboot.springionic.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.studies.springboot.springionic.domain.ItemPedido;
import br.com.studies.springboot.springionic.domain.PagamentoComBoleto;
import br.com.studies.springboot.springionic.domain.Pedido;
import br.com.studies.springboot.springionic.domain.enums.EstadoPagamento;
import br.com.studies.springboot.springionic.repositories.ItemPedidoRepository;
import br.com.studies.springboot.springionic.repositories.PagamentoRepository;
import br.com.studies.springboot.springionic.repositories.PedidoRepository;
import br.com.studies.springboot.springionic.services.exception.DomainObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	PagamentoRepository pagamentoRepository;
	
	@Autowired
	ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	BoletoService boletoService;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	EmailService emailService;

	public Pedido buscarPedidoPorId(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		return pedido.orElseThrow(() -> new DomainObjectNotFoundException(
				"Objeto não encotrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido salvarPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.buscarClientePorId(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamento = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamento, pedido.getInstante());
		}
		
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for(ItemPedido itemPedido : pedido.getItens()) {
			itemPedido.setDesconto(0.0);
			itemPedido.setProduto(produtoService.buscarProdutoPorId(itemPedido.getProduto().getId()));
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			itemPedido.setPedido(pedido);
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		emailService.sendOrderConfirmationHtmlEmail(pedido);
		return pedido;
	}

}
