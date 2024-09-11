# Stage 1: Build the Spring Boot application
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Install git
RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*

# Clone the project repository
RUN git clone https://github.com/pinusdensiflora/knbteam1.git

# Navigate to the project folder
WORKDIR /app/knbteam1/inuri

# Build the application
RUN ./gradlew bootJar

# Stage 2: Set up Nginx and the Spring Boot application
FROM nginx:alpine

# Copy the Nginx configuration file
COPY nginx.conf /etc/nginx/nginx.conf

# Copy the Spring Boot application JAR from the build stage
COPY --from=build /app/knbteam1/inuri/build/libs/knbteam1-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose ports for Nginx and Spring Boot (if needed)
EXPOSE 80 8080

# Start both Nginx and Spring Boot
CMD ["sh", "-c", "java -jar /app/app.jar & nginx -g 'daemon off;'"]
