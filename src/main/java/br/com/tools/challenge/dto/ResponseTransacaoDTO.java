package br.com.tools.challenge.dto;

public class ResponseTransacaoDTO {

	private TransacaoDTO transacao;
	
	private String erro;
	
	public ResponseTransacaoDTO() {
		super();
	}

	public ResponseTransacaoDTO(String erro) {
		super();
		this.erro = erro;
	}

	public TransacaoDTO getTransacao() {
		return transacao;
	}

	public void setTransacao(TransacaoDTO transacao) {
		this.transacao = transacao;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
}
