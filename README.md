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
      	  "cartao": "44441234",
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
- `GET /api/consulta` - Lista todas as transações
- `GET /api/consulta/{id}` - Consulta transação por ID

## Observações
- O projeto utiliza validação avançada nos DTOs.
- Todas as respostas seguem o padrão `ApiResponse`.
- O tratamento de erros é centralizado via `GlobalExceptionHandler`.