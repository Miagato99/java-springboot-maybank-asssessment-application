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
- **SQL Server Express** (for production database)
  - Download: [SQL Server Express](https://www.microsoft.com/en-us/sql-server/sql-server-downloads)

### **Step 1a: Database Configuration**

This application is configured to use **Microsoft SQL Server Express** as the primary database.

#### **Option A: Use MSSQL (Production Database)** ⭐ **Recommended**

1. **Enable TCP/IP Protocol** (Required for JDBC connection):
   - Open **SQL Server Configuration Manager**
   - Navigate to: `SQL Server Network Configuration` → `Protocols for SQLEXPRESS`
   - Right-click **TCP/IP** → Select **Enable**
   - Go to **IP Addresses** tab → Scroll to **IPALL** section
   - Set **TCP Port**: `1433` (clear TCP Dynamic Ports)
   - Click **Apply** → **OK**

2. **Restart SQL Server Service**:
   - Open Command Prompt **as Administrator**
   - Run:
   ```cmd
   net stop "MSSQL$SQLEXPRESS"
   net start "MSSQL$SQLEXPRESS"
   ```

3. **Enable SQL Server Authentication**:
   - Open Azure Data Studio or SQL Server Management Studio (SSMS)
   - Connect to `localhost\SQLEXPRESS`
   - Run the following SQL script:
   ```sql
   -- Enable Mixed Mode Authentication
   EXEC xp_instance_regwrite N'HKEY_LOCAL_MACHINE', 
        N'Software\Microsoft\MSSQLServer\MSSQLServer',
        N'LoginMode', REG_DWORD, 2;
   GO
   
   -- Enable SA account and set password
   ALTER LOGIN sa ENABLE;
   GO
   
   ALTER LOGIN sa WITH PASSWORD = '123456';
   GO
   ```
   - Restart SQL Server again (run the `net stop/start` commands above)

4. **Verify TCP/IP is Working**:
   ```cmd
   netstat -ano | findstr :1433
   ```
   You should see SQL Server listening on port 1433.

5. **Database Configuration** (Already configured in `application.properties`):
   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=TESTDB;encrypt=true;trustServerCertificate=true
   spring.datasource.username=sa
   spring.datasource.password=123456
   ```

> **Note**: The database `TESTDB` will be created automatically by Hibernate on first run.

#### **Option B: Use H2 In-Memory Database** (Quick Testing Only)

If you want to quickly test without setting up MSSQL:

1. Open `src/main/resources/application.properties`
2. Comment out the MSSQL configuration
3. Add H2 configuration:
   ```properties
   # H2 Database (In-Memory - for quick testing only)
   spring.datasource.url=jdbc:h2:mem:testdb
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=
   spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
   ```

> **Note**: H2 is in-memory only. All data will be lost when the application stops.

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

### **Step 8: Access Database Console**

#### **For MSSQL (Production)**:
Use Azure Data Studio or SQL Server Management Studio (SSMS):
1. Connect to: `localhost\SQLEXPRESS` or `localhost:1433`
2. Database: `TESTDB`
3. Username: `sa`
4. Password: `123456`

Run SQL queries to verify data:
```sql
SELECT * FROM products;
SELECT * FROM orders;
```

#### **For H2 (Testing Only)**:
1. Open browser: `http://localhost:8080/h2-console`
2. **JDBC URL**: `jdbc:h2:mem:testdb`
3. **Username**: `sa`
4. **Password**: (leave empty)
5. Click **Connect**

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

### **Issue: Cannot connect to MSSQL database**
**Symptoms**: Application fails to start with error: `The server SQLEXPRESS is not configured to listen with TCP/IP`

**Solution**:
1. Follow the **Step 1a** database configuration guide above
2. Verify TCP/IP is enabled and SQL Server is listening on port 1433:
   ```cmd
   netstat -ano | findstr :1433
   ```
3. Check SQL Server service is running:
   ```cmd
   sc query "MSSQL$SQLEXPRESS"
   ```
4. Verify SQL Authentication is enabled (Mixed Mode)
5. Restart SQL Server after any configuration changes

### **Issue: Login failed for user 'sa'**
**Solution**:
1. Enable SQL Server Authentication (Mixed Mode) - see Step 1a
2. Enable and set password for SA account using the SQL script provided
3. Restart SQL Server service
4. Update password in `application.properties` if different from `123456`

### **Issue: Database TESTDB does not exist**
**Solution**: The database will be created automatically by Hibernate on first run. If it doesn't:
```sql
CREATE DATABASE TESTDB;
GO
```

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
- ✅ **Production Database**: Microsoft SQL Server Express with TCP/IP
- ✅ **Request/Response Logging**: Automatic logging to files with rotation
- ✅ **Pagination Support**: 10 records per page with sorting (Requirement #6)
- ✅ **Transaction Management**: @Transactional on all database operations (Requirement #5)
- ✅ **External API Integration**: RestTemplate for third-party API calls (Requirement #7)
- ✅ **Global Exception Handling**: Standardized error responses
- ✅ **Input Validation**: Jakarta Bean Validation
- ✅ **Connection Pooling**: HikariCP for optimal performance
- ✅ **Database Options**: MSSQL (Production) or H2 (Quick Testing)

---

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.4.0
- **Java Version**: Java 25 (compatible with Java 21+)
- **Database**: Microsoft SQL Server Express (Production) / H2 In-Memory (Testing)
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
- **[enable-sql-tcp.md](enable-sql-tcp.md)** - Detailed SQL Server TCP/IP setup guide
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
**Production-Ready with Microsoft SQL Server Express** | Fully Configured & Tested! 🚀

