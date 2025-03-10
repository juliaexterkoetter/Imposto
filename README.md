# Imposto
 Realiza o cálculo de impostos

1. 
Modelagem dos Dados:
• Entidade Imposto:
Criada a classe Imposto que representa um tipo de imposto, com os atributos id, nome (como String, permitindo cadastro dinâmico), descricao e aliquota. Essa abordagem dinâmica possibilita que novos impostos sejam cadastrados sem a necessidade de alterar o código.
• Entidade User:
Desenvolvida a classe User para representar os usuários da API, contendo id, username, password (armazenada de forma segura com BCrypt) e role (para definir se o usuário é ADMIN ou USER).
2. 
Transferência de Dados (DTOs):
• Criação de DTOs para separar a representação dos dados na API da camada de persistência. Isso inclui:
• ImpostoDTO: para entrada e saída dos dados de impostos.
• CalculoImpostoRequestDTO e CalculoImpostoDTO: para realizar e retornar o cálculo do imposto.
• UserRegistrationDTO e AuthResponseDTO: para registro de usuários e retorno do token JWT no login.
3. 
Persistência:
• Repositórios:
Criadas interfaces ImpostoRepository e UserRepository que estendem JpaRepository, possibilitando as operações CRUD nos impostos e nos usuários.
4. 
Lógica de Negócio (Serviços):
• ImpostoService:
Implementados métodos para listar, obter por ID, criar e excluir impostos. Também   inclui-se a lógica para calcular o valor do imposto com base na alíquota.
• UserService:
Responsável pelo registro de novos usuários (incluindo a codificação da senha) e pela busca de usuários pelo username.
5. 
APIs RESTful (Controllers):
• ImpostoController:
Gerencia os endpoints de impostos, permitindo:
• Listar todos os impostos (GET /tipos)
• Obter detalhes de um imposto específico (GET /tipos/{id})
• Cadastrar novos impostos (POST /tipos) – restrito a ADMIN.
• Excluir um imposto (DELETE /tipos/{id}) – restrito a ADMIN.
• CalculoController:
Disponibiliza o endpoint para cálculo do imposto com base no ID do imposto e no valor base fornecido (POST /calculo), também restrito a ADMIN.
• UserController:
Oferece endpoints para registro (POST /user/register) e autenticação (POST /user/login) de usuários, retornando um token JWT para autenticação.
6. 
Segurança:
• JWT e Spring Security:
Configuramos o Spring Security para proteger os endpoints, utilizando JWT para autenticação.
• JwtTokenProvider: Gera e valida os tokens JWT.
• JwtAuthenticationFilter: Intercepta as requisições, extrai e valida o token, e define a autenticação no contexto do Spring.
• SecurityConfig: Configura as regras de acesso – endpoints públicos (registro, login e documentação Swagger) e endpoints restritos a ADMIN para criação, exclusão e cálculo de impostos.
7. 
Documentação com Swagger (Springdoc OpenAPI):
• SwaggerConfig:
Configurado um bean OpenAPI que define os metadados da API (título, versão e descrição).
Com essa configuração, a documentação interativa fica disponível via Swagger UI em http://localhost:8080/swagger-ui/index.html.
8. 
Configurações do Ambiente:
• Banco de Dados:
• Para produção, utiliza-se PostgreSQL (configurado no application.properties).
• Para testes, utiliza-se o banco de dados H2 em memória (configurado no application-test.properties), permitindo a execução de testes sem depender de um banco real.
• Testes:
Desenvolvidos testes unitários e de integração (utilizando TDD, TestRestTemplate ou MockMvc) para garantir a robustez da API e a cobertura de código, integrando o uso do H2 para testes.
