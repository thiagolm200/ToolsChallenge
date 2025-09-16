package br.com.tools.challenge.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tools.challenge.dto.DescricaoTransacaoDTO;
import br.com.tools.challenge.dto.FormaPagamentoDTO;
import br.com.tools.challenge.dto.TransacaoDTO;
import br.com.tools.challenge.entity.TransacaoEntity;
import br.com.tools.challenge.repository.TransacaoRepository;

@Service
public class TransacaoService {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	public TransacaoEntity retornaTransacaoPorCodigo(Long codigo) {
		return transacaoRepository.findByCodigo(codigo).orElse(null);
	}
	
	public List<TransacaoEntity> retornaTodasTransacoes() {
		return transacaoRepository.findAll();
	}
	
	public TransacaoEntity salvarTransacao(TransacaoEntity transacaoEntity) {
		return transacaoRepository.save(transacaoEntity);
	}
	
	public TransacaoEntity converterDTOParaEntity(TransacaoDTO transacaoDTO, String status) {
		
		TransacaoEntity transacaoEntity = new TransacaoEntity();
		
		transacaoEntity.setCodigo(transacaoDTO.getId());
		
		transacaoEntity.setCartao(transacaoDTO.getCartao());
		
		transacaoEntity.setValor(transacaoDTO.getDescricao().getValor());
		
		transacaoEntity.setDataHora(converterStringParaData(transacaoDTO.getDescricao().getDataHora()));
		
		transacaoEntity.setEstabelecimento(transacaoDTO.getDescricao().getEstabelecimento());
		
		transacaoEntity.setTipoPagamento(transacaoDTO.getFormaPagamento().getTipo());
		
		transacaoEntity.setStatus(status);
		
		transacaoEntity.setParcelas(transacaoDTO.getFormaPagamento().getParcelas());
		
		return transacaoEntity;
	}
	
	public TransacaoDTO converterEntityParaDTO(TransacaoEntity transacaoEntity) {
		
		TransacaoDTO transacaoDTO = new TransacaoDTO();
		
		transacaoDTO.setId(transacaoEntity.getCodigo());
		
		transacaoDTO.setCartao(transacaoEntity.getCartao());
		
		transacaoDTO.setDescricao(populaDescricaoTransacao(transacaoEntity));
		
		transacaoDTO.setFormaPagamento(populaFormaTransacao(transacaoEntity)); 
		
		return transacaoDTO;
	}
	
	private DescricaoTransacaoDTO populaDescricaoTransacao(TransacaoEntity transacaoEntity) {

		DescricaoTransacaoDTO descricaoTransacaoDTO = new DescricaoTransacaoDTO();
		
		descricaoTransacaoDTO.setValor(transacaoEntity.getValor());	
		
		descricaoTransacaoDTO.setDataHora(converterDataParaString(transacaoEntity.getDataHora()));
		
		descricaoTransacaoDTO.setEstabelecimento(transacaoEntity.getEstabelecimento());
		
		descricaoTransacaoDTO.setNsu(transacaoEntity.getId().toString());
		
		descricaoTransacaoDTO.setCodigoAutorizacao(transacaoEntity.getId().toString());
		
		descricaoTransacaoDTO.setStatus(transacaoEntity.getStatus());
		
		return descricaoTransacaoDTO;
	}

	private FormaPagamentoDTO populaFormaTransacao(TransacaoEntity transacaoEntity) {

		FormaPagamentoDTO formaPagamentoDTO = new FormaPagamentoDTO();
		
		formaPagamentoDTO.setTipo(transacaoEntity.getTipoPagamento());
		
		formaPagamentoDTO.setParcelas(transacaoEntity.getParcelas());
		
		return formaPagamentoDTO;
	}

	private String converterDataParaString(Date data) {
		return sdf.format(data);
    }
	
	private Date converterStringParaData(String data) {
       
		try {
            return sdf.parse(data);
        } catch (ParseException e) {
        	//RETORNANDO A MESMA DATA, MAS PODERIA REALIZAR OUTRO TRATAMENTO
            return new Date();
        }
    }
	
}
