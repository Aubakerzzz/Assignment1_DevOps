# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/account-service.jar account-service.jar

# Expose the application port
EXPOSE 8084

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_INSTANCE_HOSTNAME=eureka
ENV POSTGRES_HOSTNAME=postgres
ENV REDIS_HOST=redis

# Run the application
ENTRYPOINT ["java", "-jar", "account-service.jar"]
