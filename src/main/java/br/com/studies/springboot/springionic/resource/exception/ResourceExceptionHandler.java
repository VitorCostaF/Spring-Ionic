package br.com.studies.springboot.springionic.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.studies.springboot.springionic.services.exception.DomainDataIntegrityException;
import br.com.studies.springboot.springionic.services.exception.DomainObjectNotFoundException;
import br.com.studies.springboot.springionic.services.exception.DomainObjectRepeatedException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(DomainObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(DomainObjectNotFoundException exception, HttpServletRequest request) {
		
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DomainObjectRepeatedException.class)
	public ResponseEntity<StandardError> objectRepeated(DomainObjectRepeatedException exception, HttpServletRequest request) {
		
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(DomainDataIntegrityException.class)
	public ResponseEntity<StandardError> domainDataIntegrity(DomainDataIntegrityException exception, HttpServletRequest request) {
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> invalidArgument(MethodArgumentNotValidException exception, HttpServletRequest request) {
		
		ValidationError error = new ValidationError (HttpStatus.BAD_REQUEST.value(), "Erro de validação", System.currentTimeMillis());
		
		exception.getBindingResult().getFieldErrors().forEach(errEx -> error.addError(errEx.getField(), errEx.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	
}
