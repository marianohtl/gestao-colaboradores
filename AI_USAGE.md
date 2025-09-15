Uso de IA Generativa no Projeto

Ferramentas Utilizadas

    Deepseek para arquitetura e implementação

    Spring Boot 3.5 com Java 17 para desenvolvimento

    PostgreSQL com Flyway para migrações de banco

    Docker com multi-stage build para containerização

Prompts Efetivos
1. Definição da Arquitetura Hexagonal

Prompt: "Analisando a estrutura de pastas, essa é uma aplicação que segue os princípios da Arquitetura Hexagonal."

Resultado: Implementação da arquitetura hexagonal com controllers, services para casos de uso, repositories, e models como domínio isolado.

2. Implementação dos Serviços Base

Prompt: "Rescreva o department service seguindo essas especificações e este exemplo de service" [com exemplo completo do EmployeeService]

Resultado: Criação dos services DepartmentService e PositionService seguindo o mesmo padrão consistente do EmployeeService.

3. Configuração de Banco de Dados com Flyway

Prompt: "Quero que você crie o banco e o alimente usando exclusivamente postgres flyway"

Resultado: Implementação de migrações Flyway (V1, V2, V3) para criação de tabelas, inserção de dados iniciais e criação de índices.

4. Containerização com Docker Multi-Stage

Prompt: "Rescreva este dockerfile para separar em estágios: um para compilar e outro para executar"

Resultado: Dockerfile otimizado com multi-stage build, reduzindo a imagem final e melhorando a segurança.

5. Tratamento de Exceções Global

Prompt: "Quero que você monte um GlobalExceptionHandler"

Resultado: Implementação completa de exception handling centralizado com exceções customizadas e respostas padronizadas.

6. Resolução de Conflitos com Swagger

Prompt: "o swagger parou de funcionar após adicionar o global handler"

Resultado: Correção de conflitos entre GlobalExceptionHandler e Swagger através de configurações específicas e atualização do Springdoc.

7. Migração para YAML

Prompt: "Converta este arquivo para um application.yaml" [com propriedades do Springdoc]

Resultado: Conversão completa de application.properties para application.yaml com todas as configurações necessárias.

Decisões Influenciadas por IA

    Arquitetura Hexagonal escolhida para isolamento do domínio e testabilidade

    Flyway selecionado para versionamento de banco de dados em vez de Hibernate auto-DDL

    Multi-stage Docker build implementado para otimização de tamanho de imagem

    UUID como identificador principal para melhor escalabilidade

    GlobalExceptionHandler com exceções customizadas para tratamento consistente de erros

Limitações e Soluções

    Conflito de versões Spring: IA sugeriu @RestControllerAdvice que causou conflito - solucionado atualizando versão do springdoc

    Configuração Swagger: Código inicial não considerava conflitos com exception handlers - resolvido com verificação de paths do Swagger

    Dependências: IA não considerou versões específicas do Spring Boot 3.x - ajustado manualmente no pom.xml

    Docker networking: Script inicial não aguardava PostgreSQL ficar pronto - implementado healthcheck e wait-for
    
    Arquitetura: confusão entre arquitetura hexagonal e clean archtecture - solucionado apresentando exemplo consistente de arquitetura hexagonal
    
    Alucinação: códigos que fugiam dos requisitos do projeto ou códigos não utilizados - feita uma análise profunda nos códigos sugeridos


    Estrutura Final Implementada

```text

src/
├── main/
│   ├── java/br/com/gestao_colaboradores_api
│   │   ├── controllers/         # Adaptadores HTTP
│   │   ├── services/           # Casos de uso
│   │   ├── repositories/       # Portas de persistência
│   │   ├── models/            # Domínio
│   │   ├── dtos/              # Objetos de transferência
│   │   └── exceptions/        # Tratamento de erros
│   └── resources/
│       ├── db/migration/      # Migrações Flyway
│       └── application.yaml   # Configurações
├── test/                      # Testes
└── Dockerfile                 # Multi-stage build
```

Benefícios Obtidos com IA

    Produtividade: Desenvolvimento acelerado em 60% do tempo estimado

    Consistência: Padrões arquiteturais aplicados uniformemente

    Documentação: Código auto-documentado e comentado

    Boas práticas: Implementação seguindo standards da indústria

    Manutenibilidade: Código preparado para expansões futuras

Estatísticas de Uso

    ≈45 prompts executados

    ≈1200 linhas de código geradas/adaptadas

    100% de cobertura de requisitos funcionais

    3 horas de desenvolvimento (vs. 8 horas estimadas manualmente)