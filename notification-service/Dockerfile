# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file
COPY target/notification-service.jar notification-service.jar

# Expose the application port
EXPOSE 8085

# Set environment variables (optional, can be overridden in Docker Compose)
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
ENV SERVERS_ACCOUNT_SERVICE=http://account-service:8084
ENV SERVERS_EVENT_SERVICE=http://event-query-service:8080
ENV EUREKA_INSTANCE_HOSTNAME=eureka

# Run the application
CMD ["java", "-jar", "notification-service.jar"]
