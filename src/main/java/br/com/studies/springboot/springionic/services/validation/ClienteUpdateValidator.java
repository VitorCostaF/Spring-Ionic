package br.com.studies.springboot.springionic.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import br.com.studies.springboot.springionic.domain.Cliente;
import br.com.studies.springboot.springionic.dto.ClienteDTO;
import br.com.studies.springboot.springionic.repositories.ClienteRepository;
import br.com.studies.springboot.springionic.resource.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		
		@SuppressWarnings("unchecked")
		Map<String, String> attributesMap = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		Cliente clienteBanco = clienteRepository.findByEmail(objDto.getEmail());
		
		Integer uriId = Integer.parseInt(attributesMap.get("id"));
		
		if(clienteBanco != null && !clienteBanco.getId().equals(uriId)) {
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
