package br.com.studies.springboot.springionic.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.services.validation.ClienteUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 100, message = "O comprimento deve estar entre 5 e 100")
	private String nome;

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}
	
}
