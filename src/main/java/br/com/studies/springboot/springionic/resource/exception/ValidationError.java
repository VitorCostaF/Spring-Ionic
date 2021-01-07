package br.com.studies.springboot.springionic.resource.exception;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ValidationError extends StandardError {

	private List<FieldMessage> listaErros = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldMessage> getListaErros() {
		return listaErros;
	}

	public void setListaErros(List<FieldMessage> listaErros) {
		this.listaErros = listaErros;
	}
	
	public void addError(String fieldName, String fieldMessage) {
		listaErros.add(new FieldMessage(fieldName, fieldMessage));
	}

}
