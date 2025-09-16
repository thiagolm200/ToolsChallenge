package br.com.tools.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tools.challenge.dto.ApiResponse;
import br.com.tools.challenge.dto.RequestTransacaoDTO;
import br.com.tools.challenge.dto.TransacaoDTO;
import br.com.tools.challenge.entity.StatusTransacao;
import br.com.tools.challenge.entity.TipoPagamento;
import br.com.tools.challenge.entity.TransacaoEntity;
import br.com.tools.challenge.service.EstornoService;
import br.com.tools.challenge.service.PagamentoService;
import br.com.tools.challenge.service.TransacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OperacaoController {

	@Autowired
	private PagamentoService pagamentoService;
	
	@Autowired
	private EstornoService estornoService;
	
	@Autowired
	private TransacaoService transacaoService;

	@PostMapping("/pagamento")
    public ResponseEntity<ApiResponse<TransacaoDTO>> criarPagamento(@Valid @RequestBody RequestTransacaoDTO request) {
		
		TransacaoDTO transacaoDTO = request.getTransacao();

		if (transacaoService.retornaTransacaoPorCodigo(transacaoDTO.getId()) != null) 
			return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Pagamento já realizado para o id informado", null));
		if (TipoPagamento.pegaEnumPelaDescricao(transacaoDTO.getFormaPagamento().getTipo()) == null) 
			return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Tipo de pagamento inválido", null));

		StatusTransacao statusTransacao = pagamentoService.criarPagamento(transacaoDTO);
		
		TransacaoEntity transacaoEntity = transacaoService.converterDTOParaEntity(transacaoDTO, statusTransacao.getDescricao());
		
		if (transacaoEntity != null) {
			
			transacaoService.salvarTransacao(transacaoEntity);
			
			transacaoDTO.getDescricao().setNsu(transacaoEntity.getId().toString());
			transacaoDTO.getDescricao().setCodigoAutorizacao(transacaoEntity.getId().toString());
		}
		
		transacaoDTO.getDescricao().setStatus(statusTransacao.getDescricao());
		
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.CREATED.value(), "Pagamento criado com sucesso", transacaoDTO), HttpStatus.CREATED);
	}

	@GetMapping("/estorno/{id}")
	public ResponseEntity<ApiResponse<TransacaoDTO>> realizaEstorno(@PathVariable Long id) {
		
		TransacaoEntity transacaoEntity = transacaoService.retornaTransacaoPorCodigo(id);
		
		if (transacaoEntity == null ||
				StatusTransacao.CANCELADO.getDescricao().equals(transacaoEntity.getStatus()) ||
				StatusTransacao.NEGADO.getDescricao().equals(transacaoEntity.getStatus())) {
			return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Não há estorno para realizar", null));
		}
		
		StatusTransacao statusTransacao = estornoService.realizaEstorno(transacaoEntity);
		
		if (statusTransacao == StatusTransacao.CANCELADO) {
			
			transacaoEntity.setStatus(StatusTransacao.CANCELADO.getDescricao());
			
			transacaoService.salvarTransacao(transacaoEntity);
			
			TransacaoDTO transacaoDTO = transacaoService.converterEntityParaDTO(transacaoEntity);
			
			return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Estorno realizado com sucesso", transacaoDTO), HttpStatus.OK);
		}
		
		return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Estorno não autorizado", null));
	}

	@GetMapping("/consulta")
	public ResponseEntity<ApiResponse<List<TransacaoDTO>>> consultarTodos() {
		
		List<TransacaoEntity> listaTransacaoEntity = transacaoService.retornaTodasTransacoes();
		
		if (CollectionUtils.isEmpty(listaTransacaoEntity)) 
			return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Sem transações para consulta", null));
		
		List<TransacaoDTO> listaTransacaoDTO = new ArrayList<>();
		
		for (TransacaoEntity transacaoEntity : listaTransacaoEntity) {
			listaTransacaoDTO.add(transacaoService.converterEntityParaDTO(transacaoEntity));
		}
		
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Consulta realizada com sucesso", listaTransacaoDTO), HttpStatus.OK);
	}

	@GetMapping("/consulta/{id}")
	public ResponseEntity<ApiResponse<TransacaoDTO>> consultaPorId(@PathVariable Long id) {
		
		TransacaoEntity transacaoEntity = transacaoService.retornaTransacaoPorCodigo(id);
		
		if (transacaoEntity == null) 
			return ResponseEntity.badRequest().body(new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Id nao existente para consulta", null));
		
		TransacaoDTO transacaoDTO = transacaoService.converterEntityParaDTO(transacaoEntity);
		
		return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK.value(), "Consulta realizada com sucesso", transacaoDTO), HttpStatus.OK);
	}
}