# Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Set environment variables
ENV MYSQL_HOST=localhost
ENV MYSQL_PORT=3306
ENV MYSQL_DB=countrytickerappdb
ENV MYSQL_USER=userdb6
ENV MYSQL_PASSWORD=12345

# Set the working directory
WORKDIR /app

# Copy the JAR file
COPY country-ticker-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that the Spring Boot app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
