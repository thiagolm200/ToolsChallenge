package br.com.tools.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.CollectionUtils;

import br.com.tools.challenge.dto.TransacaoDTO;
import br.com.tools.challenge.entity.ContaEntity;
import br.com.tools.challenge.entity.StatusTransacao;
import br.com.tools.challenge.entity.TransacaoEntity;
import br.com.tools.challenge.repository.ContaRepository;
import br.com.tools.challenge.repository.TransacaoRepository;

@ExtendWith(MockitoExtension.class)
public class OperacoesTest {

	@InjectMocks
    private TransacaoService transacaoService;
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@InjectMocks
    private PagamentoService pagamentoService;
	
	@InjectMocks
	private EstornoService estornoService;
	
	@Mock
	private ContaRepository contaRepository;
	
	@BeforeEach
    public void setUp() {
        
		ContaEntity conta = new ContaEntity();
		
		conta.setCartao("4445454");
		
		conta.setSaldo(500D);
		
		lenient().when(contaRepository.findByCartao("4445454")).thenReturn(Optional.of(conta));
		
		TransacaoEntity transacao = new TransacaoEntity();
		
		transacao.setId(55L);
	
		transacao.setCodigo(511121L);
		
		transacao.setCartao("4445454");
		
		transacao.setValor(null);
		
		transacao.setEstabelecimento("");
		
		transacao.setDataHora(new Date());
		
		transacao.setParcelas(1);
		
		transacao.setValor(50D);
		
		transacao.setStatus("AUTORIZADO");
		
		transacao.setTipoPagamento("AVISTA");

		lenient().when(transacaoRepository.findByCodigo(511121L)).thenReturn(Optional.of(transacao));
		lenient().when(transacaoRepository.findAll()).thenReturn(Collections.singletonList(transacao));
    }
	
	@Test
	public void testaPagamento() {
		
		TransacaoDTO transacaoDTO = transacaoService.converterEntityParaDTO(transacaoRepository.findByCodigo(511121L).orElse(null));
		
		transacaoDTO.setId(5515L);
		
		transacaoDTO.getDescricao().setValor(1D);
		
		System.out.println("Saldo antes do pagamento " +  contaRepository.findByCartao("4445454").get().getSaldo());
		
		StatusTransacao statusTransacao = pagamentoService.criarPagamento(transacaoDTO);
		
		System.out.println("Saldo apos o pagamento " +  contaRepository.findByCartao("4445454").get().getSaldo());
		
		assertEquals(StatusTransacao.AUTORIZADO.getDescricao(), statusTransacao.getDescricao());
	}
	
	@Test
	public void testaEstorno() {
		
		System.out.println("Saldo antes do estorno " +  contaRepository.findByCartao("4445454").get().getSaldo());
		
		StatusTransacao statusTransacao = estornoService.realizaEstorno(transacaoRepository.findByCodigo(511121L).orElse(null));
		
		System.out.println("Saldo apos o estorno " +  contaRepository.findByCartao("4445454").get().getSaldo());
		
		assertEquals(StatusTransacao.CANCELADO.getDescricao(), statusTransacao.getDescricao());
	}
	
	@Test
	public void testaBuscaContaPorCartao() {
		assertEquals(500D, contaRepository.findByCartao("4445454").get().getSaldo());
	}
	
	@Test
	public void testaRetornaTodoasTransacoes() {
		assert(!CollectionUtils.isEmpty(transacaoService.retornaTodasTransacoes()));
	}
}


