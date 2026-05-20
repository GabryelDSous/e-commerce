# E-Commerce API

REST API for e-commerce management built with Spring Boot, Spring Security, and Spring Data JPA.

---

## Technologies

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Flyway

---

## Features

- User registration
- JWT authentication
- Login with email and password
- Product registration
- Purchase CRUD operations
- Database versioning using Flyway migrations

---

## Authentication

| Method | Endpoint | Description |
|---|---|---|
| POST | /users/register-user | Register a new user |
| POST | /users/login | Authenticate user |

---

## Users

| Method | Endpoint | Description |
|---|---|---|
| GET | /users/list-all | List all users |
| DELETE | /users/delete/{id} | Delete user (admin only) |

---

## Products

| Method | Endpoint | Description |
|---|---|---|
| POST | /products/register-product | Create product (admin only) |
| GET | /products/list-all | List all products |

---

## Purchases

| Method | Endpoint | Description |
|---|---|---|
| POST | /purchases/purchase | Create purchase |
| DELETE | /purchases/delete-id?id=uuid | Delete purchase |

---

## JSON Requests

### User Registration

```json
{
  "email": "your-email",
  "password": "your-password",
  "name": "your-name"
}
```

### Login

```json
{
  "email": "your-email",
  "password": "your-password"
}
```

### Product Registration

```json
{
  "name": "product-name",
  "description": "product description",
  "price": 1000.00,
  "stock": 30
}
```

### Purchase

```json
{
  "id": "product-uuid",
  "quantity": 4
}
```

---

## Project Architecture

- Model
- Repository
- DTO
- Mapper
- Exception Handler
- Service
- Controller

---

## How to Run the Project

### Requirements

- Java 21
- Maven
- PostgreSQL

### Clone the Project

```bash
git clone https://github.com/GabryelDSous/e-commerce.git
```

### Configure `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/database-name
spring.datasource.username=postgres
spring.datasource.password=your-password

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true

spring.flyway.enabled=true

jwt.secret=SECRET_WORD_TO_SIGN_JWT
```

### Run the Application

```bash
mvn spring-boot:run
```
