# Borges Scheduler API
## API REST desenvolvida com Spring Boot para o gerenciamento de agendamentos. 
### A aplicação conta com um sistema de autenticação de administradores baseado em JSON Web Tokens (JWT) para proteger endpoints específicos.

## ✨ Funcionalidades

### Autenticação: 
* Autenticação de administradores com e-mail e senha, retornando um token JWT.
### Gerenciamento de Administradores: 
* Criação de novos administradores (requer autenticação).
### Gerenciamento de Agendamentos:
* Criação de novos agendamentos (endpoint público).
* Listagem paginada de agendamentos (endpoint público).
* Filtragem de agendamentos por data.

## 🛠️ Tecnologias Utilizadas
* Java 17+
* Spring Boot 3
* Spring Security: 
Para controle de autenticação e autorização.
* Spring Data JPA: 
Para persistência de dados.
* Auth0 Java JWT: 
Para geração e validação de tokens.
* Lombok: 
Para reduzir código boilerplate em DTOs e entidades.
* Maven: 
Como gerenciador de dependências e build.
* Banco de Dados: MySQL.

## ⚙️ Pré-requisitos
* JDK 17 ou superior.
* Maven 3.8 ou superior.
* Uma instância de banco de dados (ex: MySQL) ou Docker para rodá-la.

## 🏁 Como Executar o Projeto
* Clone o repositório: ```git clone https://github.com/TheLastJedi00/BorgesScheduleApi```
* ```cd my-scheduler-api```
* Configure o ``application.properties``:
* Abra o arquivo ``src/main/resources/application.properties`` e configure as variáveis de ambiente, principalmente a conexão com o banco de dados e o segredo do token JWT.

## Configuração do Banco de Dados (ex: MySQL)
* `` spring.datasource.url=jdbc:mysql://localhost:3306/scheduler_db``
* ``spring.datasource.username=seu-usuario``
* ``spring.datasource.password=sua-senha``

### Configuração do Hibernate
* ``spring.jpa.hibernate.ddl-auto=update``

### Segredo para a geração do token JWT
* ``api.security.token.secret=${JWT_SECRET:meu-segredo-super-secreto}``
* Compile o projeto com o Maven:mvn clean install
* Execute a aplicação:mvn spring-boot:run
* A API estará disponível em http://localhost:8080.

# 📖 Endpoints da API
### A seguir estão os principais endpoints disponíveis na API.

## 🔑 Autenticação

### POST /login
Autentica um administrador e retorna um token JWT para ser usado nas requisições protegidas.
* Request Body: 
``{
"email": "admin@email.com",
"password": "senha"
}``
* Success Response (200 OK):
``{
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}``

## 👤 Administradores
### POST /admin
* Cria um novo administrador no sistema.
* Autenticação Requerida: Sim (ROLE_ADMIN)
* Request Body:
``{
"name": "Novo Admin",
"email": "novoadmin@email.com",
"password": "senha"
}``
* Success Response (201 Created): 
Retorna os detalhes do administrador criado.

## 🗓️ Agendamentos
### POST /agendamento
* Cria um novo agendamento.
* Autenticação Requerida: Não (Endpoint Público)
* Request Body:
``{
"name": "Nome do Cliente",
"phone": "11999998888",
"date": "2025-07-20T14:30:00",
"service": "Corte de Cabelo"
}``
* Success Response (201 Created): 
Retorna os detalhes do agendamento criado.

### GET /agendamento
* Lista os agendamentos de forma paginada.
* Autenticação Requerida: Não (Endpoint Público)
* Query Parameters:
  * date (opcional): Filtra agendamentos por uma data específica. Ex: ?date=2025-07-20T00:00:00
  * page (opcional): Número da página (inicia em 0).
  * size (opcional): Quantidade de itens por página.
  * sort (opcional): Campo para ordenação. Ex: ?sort=date,asc
* Success Response (200 OK): Retorna um objeto Page com a lista de agendamentos e informações de paginação.

### PUT /agendamento
* Atualiza as informações de um agendamento existente. 
* Permite atualizações parciais (só é preciso enviar os campos que deseja alterar junto com o id).
* Autenticação Requerida: Sim (Endpoint Privado)
* Request Body:
* JSON ``{
"id": 1,
"phone": "11912345678",
"date": "2025-07-20T15:00:00"
}•Success Response (200 OK): Retorna os detalhes completos do agendamento com as informações atualizadas.JSON{
"id": 1,
"name": "Nome do Cliente",
"phone": "11912345678",
"date": "2025-07-20T15:00:00",
"service": "Corte de Cabelo",
"endOfService": "2025-07-20T15:30:00",
"dayOfWeek": "SUNDAY"
}``
### DELETE /agendamento/{id}
* Realiza a exclusão lógica de um agendamento. 
* O registro não é removido fisicamente do banco, mas é marcado como inválido e não aparecerá mais nas listagens.
* Autenticação Requerida: Sim (Endpoint Privado)
* URL Parameter:
* * id (obrigatório): O ID do agendamento a ser excluído. Ex: /agendamento/1
* Success Response (204 No Content): A resposta não contém corpo (body), indicando que a operação foi bem-sucedida.

# 🏗️ Estrutura do Projeto
O projeto segue uma arquitetura em camadas para organizar as responsabilidades:
## controller: 
* Responsável por expor os endpoints da API (a camada de entrada). 
* Recebe as requisições HTTP, delega a lógica de negócio e formata as respostas.
## model / entidades: 
* Representa as entidades do domínio (Admin, Schedule) e o mapeamento para o banco de dados (JPA).
## repository: 
* Interfaces do Spring Data JPA que abstraem o acesso aos dados, fornecendo métodos para operações de CRUD.
## infra: 
* Camada de infraestrutura, contendo componentes que dão suporte à aplicação, mas não são a lógica de negócio principal.
## security: 
* Classes de configuração do Spring Security, o filtro de token e o serviço de geração/validação de JWT.
## services: 
* Classes de serviço que contêm a lógica de negócio mais complexa (ex: ScheduleFeatures).
## dto (Data Transfer Objects): 
* Objetos usados para modelar os dados que entram e saem da API, desacoplando a camada de controle da camada de modelo e garantindo que apenas os dados necessários sejam expostos.
