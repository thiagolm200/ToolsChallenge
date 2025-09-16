# ToolsChallenge

Este projeto é uma API RESTful desenvolvida em Java com Spring Boot para gerenciar operações de transações financeiras, incluindo pagamentos, estornos e consultas.

## Funcionalidades
- Realizar pagamento
- Realizar estorno
- Consultar todas as transações
- Consultar transação por ID

## Tecnologias Utilizadas
- Java 21
- Spring Boot
- Spring Data JPA
- H2 Database (padrão para testes)
- JUnit e Mockito (testes automatizados)

## Estrutura do Projeto
- `controller/` - Controllers REST
- `service/` - Lógica de negócio
- `repository/` - Acesso a dados
- `dto/` - Data Transfer Objects (request/response)
- `entity/` - Entidades JPA
- `exception/` - Tratamento global de exceções

## Como Executar
1. Clone o repositório
2. Execute `./mvnw spring-boot:run`
3. Acesse a API em `http://localhost:8080/api`

## Endpoints
- `POST /api/pagamento` - Realiza um pagamento
  - Exemplo de Request (JSON):
    ```json
    {
        "transacao": {
            "cartao": "44441234",
            "id": "41411413",
            "descricao": {
                "valor": 50.00,
                "dataHora": "01/05/2021 18:30:00",
                "estabelecimento": "PetStest"
            },
            "formaPagamento": {
                "tipo": "AVISTA",
                "parcelas": 1
            }
        }
    }
    ```
  - Exemplo de Response (JSON):
    ```json
    {
    	"status": 201,
    	"message": "Pagamento criado com sucesso",
    	"transacao": {
      	  "cartao": "41411413",
      	  "id": 414114113,
    	    "descricao": {
      	      "valor": 50.0,
      	      "dataHora": "01/05/2021 18:30:00",
      	      "estabelecimento": "PetStest",
     	      "nsu": "6",
      	      "codigoAutorizacao": "6",
       	     "status": "AUTORIZADO"
      	  },
       	 "formaPagamento": {
            	"tipo": "AVISTA",
           	 "parcelas": 1
        	}
    	}
	}
    ```
	
- `GET /api/estorno/{id}` - Realiza estorno de uma transação
  - Exemplo de Response (JSON):
    ```json
    {
    	"status": 201,
    	"message": "Pagamento criado com sucesso",
    	"transacao": {
      	  "cartao": "41411413",
      	  "id": 414114113,
    	    "descricao": {
      	      "valor": 50.0,
      	      "dataHora": "01/05/2021 18:30:00",
      	      "estabelecimento": "PetStest",
     	      "nsu": "6",
      	      "codigoAutorizacao": "6",
       	     "status": "CANCELADO"
      	  },
       	 "formaPagamento": {
            	"tipo": "AVISTA",
           	 "parcelas": 1
        	}
    	}
	}
    ```

- `GET /api/consulta` - Lista todas as transações
  - Exemplo de Response (JSON):
    ```json
	{
	    "status": 200,
	    "message": "Consulta realizada com sucesso",
	    "transacao": [
	        {
	            "cartao": "12333444",
	            "id": 13333,
	            "descricao": {
	                "valor": 150.75,
	                "dataHora": "16-09-2025 14:33:18",
	                "estabelecimento": "Pet Shop mundo cão",
	                "nsu": "1",
	                "codigoAutorizacao": "1",
	                "status": "AUTORIZADO"
	            },
	            "formaPagamento": {
	                "tipo": "AVISTA",
	                "parcelas": 3
	            }
	        },
	        {
	            "cartao": "12333444",
	            "id": 1444,
	            "descricao": {
	                "valor": 299.99,
	                "dataHora": "16-09-2025 14:33:18",
	                "estabelecimento": "Mercado Testt",
	                "nsu": "2",
	                "codigoAutorizacao": "2",
	                "status": "NEGADO"
	            },
	            "formaPagamento": {
	                "tipo": "AVISTA",
	                "parcelas": 1
	            }
	        },
	        {
	            "cartao": "33333444",
	            "id": 1555,
	            "descricao": {
	                "valor": 59.9,
	                "dataHora": "16-09-2025 14:33:18",
	                "estabelecimento": "Shoppiing",
	                "nsu": "3",
	                "codigoAutorizacao": "3",
	                "status": "CANCELADO"
	            },
	            "formaPagamento": {
	                "tipo": "PARCELADO LOJA",
	                "parcelas": 0
	            }
	        },
	        {
	            "cartao": "44441234",
	            "id": 16666,
	            "descricao": {
	                "valor": 1200.0,
	                "dataHora": "16-09-2025 14:33:18",
	                "estabelecimento": "Loja Test",
	                "nsu": "4",
	                "codigoAutorizacao": "4",
	                "status": "AUTORIZADO"
	            },
	            "formaPagamento": {
	                "tipo": "PARCELADO EMISSOR",
	                "parcelas": 12
	            }
	        }
	    ]
	}
	```

- `GET /api/consulta/{id}` - Consulta transação por ID
  - Exemplo de Response (JSON):
    ```json
    	{
	    "status": 200,
	    "message": "Consulta realizada com sucesso",
	    "transacao": {
	        "cartao": "12333444",
	        "id": 13333,
	        "descricao": {
	            "valor": 150.75,
	            "dataHora": "16-09-2025 14:36:44",
	            "estabelecimento": "Pet Shop mundo cão",
	            "nsu": "1",
	            "codigoAutorizacao": "1",
	            "status": "AUTORIZADO"
	        },
	        "formaPagamento": {
	            "tipo": "AVISTA",
	            "parcelas": 3
	        }
	    }
	}

## Observações
- O projeto utiliza validação avançada nos DTOs.
- Todas as respostas seguem o padrão `ApiResponse`.
- O tratamento de erros é centralizado via `GlobalExceptionHandler`.