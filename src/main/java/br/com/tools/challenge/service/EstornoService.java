package br.com.tools.challenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tools.challenge.entity.ContaEntity;
import br.com.tools.challenge.entity.StatusTransacao;
import br.com.tools.challenge.entity.TransacaoEntity;
import br.com.tools.challenge.repository.ContaRepository;

@Service
public class EstornoService {

	@Autowired
	private ContaRepository contaRepository;

	public StatusTransacao realizaEstorno(TransacaoEntity transacaoEntity) {
		
		ContaEntity conta = contaRepository.findByCartao(transacaoEntity.getCartao()).orElseThrow(() -> new RuntimeException("Conta não encontrada para o cartão informado"));
		
		conta.setSaldo(conta.getSaldo() + transacaoEntity.getValor());
		
		contaRepository.save(conta);
		
		return StatusTransacao.CANCELADO;
	}
	
	
}
