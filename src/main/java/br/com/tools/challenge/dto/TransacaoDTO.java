package br.com.tools.challenge.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransacaoDTO {

	@NotBlank
	private String cartao;
	
	@NotNull
	@Min(1)
	private Long id;
	
	@NotNull
	@Valid
    private DescricaoTransacaoDTO descricao;
	
	@NotNull
	@Valid
    private FormaPagamentoDTO formaPagamento;

	public String getCartao() {
		return cartao;
	}

	public void setCartao(String cartao) {
		this.cartao = cartao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DescricaoTransacaoDTO getDescricao() {
		return descricao;
	}

	public void setDescricao(DescricaoTransacaoDTO descricao) {
		this.descricao = descricao;
	}

	public FormaPagamentoDTO getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoDTO formaPagamento) {
		this.formaPagamento = formaPagamento;
	}  
	
}
