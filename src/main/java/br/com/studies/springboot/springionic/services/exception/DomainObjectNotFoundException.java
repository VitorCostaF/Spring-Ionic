package br.com.studies.springboot.springionic.services.exception;

public class DomainObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DomainObjectNotFoundException(String msg) {
		super(msg);
	}

	public DomainObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
