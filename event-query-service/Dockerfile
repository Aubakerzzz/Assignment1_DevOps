# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/event-query-service.jar event-query-service.jar

# Expose the application port
EXPOSE 8080

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_INSTANCE_HOSTNAME=eureka
ENV ELASTICSEARCH_HOSTNAME=elasticsearch

# Run the application
ENTRYPOINT ["java", "-jar", "event-query-service.jar"]
