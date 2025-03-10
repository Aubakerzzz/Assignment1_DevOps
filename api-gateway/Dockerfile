# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/api-gateway.jar api-gateway.jar

# Expose the application port
EXPOSE 9000

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=prod
ENV EUREKA_INSTANCE_HOSTNAME=eureka

# Run the application
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
