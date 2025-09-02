FROM openjdk:17-jdk-slim

WORKDIR /app

# Copier les fichiers de configuration Maven
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Télécharger les dépendances (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copier le code source
COPY src src

# Construire l'application
RUN ./mvnw clean package -DskipTests

# Exposer le port
EXPOSE 8080

# Commande de démarrage
CMD ["java", "-jar", "target/map_of_france-0.0.1-SNAPSHOT.jar"]
