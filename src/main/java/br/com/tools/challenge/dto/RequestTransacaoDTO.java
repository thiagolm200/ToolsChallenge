package br.com.tools.challenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class RequestTransacaoDTO {
	
	@NotNull
	@Valid
    private TransacaoDTO transacao;

	public TransacaoDTO getTransacao() {
		return transacao;
	}

	public void setTransacao(TransacaoDTO transacao) {
		this.transacao = transacao;
	}
}