# Maybank Assessment Application

A comprehensive Java Spring Boot REST API application with database integration, request/response logging, pagination, transactions, and external API integration.

## 🎯 Assessment Requirements Fulfilled

✅ **Java Spring Boot Application** - Built with Spring Boot 3.4.0 and Java 25  
✅ **Structured Project Architecture** - Clean 3-tier architecture (Controller → Service → Repository)  
✅ **REST API with Postman Collection** - Complete API documentation provided  
✅ **Request/Response Logging** - All API calls logged to `logs/request-response.log`  
✅ **Database Integration** - H2 in-memory database (zero setup required)  
✅ **@Transactional Implementation** - Applied to INSERT, UPDATE, GET methods  
✅ **Pagination (10 records per page)** - Implemented on GET endpoints  
✅ **External API Integration** - Nested API calls to JSONPlaceholder  

---

## 🚀 How to Run This Application

### **Step 1: Prerequisites**
Ensure you have the following installed:
- **Java 21 or higher** (Java 25 recommended)
  - Verify: `java -version`
- **Maven 3.8+**
  - Verify: `mvn -version`

> **Note**: No database setup required! The application uses H2 in-memory database which is automatically configured.

---

### **Step 2: Clone or Extract the Project**
If you received this as a ZIP file, extract it to your preferred location.

```bash
cd d:\Codebases\java-springboot-maybank-asssessment-application
```

---

### **Step 3: Build the Application**
Open a terminal in the project root directory and run:

```bash
mvn clean install
```

This will:
- Download all dependencies
- Compile the source code
- Run tests
- Create the executable JAR file

**Expected output**: `BUILD SUCCESS`

---

### **Step 4: Run the Application**

#### **Option A: Using Maven (Recommended)**
```bash
mvn spring-boot:run
```

#### **Option B: Using the JAR file**
```bash
java -jar target/assessment-application-1.0.0.jar
```

#### **Option C: Using Your IDE**
- Open the project in IntelliJ IDEA or Eclipse
- Navigate to `src/main/java/com/maybank/assessment/AssessmentApplication.java`
- Right-click and select **Run 'AssessmentApplication'**

**Application will start on**: `http://localhost:8080`

---

### **Step 5: Verify the Application is Running**

#### **Test 1: Health Check Endpoint**
Open your browser or use curl:
```bash
curl http://localhost:8080/api/health
```

**Expected Response**:
```json
{
  "status": "UP",
  "timestamp": "2025-11-24T10:30:00",
  "application": "Maybank Assessment Application",
  "version": "1.0.0"
}
```

#### **Test 2: Welcome Endpoint**
```bash
curl http://localhost:8080/api/welcome
```

**Expected Response**:
```json
{
  "message": "Welcome to Maybank Assessment Application",
  "timestamp": "2025-11-24T10:30:00"
}
```

---

### **Step 6: Test the APIs Using Postman**

1. **Open Postman**
2. Click **Import** button
3. Select the file: `Maybank_Assessment_API.postman_collection.json`
4. The collection contains all endpoints ready to test

#### **Recommended Testing Order**:

1. ✅ **Health Check** - `GET /api/health`
2. ✅ **Create Products** - `POST /api/products` (Create 15-20 products to test pagination)
3. ✅ **Get Paginated Products** - `GET /api/products/paginated?page=0&size=10` ⭐ **Requirement #6**
4. ✅ **Create Orders** - `POST /api/orders`
5. ✅ **Get Paginated Orders** - `GET /api/orders/paginated?page=0&size=10` ⭐ **Requirement #6**
6. ✅ **External API Integration** - `GET /api/integration/external-posts` ⭐ **Requirement #7**
7. ✅ **Nested API Calls** - `GET /api/integration/product-with-external/1` ⭐ **Requirement #7**

---

### **Step 7: Check Request/Response Logs** ⭐ **Requirement #4**

All API requests and responses are automatically logged to files:

```bash
# View Request/Response Logs (Windows)
type logs\request-response.log

# View Application Logs (Windows)
type logs\application.log
```

**Logs include**:
- Timestamp
- HTTP Method and URL
- Request Headers and Body
- Response Status and Body
- Processing Time

---

### **Step 8: Access H2 Database Console (Optional)**

To view the database contents:

1. Open browser: `http://localhost:8080/h2-console`
2. **JDBC URL**: `jdbc:h2:mem:testdb`
3. **Username**: `sa`
4. **Password**: (leave empty)
5. Click **Connect**

You can run SQL queries to verify data:
```sql
SELECT * FROM products;
SELECT * FROM orders;
```

---

## 📝 Quick Testing Guide for Interviewers

### **Test Pagination** (Requirement #6):
```bash
# Create 15 products first (use Postman collection)
# Then test pagination:
GET http://localhost:8080/api/products/paginated?page=0&size=10  # First page
GET http://localhost:8080/api/products/paginated?page=1&size=10  # Second page
```

### **Test External API Integration** (Requirement #7):
```bash
# Single nested API call:
GET http://localhost:8080/api/integration/external-posts

# Multiple nested API calls:
GET http://localhost:8080/api/integration/product-with-external/1
```

### **Test @Transactional** (Requirement #5):
- Create products/orders → Check database
- Update products → Verify rollback on errors
- Check logs for transaction boundaries

### **Test Request/Response Logging** (Requirement #4):
- Make any API call
- Check `logs/request-response.log`
- Verify complete request/response is logged

---

## 🛠️ Troubleshooting

### **Issue: Port 8080 already in use**
**Solution**: Change port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### **Issue: Maven build fails**
**Solution**: Clean and rebuild:
```bash
mvn clean install -U
```

### **Issue: Application won't start**
**Solution**: Check Java version:
```bash
java -version  # Should be Java 21 or higher
```

---

## 📡 Key API Endpoints

### Health & Status
- `GET /api/health` - Application health check
- `GET /api/welcome` - Welcome message

### Products (with Pagination)
- `GET /api/products/paginated?page=0&size=10` - **10 records per page** ⭐
- `POST /api/products` - Create product (@Transactional)
- `PUT /api/products/{id}` - Update product (@Transactional)
- `GET /api/products/{id}` - Get product by ID
- `DELETE /api/products/{id}` - Delete product

### Orders (with Pagination)
- `GET /api/orders/paginated?page=0&size=10` - **10 records per page** ⭐
- `POST /api/orders` - Create order (@Transactional)
- `GET /api/orders/{id}` - Get order by ID

### External API Integration
- `GET /api/integration/external-posts` - **Nested API call** ⭐
- `GET /api/integration/product-with-external/{id}` - **Multiple nested calls** ⭐

---

## 🔍 Key Features Implemented

- ✅ **3-Tier Architecture**: Controller → Service → Repository
- ✅ **Request/Response Logging**: Automatic logging to files with rotation
- ✅ **Pagination Support**: 10 records per page with sorting (Requirement #6)
- ✅ **Transaction Management**: @Transactional on all database operations (Requirement #5)
- ✅ **External API Integration**: RestTemplate for third-party API calls (Requirement #7)
- ✅ **Global Exception Handling**: Standardized error responses
- ✅ **Input Validation**: Jakarta Bean Validation
- ✅ **Connection Pooling**: HikariCP for optimal performance
- ✅ **H2 In-Memory Database**: Zero setup required for testing

---

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.4.0
- **Java Version**: Java 25 (compatible with Java 21+)
- **Database**: H2 In-Memory Database
- **ORM**: Spring Data JPA (Hibernate)
- **Logging**: Logback with SLF4J
- **HTTP Client**: RestTemplate
- **Build Tool**: Maven 3.x
- **Validation**: Jakarta Bean Validation

---

## 📚 Additional Documentation

For complete API documentation and implementation details:
- **[API_DOCUMENTATION.md](API_DOCUMENTATION.md)** - Comprehensive API documentation
- **[QUICK_SETUP_GUIDE.md](QUICK_SETUP_GUIDE.md)** - Quick setup instructions
- **[LOMBOK_SETUP.md](LOMBOK_SETUP.md)** - Lombok configuration guide
- **Postman Collection**: `Maybank_Assessment_API.postman_collection.json`

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
- `logs/application.log` - Application logs with rotation
- `logs/request-response.log` - Complete API request/response logs ⭐ **Requirement #4**

**Log Features**:
- Automatic log rotation (max 10MB per file)
- 30-day history retention
- Detailed request/response information
- Exception stack traces
- Transaction boundaries

---

**Developed for Maybank Assessment** | Spring Boot 3.4.0 | Java 25  
**Zero Setup Required** - H2 In-Memory Database | Ready to Run in 2 Minutes! 🚀

