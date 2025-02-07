# Use Amazon Corretto JDK 17 as the base image
FROM amazoncorretto:17-alpine-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
# Assuming your Maven build creates a JAR file in the target directory with a name like "web-app.jar"
COPY target/web-app.jar /app/web-app.jar

# Expose the port that your Spring Boot application runs on (usually 8080)
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/web-app.jar"]

# Optionally, you can set environment variables
# ENV SPRING_PROFILES_ACTIVE=production