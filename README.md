# Phone Records REST API

## Overview
This is a simple Java Spring Boot application that manages phone records (name and phone number) via a REST API. It persists the data in MySQL and validates phone numbers using [AbstractAPI](https://www.abstractapi.com/).

## Features
- Add phone records (name + phone number)
- Retrieve phone records by ID or fetch all
- Validation of phone numbers using AbstractAPI
- Persistence using MySQL

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

## 🛠 Technical Details

- **Framework**: Spring Boot
- **Database**: MySQL
- **Phone Validation**: AbstractAPI's Phone Intelligence API
- **Containerization**: Docker Compose

---