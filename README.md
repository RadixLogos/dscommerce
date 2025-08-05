# Dscommerce
[![License: MIT](https://img.shields.io/badge/License-MIT-darkgreen.svg)](https://github.com/RadixLogos/dscommerce/blob/main/LICENSE)
## Sobre o projeto
Esse projeto é um exemplo de uma aplicação back-end para um E-commerce. Desenvolvi-o durante o curso Spring Professional da escola Devsuperior. A medida que o conteúdo era passado eu implementava sozinho com a finalidade de reter as informações e técnicas ensinadas. Optei por fazer uso de Records ao invés de Classes para os DTOs.
## Tecnologias utilizadas
O projeto foi desenvolvido com as seguintes ferramentas
* Java 21
* Maven
* Spring Boot Framework
* Spring JPA
* H2 Database
* JWT
* OAuth2
## Padrões adotados
* Layered Architecture
* RESTful
* DTO
## Seed dos dados
O banco em memória H2 é populado no perfil de teste da aplicação atravéz do arquivo import.sql presente na pasta resources
## Features
O projeto contempla as seguintes funcionalidades por end-points:
### Acesso publico
* Listagem paginada de todos os produtos
* Busca de produtos por id
* Listagem de todas as categorias (Não há necessidade para restringir o acesso a esse end-point)
### Acesso exclusivo à usuários
* Buscar o próprio pedido, e se for administrador buscar todos os pedidos
* Fazer um novo pedido
* Fazer login e receber um token
### Acesso exclusivo à administradores
* Cadastrar novo produto
* Atualizar produtos
* Deletar produtos

# Como testar o projeto
Para testar a API é necessário ter instalado no computador o Java SDK e uma aplicação de testes de requisição, dou como sugestão o Postman desktop.
Faça um clone do projeto utilizando:
```bash
  git clone git@github.com:RadixLogos/dscommerce.git
```
Na IDE de sua preferencia faça o import do projeto
### Testar no postman (localmente):
Após ter o projeto rodando como servidor local clicando no botão abaixo faça o fork da collection do postman que possue as requisições prontas para testes, utilizando o environment OAuth2

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/32035818-50152931-86ca-4c38-9601-51030fa7e5e1?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D32035818-50152931-86ca-4c38-9601-51030fa7e5e1%26entityType%3Dcollection%26workspaceId%3Dc4e03449-5644-4b5f-8c74-d0cda8f79012#?env%5BOAuth2%5D=W3sia2V5IjoiYXNob3N0IiwidmFsdWUiOiJodHRwOi8vbG9jYWxob3N0OjgwODEiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MSIsImNvbXBsZXRlU2Vzc2lvblZhbHVlIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwic2Vzc2lvbkluZGV4IjowfSx7ImtleSI6InJzaG9zdCIsInZhbHVlIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsInNlc3Npb25JbmRleCI6MX0seyJrZXkiOiJjbGllbnQtaWQiLCJ2YWx1ZSI6Im15Y2xpZW50aWQiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Im15Y2xpZW50aWQiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6Im15Y2xpZW50aWQiLCJzZXNzaW9uSW5kZXgiOjJ9LHsia2V5IjoiY2xpZW50LXNlY3JldCIsInZhbHVlIjoibXljbGllbnRzZWNyZXQiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Im15Y2xpZW50c2VjcmV0IiwiY29tcGxldGVTZXNzaW9uVmFsdWUiOiJteWNsaWVudHNlY3JldCIsInNlc3Npb25JbmRleCI6M30seyJrZXkiOiJ1c2VybmFtZSIsInZhbHVlIjoiYWxleEBnbWFpbC5jb20iLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6ImFsZXhAZ21haWwuY29tIiwiY29tcGxldGVTZXNzaW9uVmFsdWUiOiJhbGV4QGdtYWlsLmNvbSIsInNlc3Npb25JbmRleCI6NH0seyJrZXkiOiJwYXNzd29yZCIsInZhbHVlIjoiMTIzNDU2IiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiIxMjM0NTYiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6IjEyMzQ1NiIsInNlc3Npb25JbmRleCI6NX0seyJrZXkiOiJ0b2tlbiIsInZhbHVlIjoiIiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiIiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6IiIsInNlc3Npb25JbmRleCI6Nn1d)
# Domain model
![domain model image](https://github.com/user-attachments/assets/687b7794-42d9-48d8-be1f-6cb52a96c5c2)


