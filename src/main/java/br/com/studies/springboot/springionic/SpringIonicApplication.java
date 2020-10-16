package br.com.studies.springboot.springionic;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.studies.springboot.springionic.domain.Categoria;
import br.com.studies.springboot.springionic.domain.Cidade;
import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.domain.Endereco;
import br.com.studies.springboot.springionic.domain.Estado;
import br.com.studies.springboot.springionic.domain.ItemPedido;
import br.com.studies.springboot.springionic.domain.Pagamento;
import br.com.studies.springboot.springionic.domain.PagamentoComBoleto;
import br.com.studies.springboot.springionic.domain.PagamentoComCartao;
import br.com.studies.springboot.springionic.domain.Pedido;
import br.com.studies.springboot.springionic.domain.Produto;
import br.com.studies.springboot.springionic.domain.enums.EstadoPagamento;
import br.com.studies.springboot.springionic.domain.enums.TipoCliente;
import br.com.studies.springboot.springionic.repositories.CategoriaRepository;
import br.com.studies.springboot.springionic.repositories.CidadeRepository;
import br.com.studies.springboot.springionic.repositories.ClienteRepository;
import br.com.studies.springboot.springionic.repositories.EnderecoRepository;
import br.com.studies.springboot.springionic.repositories.EstadoRepository;
import br.com.studies.springboot.springionic.repositories.ItemPedidoRepository;
import br.com.studies.springboot.springionic.repositories.PagamentoRepository;
import br.com.studies.springboot.springionic.repositories.PedidoRepository;
import br.com.studies.springboot.springionic.repositories.ProdutoRepository;

@SpringBootApplication
public class SpringIonicApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	ProdutoRepository produtoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	PedidoRepository pedidoRepository;

	@Autowired
	PagamentoRepository pagamentoRepository;

	@Autowired
	ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringIonicApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria categoria1 = new Categoria(null, "Informatica");
		Categoria categoria2 = new Categoria(null, "Escritorio");

		Produto produto1 = new Produto(null, "Computador", 2000.00);
		Produto produto2 = new Produto(null, "Impressora", 800.00);
		Produto produto3 = new Produto(null, "Mouse", 80.00);

		categoria1.getProdutos().addAll(Arrays.asList(produto1, produto2, produto3));
		categoria2.getProdutos().addAll(Arrays.asList(produto2));

		produto1.getCategorias().addAll(Arrays.asList(categoria1));
		produto2.getCategorias().addAll(Arrays.asList(categoria1, categoria2));
		produto2.getCategorias().addAll(Arrays.asList(categoria1));

		categoriaRepository.saveAll(Arrays.asList(categoria1, categoria2));
		produtoRepository.saveAll(Arrays.asList(produto1, produto2, produto3));

		Estado estado1 = new Estado(null, "Minas Gerais");
		Estado estado2 = new Estado(null, "Sao Paulo");

		Cidade cidade1 = new Cidade(null, "Uberlandia", estado1);
		Cidade cidade2 = new Cidade(null, "Sao Paulo", estado2);
		Cidade cidade3 = new Cidade(null, "Uberlandia", estado2);

		estado1.getCidedes().addAll(Arrays.asList(cidade1));
		estado2.getCidedes().addAll(Arrays.asList(cidade2, cidade3));

		estadoRepository.saveAll(Arrays.asList(estado1, estado2));
		cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

		Cliente cliente1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "3678912377", TipoCliente.PESSOAFISICA);
		cliente1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		Endereco endereco1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", cliente1,
				cidade1);
		Endereco endereco2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cliente1,
				cidade2);

		cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

		clienteRepository.saveAll(Arrays.asList(cliente1));
		enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido pedido1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cliente1, endereco1);
		Pedido pedido2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cliente1, endereco2);

		Pagamento pagamento1 = new PagamentoComCartao(null, EstadoPagamento.QUITAOD, pedido1, 6);
		pedido1.setPagamento(pagamento1);

		Pagamento pagamento2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedido2,
				sdf.parse("20/10/2017 00:00"), null);
		pedido2.setPagamento(pagamento2);

		cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

		ItemPedido itemPedido1 = new ItemPedido(pedido1, produto1, 0.00, 1, 2000.00);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, produto3, 0.00, 2, 80.00);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, produto2, 100.00, 1, 800.00);

		pedido1.getItems().addAll(Arrays.asList(itemPedido1, itemPedido2));
		pedido2.getItems().addAll(Arrays.asList(itemPedido3));

		produto1.getItems().addAll(Arrays.asList(itemPedido1));
		produto2.getItems().addAll(Arrays.asList(itemPedido3));
		produto3.getItems().addAll(Arrays.asList(itemPedido2));

		itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));

	}

}
