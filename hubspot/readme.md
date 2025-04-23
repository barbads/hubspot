# Configuração do Banco de Dados PostgreSQL para o Projeto HubSpot

Este guia explica como criar e configurar um banco de dados PostgreSQL local para a aplicação HubSpot.

---

## ✅ Requisitos

- PostgreSQL instalado localmente
- Acesso ao terminal (Linux/Mac) ou Prompt de Comando/PowerShell (Windows)
- Senha do usuário `postgres` (neste exemplo, estamos usando `matheus`)

---

### 1. Acesse o console do PostgreSQL

Abra o terminal e digite:

```bash
psql -U postgres
```

### 2. Crie o banco de dados
```bash
CREATE DATABASE hubspot;
GRANT ALL PRIVILEGES ON DATABASE hubspot TO postgres;
\q
```

testando a conexao:
```bash
psql -U postgres -d hubspot
```

agora, para ter as tabelas utilizadas na aplicação:
```bash
mvn liquibase:update
```
---

### Primeiro endpoint

chamar um GET em http://localhost:8080/api/auth/generateUrl. A URL será gerada e, ao clicá-la,
você será redirecionado para a página de login do HubSpot. Após o login, você será redirecionado para a URL de callback configurada no projeto,
que é localhost:8080/api/auth/callback.

Automaticamente o código será trocado pelo token de acesso. O app calcula quando vai expirar e persiste
na tabela de tokens em "expires_at". 

### Endpoint de criação de contatos.

localhost:8080/api/auth/contact cria contato, puxando um access token, se estiver válido ainda.
No Body da chamada, colocar como nos exemplos JSON do proprio hubspot:

{
"email": "example@hasdasdasd.com",
"firstname": "Joao",
"lastname": "Doe",
"phone": "(555) 555-5555",
"company": "HubSpot",
"website": "hubspot.com",
"lifecyclestage": "marketingqualifiedlead"
}.

Isso criará um contato no HubSpot e persistirá suas informações na tabela contacts.

---
### Webhook

O webhook recebe as informações de um contato criado no HubSpot. Então, dentro do webhook, quando 
é chamado, pega-se o request content, em forma de JSON, e transforma em um objeto ContactEvent.
Um DTO é formado e atualiza o contato baseado em seu object_id, informando agora seu Subscription Status
para CREATED.

