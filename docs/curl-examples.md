# 📝 Guide: cURL Examples and Exception Handling in Phone Records Validation Service

This guide covers:
- Example `cURL` commands to interact with Phone Records REST API
- Expected responses for valid and invalid interactions
- Exception handling in the Phone Records Validation Service

---

## 📌 cURL Examples for API Testing

---

### 1. Create a Valid Phone Record
```bash
curl -X POST http://localhost:8080/api/phones \
-H "Content-Type: application/json" \
-d '{"name":"John Doe","phoneNumber":"+14152000000"}'
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "John Doe",
  "phoneNumber": "+14152000000"
}
```

---

### 2. Create a Valid Saved Phone Record (Unique Mobile Number Check)
```bash
curl -X POST http://localhost:8080/api/phones \
-H "Content-Type: application/json" \
-d '{"name":"John Doe","phoneNumber":"+14152000000"}'
```

**Response (409 Conflict):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 409,
  "error": "PHONE_ALREADY_EXISTS",
  "message": "A phone record with this number already exists"
}
```

---

### 3. Create an Invalid Phone Record (internal validation)
```bash
curl -X POST http://localhost:8080/api/phones \
-H "Content-Type: application/json" \
-d '{"name":"Jane Doe","phoneNumber":"+19875745"}'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 400,
  "error": "VALIDATION_FAILED",
  "fieldErrors": {
    "phoneNumber": "Phone number must be in international format, e.g., +919875857545"
  },
  "message": "VALIDATION_FAILED"
}
```

### 4. Create an Invalid Phone Record (external validation api)
```bash
curl -X POST http://localhost:8080/api/phones \
-H "Content-Type: application/json" \
-d '{"name":"Jane Doe","phoneNumber":"+19875191887"}'
```

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 400,
  "error": "PHONE_RECORD_VALIDATION_FAILED",
  "message": "Invalid phone number: +19875191887"
}
```

---

### 5. Get All Phone Records
```bash
curl http://localhost:8080/api/phones
```

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "name": "John Doe",
    "phoneNumber": "+14152000000"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "phoneNumber": "+19876543210"
  }
]
```

---

### 6. Get Phone Record by Existing ID
```bash
curl http://localhost:8080/api/phones/1
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "John Doe",
  "phoneNumber": "+14152000000"
}
```

---

### 7. Get Phone Record by Non-Existing ID
```bash
curl http://localhost:8080/api/phones/999
```

**Response (404 Not Found):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 404,
  "error": "PHONE_RECORD_NOT_FOUND",
  "message": "No phone record found for the given ID"
}
```

---

## 🔧 Exception Handling Overview

Here are the main exceptions handled by the service and their corresponding response formats:

---

### 1. Validation Errors
**Exception Class:** `MethodArgumentNotValidException`

**Response (400 Bad Request):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 400,
  "error": "VALIDATION_FAILED",
  "fieldErrors": {
    "fieldName": "Error message for the field"
  },
  "message": "VALIDATION_FAILED"
}
```

---

### 2. Record Not Found
**Exception Class:** `PhoneNotFoundException`

**Response (404 Not Found):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 404,
  "error": "PHONE_RECORD_NOT_FOUND",
  "message": "No phone record found for the given ID"
}
```

---

### 3. Validation Service Unavailable
**Exception Class:** `PhoneValidationServiceUnavailableException`

**Response (503 Service Unavailable):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 503,
  "error": "SERVICE_UNAVAILABLE",
  "message": "Phone validation service is temporarily unavailable"
}
```

---

### 4. Duplicate Phone Records
**Exception Class:** `PhoneAlreadyExistsException`

**Response (409 Conflict):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 409,
  "error": "PHONE_ALREADY_EXISTS",
  "message": "A phone record with this number already exists"
}
```

---

### 5. Generic/Internal Server Errors
**Exception Class:** `Exception`

**Response (500 Internal Server Error):**
```json
{
  "timestamp": "2025-12-18T12:00:00",
  "status": 500,
  "error": "INTERNAL_SERVER_ERROR",
  "message": "Unexpected server error occurred"
}
```

---

## 📚 DTO Classes for API and Exception Responses

### 1. `PhoneRecordResponse`
- Represents the response schema for a valid phone record.

```java
public class PhoneRecordResponse {
    private Long id;
    private String name;
    private String phoneNumber;

    // Constructor, getters, and setters omitted for brevity
}
```

---

### 2. `ErrorResponse`
- Represents the response schema for error cases.

```java
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private Map<String, String> fieldErrors;

    // Constructors, getters, and setters omitted for brevity
}
```

---

This concludes the guide for API testing using `cURL` and understanding the exception handling implemented in the Phone Records Validation Service.