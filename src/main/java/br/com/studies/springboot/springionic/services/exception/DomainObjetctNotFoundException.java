package br.com.studies.springboot.springionic.services.exception;

public class DomainObjetctNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DomainObjetctNotFoundException(String msg) {
		super(msg);
	}

	public DomainObjetctNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
