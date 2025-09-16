package br.com.tools.challenge.dto;

public class ApiResponse<T> {
    private int status;
    private String message;
    private T transacao;

    public ApiResponse() {}

    public ApiResponse(int status, String message, T transacao) {
        this.status = status;
        this.message = message;
        this.transacao = transacao;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public T getTransacao() {
		return transacao;
	}

	public void setTransacao(T transacao) {
		this.transacao = transacao;
	}
}
