# Dscommerce
## Sobre o projeto
Esse projeto é um exemplo de uma aplicação back-end para um E-commerce. Desenvolvi-o durante o curso Spring Professional da escola Devsuperior. A medida que o conteúdo era passado eu implementava sozinho com a finalidade de reter as informações e técnicas ensinadas. Optei por fazer uso de Records ao invés de Classes para os DTOs.
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
### Testar no postman:

[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/32035818-50152931-86ca-4c38-9601-51030fa7e5e1?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D32035818-50152931-86ca-4c38-9601-51030fa7e5e1%26entityType%3Dcollection%26workspaceId%3Dc4e03449-5644-4b5f-8c74-d0cda8f79012)
# Dscommerce domain model
![domain model image](https://github.com/user-attachments/assets/687b7794-42d9-48d8-be1f-6cb52a96c5c2)


