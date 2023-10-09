FROM eclipse-temurin:17-jdk-alpine
# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file built by Gradle into the container
COPY build/libs/crud-kotlin-api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application will run on
EXPOSE 9090

# Command to run your Spring Boot application
CMD ["java", "-jar", "app.jar"]