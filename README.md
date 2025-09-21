# Parking Management System

A backend REST API for managing parking lots, vehicles, entry gates, slot allocation, and payment receipts. Built with Spring Boot, PostgreSQL, and Testcontainers for integration testing.

## Features

- Parking lot and entry gate management
- Vehicle registration and lookup
- Optimal parking slot allocation based on entry gate and vehicle type
- Parking ticket issuance and retrieval
- Parking session completion and payment receipt generation
- Rule-based pricing engine
- Integration tests with Testcontainers and PostgreSQL

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA & JDBC
- PostgreSQL
- Testcontainers (for integration tests)
- Maven

## Project Structure

```
src/
  main/
    java/io/shashanksm/pms/         # Application code
    resources/                      # Main application properties
  test/
    java/io/shashanksm/pms/         # Integration tests
    resources/                      # Test configs and SQL
database/
  docker-compose.yml                # PostgreSQL dev environment
  initdb/                           # DB schema and seed data
```

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker (for local PostgreSQL)

### Running PostgreSQL Locally

Start the database using Docker Compose:

```sh
docker-compose -f database/docker-compose.yml up -d
```

This will start PostgreSQL on port `25432` with the default credentials as defined in the compose file.

### Build & Run

Build the project:

```sh
./mvnw clean package
```

Run the Spring Boot application:

```sh
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Running Tests

Integration tests use Testcontainers and will spin up a temporary PostgreSQL instance automatically:

```sh
./mvnw test
```

## API Endpoints

- `POST /api/parking-tickets`  
  Issue a new parking ticket (start a parking session).

- `GET /api/parking-tickets/{ticketId}`  
  Retrieve details of a parking ticket.

- (Planned) `POST /api/parking-receipts`  
  Complete a parking session and generate a payment receipt.

## Configuration

- Main DB config: [`src/main/resources/application.properties`](src/main/resources/application.properties)
- Test DB config: [`src/test/resources/application-test.properties`](src/test/resources/application-test.properties)

## Database Schema

See [`database/initdb/create_tables.sql`](database/initdb/create_tables.sql) for schema details.

## License

This project is for educational/demo purposes.

---

*Developed by Shashank S. M.*