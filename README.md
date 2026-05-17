# Helpdesk API

![Java 17](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?logo=springboot&logoColor=white)
![REST API](https://img.shields.io/badge/API-REST-0A66C2)
![H2](https://img.shields.io/badge/Database-H2-1E4C8F)

API REST de helpdesk construída com Spring Boot para gestão de tickets.

## Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database

## Funcionalidades

- Criar ticket
- Buscar ticket por id
- Listar tickets com filtro por status e prioridade
- Paginação
- Ordenação por colunas
- Ordenação de negócio para prioridade e status
- Atualização parcial com `PATCH`
- Exclusão de ticket
- Tratamento global de erros
- Seed local com `data.sql`

## Endpoints

- `POST /tickets`
- `GET /tickets/{id}`
- `GET /tickets?status=OPEN&priority=HIGH&page=0&size=10&sort=title,asc`
- `PATCH /tickets/{id}`
- `DELETE /tickets/{id}`

## Como rodar

```bash
./mvnw spring-boot:run
```

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

API disponível em:

- `http://localhost:8080`

## Exemplos

### Criar ticket

```json
{
  "title": "Erro no login do painel",
  "description": "O utilizador nao consegue aceder ao painel administrativo.",
  "priority": "HIGH",
  "requesterName": "Vitor Silva",
  "requesterEmail": "vitor@email.com",
  "assignedTo": "Ana Souza"
}
```

### Atualizar ticket

```json
{
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "assignedTo": "Ana Souza"
}
```

## Observações

- A aplicação usa H2 em memória para desenvolvimento.
- Ao subir a aplicação, o `data.sql` carrega tickets de exemplo automaticamente.
- `priorityOrder` e `statusOrder` são usados para suportar ordenação de negócio.
- O CORS está liberado para `http://localhost:5173`.
