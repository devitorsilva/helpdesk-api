# Helpdesk API

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
- Atualização parcial com `PATCH`
- Tratamento global de erros
- Seed local com `data.sql`

## Endpoints

- `POST /tickets`
- `GET /tickets/{id}`
- `GET /tickets?status=OPEN&priority=HIGH&page=0&size=10`
- `PATCH /tickets/{id}`

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
  "requesterEmail": "vitor@email.com"
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
- O CORS está liberado para `http://localhost:5173`.
