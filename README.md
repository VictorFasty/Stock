# Stock Management API 📦

O **Stock** é uma plataforma robusta para gerenciamento de estoques corporativos e multi-empresas. O sistema oferece uma API RESTful segura e documentada para controle de produtos, fluxo de entrada/saída e gestão administrativa, além de possuir integração com OAuth2 (Google) e sistema de alertas por e-mail.

## 🚀 Tecnologias & Arquitetura

* **Java 17+** & **Spring Boot 3**
* **Spring Security + OAuth2 Client** (Login Social com Google)
* **PostgreSQL** (Banco de dados relacional)
* **Spring Data JPA** (Persistência)
* **Design Patterns:** DTOs (Data Transfer Objects) e Mappers para desacoplamento.
* **SpringDoc OpenAPI** (Swagger UI para documentação viva).
* **Java Mail Sender** (Envio de notificações SMTP).

## ⚙️ Funcionalidades Principais

* **Gestão Multi-Empresas:** CRUD completo de empresas (`Enterprises`).
* **Controle de Estoque Inteligente:**
    * Cadastro de produtos vinculados a empresas.
    * Endpoints dedicados para **Adição** e **Baixa** de estoque (Entrada/Saída).
    * Busca paginada e ordenada de produtos e empresas.
* **Segurança (RBAC):**
    * Níveis de acesso: `ADMIN` e `USER`.
    * Proteção de rotas sensíveis (Ex: Apenas Admins criam Clients OAuth).
* **Interface Híbrida:**
    * API REST para consumo externo/frontend.
    * View de Login integrada (SSR) para acesso rápido.

## 🛠️ Configuração e Instalação

### Pré-requisitos
* JDK 17 ou superior
* Maven
* Banco de Dados PostgreSQL
* Conta no Google Cloud (para Credenciais OAuth2)

### 🔐 Variáveis de Ambiente (Obrigatório)

O projeto utiliza o profile `production` e não chumba senhas no código. Para rodar, você **precisa** definir as seguintes variáveis de ambiente no seu sistema ou na sua IDE:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `DATASOURCE_URL` | URL de conexão JDBC | `jdbc:postgresql://localhost:5432/stock_db` |
| `DATASOURCE_USERNAME` | Usuário do Banco | `postgres` |
| `DATASOURCE_PASSWORD` | Senha do Banco | `123456` |
| `GOOGLE_CLIENT_ID` | Client ID do Google Cloud | `123...apps.googleusercontent.com` |
| `GOOGLE_CLIENT_SECRET` | Client Secret do Google | `GOCSPX-...` |
| `EMAIL_USERNAME` | E-mail remetente (SMTP) | `seu-email@gmail.com` |
| `EMAIL_PASSWORD` | Senha de App do E-mail | `abcd 1234 ...` |
| `ALERT_RECIPIENT_EMAIL` | Quem recebe os alertas | `admin@empresa.com` |

### Executando Localmente

1.  Clone o repositório:
    ```bash
    git clone [https://github.com/seu-usuario/stock-api.git](https://github.com/seu-usuario/stock-api.git)
    ```
2.  Configure as variáveis de ambiente acima.
3.  Execute o projeto via Maven:
    ```bash
    mvn spring-boot:run
    ```
4.  Acesse a documentação da API (Swagger):
    * `http://localhost:8080/swagger-ui.html`

## 📍 Endpoints da API

Abaixo estão as principais rotas. Para detalhes dos payloads, consulte o Swagger.

### 🏢 Empresas (Enterprises)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/Enterprises/create` | Cria nova empresa |
| `GET` | `/Enterprises/findall` | Lista todas as empresas |
| `GET` | `/Enterprises/search/{name}` | Busca paginada por nome |
| `PUT` | `/Enterprises/update/{id}` | Atualiza dados da empresa |

### 📦 Produtos (Stock)
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/product/create` | Cadastra novo produto |
| `PUT` | `/product/A/{id}/{quantity}` | **Adiciona** quantidade ao estoque |
| `PUT` | `/product/R/{id}/{quantity}` | **Remove** quantidade do estoque |
| `GET` | `/product/search/{name}` | Busca de produtos |

### 👥 Usuários e Acesso
| Método | Rota | Descrição |
| :--- | :--- | :--- |
| `POST` | `/users/create` | Registra novo usuário |
| `POST` | `/clients` | Cria Client OAuth2 (Admin Only) |
| `GET` | `/login` | Página de Login (HTML) |

## 🤝 Contribuição

1.  Faça um Fork.
2.  Crie uma Branch (`git checkout -b feature/NovaFeature`).
3.  Commit suas mudanças.
4.  Abra um Pull Request.
