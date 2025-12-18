# Phone Records REST API

## Overview
A Spring Boot REST API for managing phone records with external phone number validation.

## Features

## Features

- Add phone records (name + phone number)
- Retrieve phone records by ID or fetch all
- Validation of phone numbers using AbstractAPI
- Persistence using MySQL
- Duplicate prevention - Each phone number can only be registered once (409 Conflict)
- Comprehensive error handling
- Docker support with Docker Compose
- RESTful API design

---

## 🔧 Configuration

The application is configured using **environment variables**.  
All required variables are documented in the `.env.demo` file.

---

### Setup Instructions

1. Copy the demo environment file:
   ```bash
   cp .env.demo .env
2. Open .env and replace the placeholder values with valid credentials.
3. Start the application using Docker Compose or your preferred runtime.

---

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.8**
- **Spring Data JPA**
- **MYSQL**
- **Maven**
- **Docker & Docker Compose**
- **Abstract API** for phone validation

---

## 🚀 Getting Started

### Running the Application

**Using Docker Compose**

To start the application:
```bash
docker compose up --build
```

By default:
- The Spring Boot application runs on `http://localhost:8080`.
- MySQL runs on port `3306`.

**Environment Configuration**  
The application's settings, including database and API configurations, are injected via environment variables.

To stop the application:
```bash
docker compose down
```

---

## 📦 API Endpoints

| **Method** | **Endpoint**         | **Description**                |
|------------|-----------------------|--------------------------------|
| `POST`     | `/api/phones`         | Create a phone record          |
| `GET`      | `/api/phones`         | Get all phone records          |
| `GET`      | `/api/phones/{id}`    | Get phone record by ID         |

---

## API Usage Examples

See [docs/curl-examples.md](docs/curl-examples.md) for detailed curl commands.

### Quick Examples

#### Create a Phone Record (Valid)
```bash
curl -X POST http://localhost:8080/api/phones \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "phoneNumber": "14158586273"
  }'
```

#### Get All Phone Records
```bash
curl http://localhost:8080/api/phones
```

#### Get Phone Record by ID
```bash
curl http://localhost:8080/api/phones/1
```

## Response Examples

### Success Response (201 Created)
```json
{
  "id": 1,
  "name": "John Doe",
  "phoneNumber": "14158586273"
}
```

### Error Response (400 Bad Request - Invalid Phone)
```json
{
  "timestamp": "2024-12-17T10:30:00",
  "status": 400,
  "error": "PHONE_RECORD_VALIDATION_FAILED",
  "message": "Invalid phone number: 1234567890. Reason: Phone number validation failed"
}
```

### Error Response (409 Conflict - Duplicate Phone)
```json
{
  "timestamp": "2024-12-17T10:30:00",
  "status": 409,
  "error": "PHONE_ALREADY_EXISTS",
  "message": "Phone number '14158586273' already exists with ID: 1"
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2024-12-17T10:30:00",
  "status": 404,
  "error": "PHONE_RECORD_NOT_FOUND",
  "message": "Phone record not found with id: 999"
}
```

---