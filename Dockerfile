# Stage 1: Build stage
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copiar arquivos de build do Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Baixar dependências (cache de layers)
RUN ./mvnw dependency:go-offline -B

# Copiar o código fonte
COPY src ./src

# Compilar a aplicação e criar o JAR
RUN ./mvnw package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar wait-for-it para aguardar o PostgreSQL
RUN apk add --no-cache bash

# Copiar o JAR da stage de build
COPY --from=builder /app/target/*.jar app.jar

# Copiar script de inicialização
COPY start.sh .
RUN chmod +x start.sh

# Expor porta
EXPOSE 5000

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=30s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:5000/actuator/health || exit 1

# Comando de entrada
ENTRYPOINT ["./start.sh"]