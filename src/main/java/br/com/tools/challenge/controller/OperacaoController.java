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

import br.com.tools.challenge.dto.RequestTransacaoDTO;
import br.com.tools.challenge.dto.ResponseConsultaTudoDTO;
import br.com.tools.challenge.dto.ResponseTransacaoDTO;
import br.com.tools.challenge.dto.TransacaoDTO;
import br.com.tools.challenge.entity.StatusTransacao;
import br.com.tools.challenge.entity.TipoPagamento;
import br.com.tools.challenge.entity.TransacaoEntity;
import br.com.tools.challenge.service.EstornoService;
import br.com.tools.challenge.service.PagamentoService;
import br.com.tools.challenge.service.TransacaoService;
import io.micrometer.common.util.StringUtils;
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
    public ResponseEntity<ResponseTransacaoDTO> criarPagamento(@Valid @RequestBody RequestTransacaoDTO request) {
		
		String erro = "";		
		
		TransacaoDTO transacaoDTO = request.getTransacao();
		
		if(transacaoService.retornaTransacaoPorCodigo(transacaoDTO.getId()) != null)
			erro = "Pagamento já realizado para o id informado";
		else if(TipoPagamento.pegaEnumPelaDescricao(transacaoDTO.getFormaPagamento().getTipo()) == null) 
			erro = "Tipo de pagamento inválido";
		
		try {
			
			if(StringUtils.isBlank(erro)) {
				
				StatusTransacao statusTransacao = pagamentoService.criarPagamento(transacaoDTO);
				
				//talvez salvar somente para autorizado
				//if(statusTransacao == StatusTransacao.AUTORIZADO) {
					
					TransacaoEntity transacaoEntity = transacaoService.converterDTOParaEntity(transacaoDTO, statusTransacao.getDescricao());
					
					if(transacaoEntity != null) {
						
						transacaoService.salvarTransacao(transacaoEntity);
						
						transacaoDTO.getDescricao().setNsu(transacaoEntity.getId().toString());
						
						transacaoDTO.getDescricao().setCodigoAutorizacao(transacaoEntity.getId().toString());
					}
				//}
			
				transacaoDTO.getDescricao().setStatus(statusTransacao.getDescricao());
				
				ResponseTransacaoDTO response = new ResponseTransacaoDTO();
				
				response.setTransacao(transacaoDTO);
				
				return new ResponseEntity<>(response, HttpStatus.CREATED);
			}
			
		}catch(Exception e) {
			erro = e.getMessage();
		}
		
        return new ResponseEntity<>(new ResponseTransacaoDTO(erro), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/estorno/{id}")
	public ResponseEntity<ResponseTransacaoDTO> realizaEstorno(@PathVariable Long id) {

		String erro = "";
		
		TransacaoEntity transacaoEntity = transacaoService.retornaTransacaoPorCodigo(id);
		
		if(transacaoEntity == null || 
				transacaoEntity.getStatus().equals(StatusTransacao.CANCELADO.getDescricao()) 
				|| transacaoEntity.getStatus().equals(StatusTransacao.NEGADO.getDescricao()))
			erro = "Não há estorno para realizar";
		
		try {
			
			if(StringUtils.isBlank(erro)) {
				
				StatusTransacao statusTransacao = estornoService.realizaEstorno(transacaoEntity);
				
				if(statusTransacao == StatusTransacao.CANCELADO) {
					
					transacaoEntity.setStatus(StatusTransacao.CANCELADO.getDescricao());
					
					transacaoService.salvarTransacao(transacaoEntity);
					
					TransacaoDTO transacaoDTO = transacaoService.converterEntityParaDTO(transacaoEntity);
					
					ResponseTransacaoDTO response = new ResponseTransacaoDTO();
					
					response.setTransacao(transacaoDTO);
					
					return new ResponseEntity<>(response, HttpStatus.CREATED);
				}
			}
			
		}catch(Exception e) {
			erro = e.getMessage();
		}
		
		return new ResponseEntity<>(new ResponseTransacaoDTO(erro), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/consulta")
	public ResponseEntity<ResponseConsultaTudoDTO> consultarTodos() {

		String erro = "";
		
		List<TransacaoEntity> listaTransacaoEntity = transacaoService.retornaTodasTransacoes();
		
		if(CollectionUtils.isEmpty(listaTransacaoEntity))
			erro = "Sem transações para consulta";

		if(StringUtils.isBlank(erro)) {
		
			ResponseConsultaTudoDTO response = new ResponseConsultaTudoDTO();
			
			List<TransacaoDTO> listaTransacaoDTO = new ArrayList<TransacaoDTO>();
			
			for(TransacaoEntity transacaoEntity : listaTransacaoEntity) 
				listaTransacaoDTO.add(transacaoService.converterEntityParaDTO(transacaoEntity));
			
			response.setListaTransacao(listaTransacaoDTO);
			
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(new ResponseConsultaTudoDTO(erro), HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/consulta/{id}")
	public ResponseEntity<ResponseTransacaoDTO> consultaPorId(@PathVariable Long id) {

		String erro = "";
		
		TransacaoEntity transacaoEntity = transacaoService.retornaTransacaoPorCodigo(id);
		
		if(transacaoEntity == null)
			erro = "Id nao existente para consulta";

		if(StringUtils.isBlank(erro)) {
		
			TransacaoDTO transacaoDTO = transacaoService.converterEntityParaDTO(transacaoEntity);
			
			ResponseTransacaoDTO response = new ResponseTransacaoDTO();
			
			response.setTransacao(transacaoDTO);
			
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		}
		
		return new ResponseEntity<>(new ResponseTransacaoDTO(erro), HttpStatus.BAD_REQUEST);
	}
}
