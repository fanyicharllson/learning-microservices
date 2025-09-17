# Learning Microservices

A Spring Boot microservices project for learning purposes, demonstrating a simple e-commerce system architecture.

## Architecture

The project consists of three microservices:

- **Product Service** - Manages product catalog (MongoDB)
- **Order Service** - Handles order processing (MySQL)  
- **Inventory Service** - Tracks product inventory (MySQL)

## Technology Stack

- Java 21
- Spring Boot 3.5.5
- Maven
- MongoDB (Product Service)
- MySQL (Order & Inventory Services)
- Testcontainers for integration testing

## Build & Run

```bash
# Build all services
mvn clean compile

# Run tests
mvn test
```

## Services

| Service | Port | Database |
|---------|------|----------|
| Product Service | 8080 | MongoDB |
| Order Service | 8081 | MySQL |
| Inventory Service | 8082 | MySQL |