FROM openjdk:21-jdk

# Directorios de trabajo para los servicios
WORKDIR /servicesClients
COPY clients/target/Clients-1.0-SNAPSHOT.jar clients.jar

WORKDIR /servicesAccounts
COPY accounts/target/accounts-1.0-SNAPSHOT.jar accounts.jar

# Copiar el script de inicio
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 8080
EXPOSE 8081

# Ejecutar el script que levanta ambos servicios
ENTRYPOINT ["/entrypoint.sh"]