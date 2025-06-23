📅 API de Agendamento
📝 Descrição do Projeto
Este projeto consiste em uma API RESTful desenvolvida em Java com Spring Boot, projetada para gerenciar agendamentos de serviços. A API oferece endpoints para administradores gerenciarem o negócio e para clientes realizarem e consultarem agendamentos.
O sistema conta com uma lógica de negócios robusta para validar horários, evitar conflitos e garantir a integridade dos dados de agendamento.
✨ Funcionalidades (Features)
 * ✅ Gerenciamento Administrativo: Endpoints seguros para o proprietário do negócio.
 * ✅ Agendamento de Clientes: Operações de CRUD para agendamentos (criar, ler, atualizar, deletar).
 * ✅ Validação de Horários: Lógica para evitar agendamentos conflitantes.
 * ✅ Lógica de Negócios Centralizada: Um serviço (ScheduleServices) dedicado a tratar as regras de agendamento, como duração do atendimento e comparação de horários.
🛠️ Tecnologias Utilizadas
 * Linguagem: Java 17+
 * Framework: Spring Boot 3
 * Banco de Dados: (Ex: PostgreSQL, MySQL, H2)
 * Build Tool: Maven
 * Segurança: (Ex: Spring Security, JWT)
🏛️ Arquitetura
A API está estruturada em controladores que separam as responsabilidades e um serviço que encapsula as regras de negócio complexas.
 * AdminController.java: Responsável por todos os endpoints relacionados à administração do sistema. As funcionalidades aqui presentes são destinadas ao dono do negócio.
 * SchedulerController.java: Responsável pelos endpoints públicos ou de cliente, focados na criação e consulta de agendamentos.
 * ScheduleServices.java: Uma classe de serviço que contém toda a lógica de negócio para os agendamentos. Suas responsabilidades incluem:
   * Validar a disponibilidade de um horário.
   * Prevenir agendamentos duplos ou conflitantes.
   * Calcular e definir a duração de um atendimento.
   * Processar e preparar os dados antes de salvá-los no banco de dados.
🚀 Endpoints da API
A seguir, uma descrição dos principais endpoints disponíveis.
Admin Controller (/api/admin)
| Método | Endpoint | Descrição |
|---|---|---|
| GET | /dashboard | Retorna dados para o painel admin. |
| POST | /servicos | Cria um novo tipo de serviço. |
| PUT | /servicos/{id} | Atualiza um serviço existente. |
| GET | /agendamentos | Lista todos os agendamentos do dia/mês. |
Exemplo de corpo para POST /servicos:
{
  "nome": "Corte de Cabelo",
  "duracaoEmMinutos": 45,
  "valor": 50.00
}

Scheduler Controller (/api/schedules)
| Método | Endpoint | Descrição |
|---|---|---|
| POST | / | Cria um novo agendamento. |
| GET | / | Lista os horários disponíveis para um dia. |
| GET | /{id} | Busca os detalhes de um agendamento específico. |
| DELETE | /{id} | Cancela um agendamento. |
Exemplo de corpo para POST /:
{
  "idCliente": "uuid-do-cliente-123",
  "idServico": "uuid-do-servico-456",
  "dataHora": "2024-10-28T10:00:00"
}

Exemplo de resposta para GET / (horários disponíveis):
[
    "2024-10-28T09:00:00",
    "2024-10-28T11:00:00",
    "2024-10-28T14:00:00"
]

📋 Pré-requisitos
Antes de começar, você precisará ter as seguintes ferramentas instaladas em sua máquina:
 * JDK 17 ou superior
 * Maven
 * Um cliente de API, como Postman ou Insomnia.
▶️ Como Executar
 * Clone o repositório:
   git clone https://github.com/seu-usuario/seu-repositorio.git

 * Navegue até o diretório do projeto:
   cd seu-repositorio

 * Instale as dependências com o Maven:
   mvn clean install

 * Execute a aplicação:
   mvn spring-boot:run

A API estará disponível em http://localhost:8080.
🤝 Como Contribuir
Contribuições são o que tornam a comunidade de código aberto um lugar incrível para aprender, inspirar e criar. Qualquer contribuição que você fizer será muito apreciada.
 * Faça um Fork do projeto.
 * Crie uma Branch para sua feature (git checkout -b feature/AmazingFeature).
 * Faça o Commit de suas mudanças (git commit -m 'Add some AmazingFeature').
 * Faça o Push da Branch (git push origin feature/AmazingFeature).
 * Abra um Pull Request.
📄 Licença
Distribuído sob a licença MIT. Veja LICENSE para mais informações.

