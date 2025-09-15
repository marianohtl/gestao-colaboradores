# Sistema de Gestão de Colaboradores

Sistema desenvolvido para gestão de colaboradores, departamentos e cargos de uma instituição bancária, servindo como base para futuros módulos como folha de pagamento e controle de ponto.

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.5.5** - Framework principal
- **PostgreSQL** - Banco de dados principal
- **Flyway** - Versionamento de banco de dados
- **Docker** - Containerização
- **JUnit 5 & Mockito** - Testes unitários
- **Springdoc OpenAPI** - Documentação da API

## 📋 Requisitos Funcionais

### Entidades
- **Employee**: ID, nome, email, departmentId, positionId, createdAt, status
- **Department**: ID, nome
- **Position**: ID, título

### Endpoints Principais
- `GET/POST /employees` - Gestão de colaboradores
- `GET/POST /departments` - Gestão de departamentos
- `GET/POST /positions` - Gestão de cargos
- `DELETE /employees/{id}` - Remoção de colaboradores
- `GET /employees/search?department={nome}` - Busca por departamento

## 🏗️ Arquitetura

### Hexagonal Architecture
O projeto segue os princípios da Arquitetura Hexagonal (Ports and Adapters):

```
src/
├── controllers/     → Adaptadores HTTP (primários)
├── services/        → Casos de uso e lógica de negócio
├── repositories/    → Portas de persistência (interfaces)
├── models/          → Domínio (entidades de negócio)
├── dtos/            → Objetos de transferência de dados
└── exceptions/      → Tratamento centralizado de erros
```

### Decisões Arquiteturais

1. **Domínio Isolado**: Models independentes de frameworks
2. **Inversão de Dependência**: Core não depende de implementações externas
3. **Testabilidade**: Facilidade para testes unitários e de integração
4. **Expansibilidade**: Preparado para incluir novos casos de uso

## 🚀 Como Executar Localmente

### Pré-requisitos
- Java 17+
- Maven 3.6+
- PostgreSQL 15+ (ou Docker)
- Docker (opcional)

### 1. Clone o repositório
```bash
git clone <repository-url>
cd gestao-colaboradores-api
```

### 2. Configure o banco de dados
```bash
# Com Docker (recomendado)
docker compose up -d postgres

# Ou manualmente:
# Crie um banco PostgreSQL chamado 'gestao_colaboradores_db'
```

### 3. Execute a aplicação
```bash
# Com Maven
./mvnw spring-boot:run

# Ou compile primeiro
./mvnw clean package
java -jar target/*.jar
```

### 4. Acesse a aplicação
- **API**: http://localhost:5000
- **Swagger UI**: http://localhost:5000/swagger-ui.html
- **API Docs**: http://localhost:5000/api-docs

## 🧪 Como Executar os Testes

```bash
# Executar todos os testes
./mvnw test

# Executar testes com cobertura
./mvnw test jacoco:report

# Executar testes específicos
./mvnw test -Dtest=EmployeeServiceTest
./mvnw test -Dtest=DepartmentServiceTest
./mvnw test -Dtest=PositionServiceTest
```

## 🐳 Execução com Docker

### Opção 1: Docker Compose completo
```bash
# Construir e executar tudo (app + PostgreSQL)
docker compose up --build

# Executar em background
docker compose up -d --build

# Parar containers
docker compose down
```

### Opção 2: Apenas banco com Docker
```bash
# Executar apenas PostgreSQL
docker-compose up -d postgres

# Executar aplicação localmente
./mvnw spring-boot:run
```

## 📊 Banco de Dados

### Migrações Automáticas
O Flyway executa automaticamente as migrações:
- `V1__create_tables.sql` - Criação das tabelas
- `V2__insert_initial_data.sql` - Dados iniciais
- `V3__create_indexes.sql` - Índices para performance

### Dados Iniciais
O sistema vem pré-populado com:
- **Departamentos**: TI, RH, Financeiro, Marketing, Operações
- **Cargos**: Engenheiro Júnior/Pleno/Sênior, Analistas, Gerentes
- **Colaboradores**: Dados de exemplo para testes

## 🤖 Uso de IA Generativa no Desenvolvimento

### Ferramentas Utilizadas
- **Deepseek** para arquitetura e implementação

## 📝 API Documentation

### Exemplo de Request/Response

**Criar Colaborador**:
```bash
curl -X POST http://localhost:5000/employees \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Joana Silva",
    "email": "joana@banco.com",
    "departmentId": "11111111-1111-1111-1111-111111111111",
    "positionId": "55555555-5555-5555-5555-555555555555"
  }'
```

**Listar Departamentos**:
```bash
curl http://localhost:5000/departments
```

## 🛠️ Configurações

### application.yaml
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gestao_colaboradores_db
    username: postgres
    password: password

springdoc:
  api-docs.path: /api-docs
  swagger-ui.path: /swagger-ui.html
```

### Variáveis de Ambiente
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/gestao_colaboradores_db
export SPRING_DATASOURCE_USERNAME=postgres
export SPRING_DATASOURCE_PASSWORD=password
```