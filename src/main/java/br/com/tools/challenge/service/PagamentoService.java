package br.com.tools.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tools.challenge.dto.TransacaoDTO;
import br.com.tools.challenge.entity.ContaEntity;
import br.com.tools.challenge.entity.StatusTransacao;
import br.com.tools.challenge.repository.ContaRepository;

@Service
public class PagamentoService {

	@Autowired
	private ContaRepository contaRepository;

	public StatusTransacao criarPagamento(TransacaoDTO request) {
		
		ContaEntity conta = contaRepository.findByCartao(request.getCartao()).orElseThrow(() -> new RuntimeException("Conta não encontrada para o cartão informado"));
		
		if(conta.getSaldo() >= request.getDescricao().getValor()) {
			
			conta.setSaldo(conta.getSaldo() - request.getDescricao().getValor());
			
			contaRepository.save(conta);
			
			return StatusTransacao.AUTORIZADO;
		}
		
		return StatusTransacao.NEGADO;
	}
	
	
}
