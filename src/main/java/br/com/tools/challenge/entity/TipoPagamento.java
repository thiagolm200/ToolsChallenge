package br.com.tools.challenge.entity;

public enum TipoPagamento {

	AVISTA("AVISTA"),
	PARCELADO("PARCELADO LOJA"),
	PARCELADO_EMISSOR("PARCELADO EMISSOR");
	
	private String descricao;
	
	private TipoPagamento(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
	
	public static TipoPagamento pegaEnumPelaDescricao(String descricao) {

		if (descricao == null) 
            return null;
        
        for (TipoPagamento tp : values()) {
            if (tp.getDescricao().equalsIgnoreCase(descricao)) {
                return tp;
            }
        }
        
        return null;
    }
}
