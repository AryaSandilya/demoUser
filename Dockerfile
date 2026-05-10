# Use a lightweight JDK 17 image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your target folder into the container
COPY target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8081"]