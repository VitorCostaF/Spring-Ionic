package br.com.studies.springboot.springionic.dto;

import br.com.studies.springboot.springionic.domain.Produto;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProdutoDTO {

	private Integer id;
	private String nome;
	private Double preco;
	
	public ProdutoDTO(Produto produto) {
		this.id = produto.getId();
		this.nome = produto.getNome();
		this.preco = produto.getPreco();
	}
	
}
