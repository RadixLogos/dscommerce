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
Para testar a API é necessário ter instalado no computador o Java 21 e uma aplicação de testes de requisição, dou como sugestão o Postman desktop.
Faça um clone do projeto utilizando:
```bash
  git clone git@github.com:RadixLogos/dscommerce.git
```
Na IDE de sua preferencia faça o import do projeto
### Testar no postman (localmente):
Após ter o projeto rodando como servidor local clicando no botão abaixo faça o fork da collection do postman que possue as requisições prontas para testes, utilizando o environment OAuth2

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/32035818-50152931-86ca-4c38-9601-51030fa7e5e1?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D32035818-50152931-86ca-4c38-9601-51030fa7e5e1%26entityType%3Dcollection%26workspaceId%3Dc4e03449-5644-4b5f-8c74-d0cda8f79012#?env%5BOAuth2%5D=W3sia2V5IjoiYXNob3N0IiwidmFsdWUiOiJodHRwOi8vbG9jYWxob3N0OjgwODEiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MSIsImNvbXBsZXRlU2Vzc2lvblZhbHVlIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxIiwic2Vzc2lvbkluZGV4IjowfSx7ImtleSI6InJzaG9zdCIsInZhbHVlIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MCIsInNlc3Npb25JbmRleCI6MX0seyJrZXkiOiJjbGllbnQtaWQiLCJ2YWx1ZSI6Im15Y2xpZW50aWQiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Im15Y2xpZW50aWQiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6Im15Y2xpZW50aWQiLCJzZXNzaW9uSW5kZXgiOjJ9LHsia2V5IjoiY2xpZW50LXNlY3JldCIsInZhbHVlIjoibXljbGllbnRzZWNyZXQiLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6Im15Y2xpZW50c2VjcmV0IiwiY29tcGxldGVTZXNzaW9uVmFsdWUiOiJteWNsaWVudHNlY3JldCIsInNlc3Npb25JbmRleCI6M30seyJrZXkiOiJ1c2VybmFtZSIsInZhbHVlIjoiYWxleEBnbWFpbC5jb20iLCJlbmFibGVkIjp0cnVlLCJ0eXBlIjoiZGVmYXVsdCIsInNlc3Npb25WYWx1ZSI6ImFsZXhAZ21haWwuY29tIiwiY29tcGxldGVTZXNzaW9uVmFsdWUiOiJhbGV4QGdtYWlsLmNvbSIsInNlc3Npb25JbmRleCI6NH0seyJrZXkiOiJwYXNzd29yZCIsInZhbHVlIjoiMTIzNDU2IiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiIxMjM0NTYiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6IjEyMzQ1NiIsInNlc3Npb25JbmRleCI6NX0seyJrZXkiOiJ0b2tlbiIsInZhbHVlIjoiIiwiZW5hYmxlZCI6dHJ1ZSwidHlwZSI6ImRlZmF1bHQiLCJzZXNzaW9uVmFsdWUiOiIiLCJjb21wbGV0ZVNlc3Npb25WYWx1ZSI6IiIsInNlc3Npb25JbmRleCI6Nn1d)

Para testar as requisições com o token utilize na variavel de ambiente 'username': 
alex@gmail.com (admin),
maria@gmail.com (client)
### Exemplo de requisições
```HTTP 
POST http://localhost:8080/oauth2/token
```
response
```Json
{
    "access_token": "eyJraWQiOiJmN2I4ZWJjNi00MDBjLTQ2MWItOTFkYS03MTM0ZTA1NDJkNDQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJteWNsaWVudGlkIiwiYXVkIjoibXljbGllbnRpZCIsIm5iZiI6MTc1NDQwODY0MCwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzU0NDk1MDQwLCJpYXQiOjE3NTQ0MDg2NDAsImp0aSI6IjdmYzY5ZjRhLTE1OTctNGVjYS04Mjc2LWZkMzdiMmE4NzVlNiIsImF1dGhvcml0aWVzIjpbIlJPTEVfQ0xJRU5UIiwiUk9MRV9BRE1JTiJdLCJ1c2VybmFtZSI6ImFsZXhAZ21haWwuY29tIn0.C680q3NkyQ5sK_yceldl7rejxSPskD-MkDJk1iUgUWE-iUZ3xL8vVqulEd9xdtQrT9M97UKZcQDlR3RsB4fNVIjhGSs2lAJRpGg6tAT3x8I8dm9K98OL9kMAPupQH94hoV17ePyh5JZ6oZN0buI1YWLLURkA5JQ_pmK1_RrUm55FwsAan75nZpBgx_KA6linNW_qaikLKA8W4PUcXoFrOrvhLhqttLDTwjVAZdtDt2UP0K_FR5zmFyqC3p3mtfXLwpoBzYHkXetTi10Lkiy5CKRAbP30Igv2jHQMOU_991s2o0tszk7SUed0FwLanPOkgKlzYgr8m_lXh-6tA1OYkw",
    "token_type": "Bearer",
    "expires_in": 86399
}
```
```HTTP 
GET http://localhost:8080/products/1
```
response
```Json
{
    "id": 1,
    "name": "The Lord of the Rings",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
    "price": 90.5,
    "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
    "categories": [
        {
            "id": 1,
            "name": "Livros"
        }
    ]
}
```
```HTTP
GET http://localhost:8080/user/me
```
response
```Json
{
    "id": 2,
    "name": "Alex Green",
    "email": "alex@gmail.com",
    "phone": "977777777",
    "birthDate": "1987-12-13",
    "roles": [
        "ROLE_CLIENT",
        "ROLE_ADMIN"
    ]
}
```

# Domain model
![domain model image](https://github.com/user-attachments/assets/687b7794-42d9-48d8-be1f-6cb52a96c5c2)

# Considerações
Nesse projeto aprendi com o professor Nélio Alves a estruturar o Back-end de uma Web Application de maneira organizada e profissional. Pude entender com mais profundidade como funciona a relação Cliente-Servidor em uma aplicação web, a fazer as devidas distinções entre API e Back-end, a como configurar a segurança no lado do servidor utilizando OAuth2 com JWT, e pude ainda melhorar as minhas habiliades com o SQL e JPQL. Por isso deixo minhas considerações e agradecimentos a escola Devsuperior por todo o conteúdo ensinado nas aulas.

