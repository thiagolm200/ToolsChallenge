package br.com.tools.challenge.entity;

public enum StatusTransacao {

	AUTORIZADO("AUTORIZADO"), 
	NEGADO("NEGADO"), 
	//CANCELADO POIS ESTÁ NO EXEMPLO DE ESTORNO
	CANCELADO("CANCELADO");
	
	private String descricao;
	
	private StatusTransacao(String descricao){
		this.descricao = descricao;
	}
	

	public String getDescricao() {
		return descricao;
	}
}
