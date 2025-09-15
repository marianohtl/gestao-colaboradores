#!/bin/bash

echo "Aguardando PostgreSQL ficar disponível..."
# Usando netcat para verificar se a porta 5432 está aberta
while ! nc -z postgres 5432; do
  sleep 2
  echo "Aguardando PostgreSQL..."
done

echo "PostgreSQL está pronto!"
echo "Executando migrações Flyway..."

# Executar migrações Flyway
java -jar app.jar --spring.flyway.enabled=true --spring.flyway.baseline-on-migrate=true --spring.main.web-application-type=none

echo "Migrações Flyway concluídas!"
echo "Iniciando aplicação Spring Boot..."

# Iniciar a aplicação
exec java -jar app.jar --spring.flyway.enabled=false