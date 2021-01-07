package br.com.studies.springboot.springionic.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.domain.enums.TipoCliente;
import br.com.studies.springboot.springionic.dto.ClienteNewDTO;
import br.com.studies.springboot.springionic.repositories.ClienteRepository;
import br.com.studies.springboot.springionic.resource.exception.FieldMessage;
import br.com.studies.springboot.springionic.services.validation.utils.ValidaCpfCnpj;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !ValidaCpfCnpj.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !ValidaCpfCnpj.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente clienteBanco = clienteRepository.findByEmail(objDto.getEmail());
		
		if(clienteBanco != null) {
			list.add(new FieldMessage( "email" , "Email já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getFieldMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
