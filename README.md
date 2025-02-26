# Gradle Java Project

This is a sample Spring Boot application built with Gradle. It demonstrates a complete CRUD API for managing users, using:

- **Spring Boot** for REST API creation.
- **Spring Data JPA** for database persistence.
- **Flyway** for database migrations.
- **Spring Security** with public endpoints.
- **DTOs** for request/response abstraction.
- **Docker Compose** to run a PostgreSQL database.
- **H2** in-memory database for integration tests.
- **JUnit 5** for unit and integration testing.

---

## Features

- **User Management:** Create, read, update, and delete users.
- **Database Migrations:** Managed via Flyway (migrations in `src/main/resources/db/migration`).
- **Security:** Configured with Spring Security to allow public access to `/users` endpoints.
- **Dockerized PostgreSQL:** Easily run PostgreSQL using Docker Compose.
- **Integration Testing:** Uses H2 in-memory database for tests.

---

## Technologies Used

- Java 21
- Spring Boot 3.x
- Gradle
- PostgreSQL (via Docker Compose)
- H2 Database (for testing)
- Flyway
- Spring Security
- JUnit 5 & Mockito
- Docker Compose

---

## Prerequisites

- **Java Development Kit (JDK) 21** or higher.
- **Docker & Docker Compose** to run the PostgreSQL database.
- **Gradle** (or use the Gradle wrapper provided).

---

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository_url>
cd gradle-java-project
```

### 2. Dockerize PostgreSQL

Ensure Docker and Docker Compose are installed. The repository contains a `docker-compose.yml` file similar to:

```yaml
version: "3.8"

services:
  db:
    image: postgres:latest
    container_name: postgres_db
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: mysecretpassword
      POSTGRES_DB: mydatabase
    ports:
      - "5437:5432"
```

Start the PostgreSQL container:

```bash
docker-compose up -d
```

### 3. Configure the Application

The main application configuration is in `src/main/resources/application.yml`. This file contains database connection settings that point to your Dockerized PostgreSQL instance.

### 4. Build the Application

Run the following command to build your project:

```bash
./gradlew build
```

---

## Running the Application

To run the Spring Boot application locally, execute:

```bash
./gradlew bootRun
```

The application will start on [http://localhost:8080](http://localhost:8080).

---

## API Endpoints

### Get All Users

- **URL:** `GET /users`
- **Description:** Retrieve all users.

### Get User by ID

- **URL:** `GET /users/{id}`
- **Description:** Retrieve a specific user by ID.

### Create User

- **URL:** `POST /users`
- **Description:** Create a new user.
- **Request Example (Postman):**
    - **Method:** POST
    - **URL:** `http://localhost:8080/users`
    - **Headers:** `Content-Type: application/json`
    - **Body:**
      ```json
      {
        "username": "jane_doe",
        "email": "jane@example.com",
        "password": "secret"
      }
      ```

### Update User

- **URL:** `PUT /users/{id}`
- **Description:** Update an existing user.

### Delete User

- **URL:** `DELETE /users/{id}`
- **Description:** Delete a user.

---

## Testing

### Integration Tests with H2

An in-memory H2 database is configured for tests using the file `src/test/resources/application-test.yml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    database-platform: org.hibernate.dialect.H2Dialect
  flyway:
    enabled: false
```

To run tests:

```bash
./gradlew test
```

### Test Suite

A JUnit 5 test suite is provided (e.g., `AllTestsSuiteTest.java` in `src/test/java/com/gradleproject`) to run all tests at once.

---

## Security Configuration

Spring Security is configured in `src/main/java/com/gradleproject/config/SecurityConfig.java` to:

- Disable CSRF protection.
- Permit all requests to `/users` endpoints.
- Require authentication for other endpoints.

Example configuration:

```java
package com.gradleproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
```

---

## Project Structure

```
gradle-java-project/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/gradleproject/
│   │   │   │   ├── config/         # Security configuration
│   │   │   │   ├── controller/     # REST controllers
│   │   │   │   ├── dto/            # Request/response DTOs (UserRequest, UserResponse)
│   │   │   │   ├── model/          # JPA entities (User)
│   │   │   │   ├── repository/     # Spring Data repositories
│   │   │   │   └── service/        # Service layer (UserService)
│   │   │   └── resources/
│   │   │       ├── application.yml # Main application configuration
│   │   │       └── db/migration/   # Flyway migration scripts
│   │   └── test/
│   │       ├── java/com/gradleproject/   # Test classes and suites
│   │       └── resources/
│   │           └── application-test.yml   # H2 test configuration
├── docker-compose.yml       # Docker Compose file for PostgreSQL
└── build.gradle             # Gradle build file
```

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

**Dzmitry Ivaniuta** — [diafter@gmail.com](mailto:diafter@gmail.com) — [GitHub](https://github.com/DimitryIvaniuta)
