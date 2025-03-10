# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/eureka-server.jar eureka-server.jar

# Expose the Eureka server port
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
