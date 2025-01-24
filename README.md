# Task Management App

## Overview
The **Task Management App** is a Spring Boot-based application designed to manage tasks, supporting features such as creating, editing, assigning, and commenting on tasks. This guide outlines the steps for setting up and deploying the application locally.

---

## Prerequisites
Ensure you have the following installed on your local machine:

- **Java 17** or later
- **PostgreSQL** (local instance or remote)
- **Maven** (for building the application)
- **Docker** (optional, for running PostgreSQL with Docker)

---

## Configuration

The application uses environment variables for configuration. The default values are specified in the `application.yml` file, and you can override them as needed.

### Default Configuration
```yaml
spring:
  application:
    name: task-management-app
  datasource:
    url: jdbc:postgresql://localhost:5432/task-management-db
    username: admin
    password: admin
  liquibase:
    enabled: true

server:
  port: 8080
```
## Local Deployment

### ⚙️ Step 1: Setup the Database

#### 🐳 Using Docker Compose
Navigate to the `build/docker-compose.yaml` file, then run the following command:
```bash
docker compose up -d
```
### ⚙️ Step 2: Build an application
Compile the application using Maven:
```bash
mvn clean install
```
### ▶️ Step 3: Run the Application
Start the application using one of the following methods:
1. Using the JAR file
```bash
java -jar target/task-management-app-0.0.1-SNAPSHOT.jar
```
2. Using Maven
```bash
mvn spring-boot:run
```
### 🌐 Step 4: Access the Application
After the application starts, you can access it at:
```angular2html
http://localhost:8080
```
### 🧪 Testing the APIs
The application provides an OpenAPI interface for testing its APIs. Once the application is running, you can access the Swagger UI here:
```angular2html
http://localhost:8080/swagger-ui/index.html
```
### 📦 Database Migrations
The application uses Liquibase for database migrations. It is enabled by default and will automatically apply any pending database migrations during the application startup.
### 🔧 Troubleshooting
1. Database Connection Issues:
Ensure that PostgreSQL is running and accessible.
Verify that the database credentials in application.yml or environment variables are correct.
2. Port Conflicts:
If port 8080 is already in use, override the port by setting the SERVER_PORT environment variable to a different value.