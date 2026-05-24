# Helpdesk API

![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/Database-PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?logo=swagger&logoColor=black)
![Docker](https://img.shields.io/badge/Docker-Local_Infra-2496ED?logo=docker&logoColor=white)

API REST de helpdesk para gestao de tickets e comentarios.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA / Hibernate
- Bean Validation
- PostgreSQL
- Swagger / OpenAPI
- Docker

## Funcionalidades

- Criar ticket
- Buscar ticket por id
- Listar tickets com filtro por status e prioridade
- Paginacao
- Ordenacao por colunas
- Ordenacao de negocio para prioridade e status
- Busca textual por titulo ou descricao
- Atualizacao parcial com `PATCH`
- Exclusao de ticket
- Criacao e listagem de comentarios por ticket
- Tratamento global de erros
- Documentacao Swagger

## Endpoints principais

- `POST /tickets`
- `GET /tickets/{id}`
- `GET /tickets`
- `GET /tickets/search?term=login&page=0&size=10`
- `PATCH /tickets/{id}`
- `DELETE /tickets/{id}`
- `POST /tickets/{ticketId}/comments`
- `GET /tickets/{ticketId}/comments`

## Como rodar

### Infra local

PostgreSQL:

```powershell
docker run --name helpdesk-postgres -e POSTGRES_DB=helpdesk -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16
```

RabbitMQ:

```powershell
docker run --name helpdesk-rabbitmq -p 5672:5672 -p 15672:15672 -d rabbitmq:3-management
```

### Aplicacao

```powershell
.\mvnw.cmd spring-boot:run
```

API:

- `http://localhost:8080`

Swagger:

- `http://localhost:8080/swagger-ui/index.html`

RabbitMQ UI:

- `http://localhost:15672`

## Observacoes

- `priorityOrder` e `statusOrder` suportam ordenacao de negocio.
- `TicketResponse` e `TicketCommentResponse` evitam loop de serializacao.
- O projeto esta sendo usado como base de estudo para JPA/Hibernate, testes, SQL e mensageria.
