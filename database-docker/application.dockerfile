#COMPILLER
FROM maven:latest AS build

WORKDIR /app

# Arquivo de configuração
COPY pom.xml .

COPY src ./src

RUN mvn clean install

FROM openjdk

WORKDIR /app

COPY --from=build /app/target/smart-wallet-backend-0.0.1-SNAPSHOT.jar ./smartwallet

EXPOSE 8080

CMD ["java", "-jar", "smartwallet.jar"]
