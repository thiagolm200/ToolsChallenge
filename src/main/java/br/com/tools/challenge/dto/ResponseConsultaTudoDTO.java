package br.com.tools.challenge.dto;

import java.util.List;

public class ResponseConsultaTudoDTO {

	private List<TransacaoDTO> listaTransacao;
	
	private String erro;
	
	public ResponseConsultaTudoDTO() {
		super();
	}

	public ResponseConsultaTudoDTO(String erro) {
		super();
		this.erro = erro;
	}

	public List<TransacaoDTO> getListaTransacao() {
		return listaTransacao;
	}

	public void setListaTransacao(List<TransacaoDTO> listaTransacao) {
		this.listaTransacao = listaTransacao;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}
}
