# Gradle Java Project

This project is a sample Java Spring Boot application built with Gradle. It demonstrates a complete CRUD API for managing users, featuring:

- **Spring Boot** for building REST APIs.
- **Spring Data JPA** for data persistence.
- **Flyway** for managing database migrations.
- **Spring Security** with public endpoints.
- **DTOs** for request/response abstraction.
- **Docker Compose** to run a PostgreSQL database.
- **H2 in-memory database** for testing.
- **JUnit 5** for unit and integration tests.
- **Gradle Build Scan (via Gradle Enterprise plugin)** for build insights and analysis.

## Features

- **User Management:** Create, retrieve, update, and delete users.
- **Database Migrations:** Use Flyway to create and manage the `users` table.
- **Security:** Configured to allow public access to the `/users` endpoints.
- **Dockerized Database:** Run PostgreSQL in a Docker container.
- **Test Suite:** Comprehensive tests using an in-memory H2 database and JUnit 5.
- **Build Insights:** Generate build scans to analyze build performance and dependencies.

## Technologies Used

- **Java 21**
- **Spring Boot 3.x**
- **Gradle**
- **PostgreSQL** (Dockerized)
- **H2 Database** (for testing)
- **Flyway**
- **Spring Security**
- **JUnit 5 & Mockito**
- **Docker Compose**
- **Gradle Enterprise Plugin** (for build scans)

## Prerequisites

- **Java Development Kit (JDK) 21** or higher.
- **Docker & Docker Compose** for running the PostgreSQL database.
- **Gradle** (or use the Gradle wrapper provided).

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository_url>
cd gradle-java-project
```

### 2. Dockerize PostgreSQL

Ensure you have Docker and Docker Compose installed. In the root directory, you should have a `docker-compose.yml` file similar to:

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

The main application configuration is located in `src/main/resources/application.yml`. This file contains the database connection settings pointing to your Dockerized PostgreSQL instance.

### 4. Build the Application

Run the following command to build your project:

```bash
./gradlew build
```

## Running the Application

You can run the Spring Boot application locally using:

```bash
./gradlew bootRun
```

The application will start on [http://localhost:8080](http://localhost:8080).

## API Endpoints

- **GET /users**  
  Retrieve all users.

- **GET /users/{id}**  
  Retrieve a specific user by ID.

- **POST /users**  
  Create a new user.  
  **Example Request (Postman):**
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

- **PUT /users/{id}**  
  Update an existing user.

- **DELETE /users/{id}**  
  Delete a user.

## Testing

The project uses an H2 in-memory database for integration tests. The test configuration is provided in `src/test/resources/application-test.yml`.

To run the tests:

```bash
./gradlew test
```

### Test Suite

A JUnit 5 test suite is provided (e.g., `AllTestsSuiteTest.java` in `src/test/java/com/gradleproject`) to run all tests at once.

## Gradle Build Scan

This project leverages Gradle Build Scans (via the Gradle Enterprise plugin) to provide insights into build performance, dependency analysis, and troubleshooting. To generate a build scan:

1. **Update Your Settings:**  
   The Gradle Enterprise plugin is applied in the `settings.gradle` file. Example configuration:

   ```gradle
   plugins {
       id 'com.gradle.enterprise' version '3.13.3'
   }

   gradleEnterprise {
       buildScan {
           termsOfServiceUrl = 'https://gradle.com/terms-of-service'
           termsOfServiceAgree = 'yes'
           tag 'backend-engineer'
       }
   }
   ```

2. **Generate a Build Scan:**  
   Run the following command to generate a build scan:

   ```bash
   ./gradlew build --scan
   ```

   Once the build completes, you will receive a URL where you can view detailed insights about your build, including the tag **backend-engineer**.

## Security Configuration

Spring Security is configured in the `SecurityConfig` class (`src/main/java/com/gradleproject/config/SecurityConfig.java`) to allow public access to the `/users` endpoints and disable CSRF protection for these endpoints.

## Project Structure

```
gradle-java-project/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/gradleproject/
│   │   │   │   ├── config/         # Security configuration
│   │   │   │   ├── controller/     # REST controllers
│   │   │   │   ├── dto/            # Data Transfer Objects (UserRequest, UserResponse)
│   │   │   │   ├── model/          # JPA entities (User)
│   │   │   │   ├── repository/     # Spring Data repositories
│   │   │   │   └── service/        # Service layer (UserService)
│   │   │   └── resources/
│   │   │       ├── application.yml # Main application configuration
│   │   │       └── db/migration/   # Flyway migration scripts
│   │   └── test/
│   │       ├── java/com/gradleproject/   # Test classes and suites
│   │       └── resources/
│   │           └── application-test.yml   # H2 database configuration for tests
├── docker-compose.yml       # Docker Compose file for PostgreSQL
└── build.gradle             # Gradle build file
```

## License

This project is licensed under the [MIT License](LICENSE).

## Author

[Your Name or Organization]

---

## Contact

**Dzmitry Ivaniuta** — [diafter@gmail.com](mailto:diafter@gmail.com) — [GitHub](https://github.com/DimitryIvaniuta)
