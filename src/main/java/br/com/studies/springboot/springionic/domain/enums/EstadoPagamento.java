package br.com.studies.springboot.springionic.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITAOD(2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer codigo;
	private String descricao;
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}	
	
	public static EstadoPagamento toEnum(Integer codigo) {
		
		if(codigo == null) {
			return null;
		}
		
		for(EstadoPagamento enumValue : EstadoPagamento.values()) {
			if(codigo.equals(enumValue.getCodigo())) {
				return enumValue;
			}
		}
		
		throw new IllegalArgumentException();
		
	}
}
