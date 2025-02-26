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

## Features

- **User Management:** Create, retrieve, update, and delete users.
- **Database Migrations:** Use Flyway to create and manage the `users` table.
- **Security:** Configured to allow public access to the `/users` endpoints.
- **Dockerized Database:** Run PostgreSQL in a Docker container.
- **Test Suite:** Comprehensive tests using an in-memory H2 database and JUnit 5.

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

## Prerequisites

- **Java Development Kit (JDK) 21** or higher.
- **Docker & Docker Compose** for running the PostgreSQL database.
- **Gradle** (or use the Gradle wrapper provided).

## Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository_url>
cd gradle-java-project
