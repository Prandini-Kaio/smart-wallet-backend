# Use uma imagem base do Java 17 ou mais recente
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo JAR para o contêiner
COPY target/smart-wallet-backend-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080
EXPOSE 8080 5005

# Comando para executar a aplicação
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005", "-jar", "app.jar", "--spring-profiles.active=db-local"]

