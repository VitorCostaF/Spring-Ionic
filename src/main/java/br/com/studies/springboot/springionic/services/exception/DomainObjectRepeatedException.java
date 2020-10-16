package br.com.studies.springboot.springionic.services.exception;

public class DomainObjectRepeatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DomainObjectRepeatedException(String msg) {
		super(msg);
	}

	public DomainObjectRepeatedException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
