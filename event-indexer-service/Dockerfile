# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/event-indexer-service.jar event-indexer-service.jar

# Expose the application port (though it's a non-web application)
EXPOSE 8086

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_INSTANCE_HOSTNAME=eureka
ENV ELASTICSEARCH_HOSTNAME=elasticsearch
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Run the application
ENTRYPOINT ["java", "-jar", "event-indexer-service.jar"]
