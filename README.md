# Maybank Assessment Application

A comprehensive Java Spring Boot REST API application with MSSQL database integration, request/response logging, pagination, transactions, and external API integration.

## 🎯 Assessment Requirements Fulfilled

✅ **Java Spring Boot Application** - Built with Spring Boot 3.4.0 and Java 21  
✅ **Structured Project Architecture** - Clean 3-tier architecture (Controller → Service → Repository)  
✅ **REST API with Postman Collection** - Complete API documentation provided  
✅ **Request/Response Logging** - All API calls logged to `logs/request-response.log`  
✅ **MSSQL Database Integration** - Connected to local MSSQL `TESTDB`  
✅ **@Transactional Implementation** - Applied to INSERT, UPDATE, GET methods  
✅ **Pagination (10 records per page)** - Implemented on GET endpoints  
✅ **External API Integration** - Nested API calls to JSONPlaceholder  

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Maven 3.8+
- MSSQL Server (Local)
- Postman

### Setup Database
```sql
CREATE DATABASE TESTDB;
```

Update credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.username=sa
spring.datasource.password=yourPassword
```

### Run Application
```bash
mvn clean install
mvn spring-boot:run
```

Application starts at: `http://localhost:8080`

### Test Health Check
```bash
curl http://localhost:8080/api/health
```

### Import Postman Collection
Import `Maybank_Assessment_API.postman_collection.json` into Postman to test all endpoints.

## 📚 Documentation

For complete API documentation, setup instructions, and implementation details, see:
- **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** - Comprehensive documentation

## 📡 Key API Endpoints

### Products (with Pagination)
- `GET /api/products/paginated?page=0&size=10` - **10 records per page** ⭐
- `POST /api/products` - Create product (@Transactional)
- `PUT /api/products/{id}` - Update product (@Transactional)

### Orders (with Pagination)
- `GET /api/orders/paginated?page=0&size=10` - **10 records per page** ⭐
- `POST /api/orders` - Create order (@Transactional)

### External API Integration
- `GET /api/integration/external-posts` - **Nested API call** ⭐
- `GET /api/integration/product-with-external/{id}` - **Multiple nested calls** ⭐

## 🔍 Features

- **3-Tier Architecture**: Controller → Service → Repository
- **Request/Response Logging**: Automatic logging to files with rotation
- **Pagination Support**: 10 records per page with sorting
- **Transaction Management**: @Transactional on all database operations
- **External API Integration**: RestTemplate for third-party API calls
- **Global Exception Handling**: Standardized error responses
- **Input Validation**: Jakarta Bean Validation
- **Connection Pooling**: HikariCP for optimal performance

## 🛠️ Technology Stack

- Spring Boot 3.4.0
- Java 21
- MSSQL Server
- Spring Data JPA (Hibernate)
- Logback (Logging)
- RestTemplate (HTTP Client)
- Maven

## 📁 Project Structure

```
src/main/java/com/maybank/assessment/
├── controller/     # REST API endpoints
├── service/        # Business logic with @Transactional
├── repository/     # JPA repositories
├── entity/         # JPA entities (Product, Order)
├── dto/            # Request/Response DTOs
├── interceptor/    # Request/Response logging
├── exception/      # Exception handling
└── config/         # Application configuration
```

## 📝 Logs

All logs are stored in the `logs/` directory:
- `logs/application.log` - Application logs
- `logs/request-response.log` - API request/response logs

---

**Developed for Maybank Assessment** | Spring Boot 3.4.0 | Java 21

