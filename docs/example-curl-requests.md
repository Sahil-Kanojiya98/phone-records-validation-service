## 📝 Curl Examples

---

### 1 Create a Valid Phone Record
```bash
curl -X POST http://localhost:8080/api/phones -H "Content-Type: application/json" -d '{"name":"John Doe","phoneNumber":"+14152000000"}'
```

### 2 Create a Valid Saved Phone Record (unique mobile number check)
```bash
curl -X POST http://localhost:8080/api/phones -H "Content-Type: application/json" -d '{"name":"John Doe","phoneNumber":"+14152000000"}'
```

### 3 Create an Invalid Phone Record
```bash
curl -X POST http://localhost:8080/api/phones -H "Content-Type: application/json" -d '{"name":"Jane Doe","phoneNumber":"123"}'
```

### 4 Get All Phone Records
```bash
curl http://localhost:8080/api/phones
```

### 5 Get Phone Record by Existing ID
```bash
curl http://localhost:8080/api/phones/1
```

### 6 Get Phone Record by Non-Existing ID
```bash
curl http://localhost:8080/api/phones/999
```