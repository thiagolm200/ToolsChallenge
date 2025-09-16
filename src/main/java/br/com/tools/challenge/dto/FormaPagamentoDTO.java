package br.com.tools.challenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FormaPagamentoDTO {

	@NotBlank(message = "O tipo da forma de pagamento é obrigatório")
	private String tipo;
	
	@NotNull(message = "A quantidade de parcelas na forma de pagamento é obrigatório")
	private Integer parcelas;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getParcelas() {
		return parcelas;
	}

	public void setParcelas(Integer parcelas) {
		this.parcelas = parcelas;
	}
}
