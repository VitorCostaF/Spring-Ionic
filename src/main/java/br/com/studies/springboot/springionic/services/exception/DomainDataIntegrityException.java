package br.com.studies.springboot.springionic.services.exception;

public class DomainDataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DomainDataIntegrityException(String msg) {
		super(msg);
	}

	public DomainDataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
