package br.com.studies.springboot.springionic.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.studies.springboot.springionic.services.exception.DomainObjetctNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(DomainObjetctNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(DomainObjetctNotFoundException exception, HttpServletRequest request) {
		
		StandardError error = new StandardError(HttpStatus.NOT_FOUND.value(), exception.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
}
