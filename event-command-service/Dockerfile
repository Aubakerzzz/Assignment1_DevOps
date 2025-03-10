# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/event-command-service.jar event-command-service.jar

# Expose the application port
EXPOSE 8081

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_INSTANCE_HOSTNAME=eureka
ENV POSTGRES_HOSTNAME=postgres

# Health check command (optional, for debugging in container logs)
HEALTHCHECK --interval=20s --timeout=5s --retries=5 --start-period=40s \
  CMD curl --fail --silent http://localhost:8081/actuator/health | grep UP || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "event-command-service.jar"]
