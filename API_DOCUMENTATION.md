# Maybank Assessment Application

A comprehensive Java Spring Boot REST API application demonstrating enterprise-level development practices including database connectivity, structured logging, pagination, transactions, and external API integration.

## 📋 Project Requirements Implemented

1. ✅ **Java Spring Boot Application** - Built with Spring Boot 3.4.0 and Java 21
2. ✅ **Structured Project Architecture** - Clean 3-tier architecture (Controller → Service → Repository)
3. ✅ **REST API with Postman Collection** - Complete API documentation and Postman collection provided
4. ✅ **Request/Response Logging** - All API requests and responses logged to `logs/request-response.log`
5. ✅ **MSSQL Database Integration** - Configured for MSSQL Server with connection pooling
6. ✅ **@Transactional Implementation** - Applied to all INSERT, UPDATE, and GET operations
7. ✅ **Pagination Support** - GET endpoints with 10 records per page
8. ✅ **External API Integration** - Nested API calls to JSONPlaceholder third-party API

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/maybank/assessment/
│   │   ├── AssessmentApplication.java          # Main application class
│   │   ├── config/
│   │   │   └── WebConfig.java                  # Web configuration (RestTemplate, Interceptors)
│   │   ├── controller/
│   │   │   ├── HealthController.java           # Health check endpoints
│   │   │   ├── ProductController.java          # Product CRUD + Pagination
│   │   │   ├── OrderController.java            # Order CRUD + Pagination
│   │   │   └── IntegrationController.java      # External API integration
│   │   ├── service/
│   │   │   ├── ProductService.java             # Product business logic with @Transactional
│   │   │   ├── OrderService.java               # Order business logic with @Transactional
│   │   │   └── ExternalApiService.java         # External API integration service
│   │   ├── repository/
│   │   │   ├── ProductRepository.java          # Product JPA repository
│   │   │   └── OrderRepository.java            # Order JPA repository
│   │   ├── entity/
│   │   │   ├── Product.java                    # Product entity
│   │   │   └── Order.java                      # Order entity
│   │   ├── dto/
│   │   │   ├── ProductRequest.java             # Product request DTO
│   │   │   ├── ProductResponse.java            # Product response DTO
│   │   │   ├── OrderRequest.java               # Order request DTO
│   │   │   ├── OrderResponse.java              # Order response DTO
│   │   │   └── ExternalApiResponse.java        # External API response DTO
│   │   ├── interceptor/
│   │   │   └── LoggingInterceptor.java         # Request/Response logging interceptor
│   │   ├── filter/
│   │   │   └── RequestResponseCachingFilter.java # Filter for caching request/response
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java     # Global exception handler
│   │       ├── ResourceNotFoundException.java  # Custom exception
│   │       ├── BadRequestException.java        # Custom exception
│   │       └── ErrorResponse.java              # Error response DTO
│   └── resources/
│       ├── application.properties              # Application configuration
│       └── logback-spring.xml                  # Logging configuration
└── test/
    └── java/com/maybank/assessment/            # Test classes
```

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.8+**
- **MSSQL Server** (Local installation)
- **Postman** (for API testing)

### Database Setup

1. Install MSSQL Server on your local machine
2. Create a database named `TESTDB`:
   ```sql
   CREATE DATABASE TESTDB;
   ```

3. Update database credentials in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=sa
   spring.datasource.password=yourPassword
   ```

### Running the Application

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd java-springboot-maybank-asssessment-application
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. The application will start at: `http://localhost:8080`

### Verify Application is Running

Visit: `http://localhost:8080/api/health`

Expected response:
```json
{
  "status": "UP",
  "timestamp": "2025-11-23T10:30:00",
  "application": "Maybank Assessment Application"
}
```

## 📊 Database Schema

The application automatically creates the following tables:

### Products Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated ID |
| name | VARCHAR(100) | Product name |
| description | VARCHAR(500) | Product description |
| price | DECIMAL(10,2) | Product price |
| stock_quantity | INTEGER | Available stock |
| category | VARCHAR(50) | Product category |
| active | BOOLEAN | Active status |
| created_at | DATETIME | Creation timestamp |
| updated_at | DATETIME | Last update timestamp |

### Orders Table
| Column | Type | Description |
|--------|------|-------------|
| id | BIGINT (PK) | Auto-generated ID |
| order_number | VARCHAR(50) | Unique order number |
| customer_name | VARCHAR(100) | Customer name |
| customer_email | VARCHAR(100) | Customer email |
| product_id | BIGINT (FK) | Reference to product |
| quantity | INTEGER | Order quantity |
| total_amount | DECIMAL(10,2) | Total order amount |
| status | VARCHAR(20) | Order status |
| notes | VARCHAR(500) | Order notes |
| created_at | DATETIME | Creation timestamp |
| updated_at | DATETIME | Last update timestamp |

## 📡 API Endpoints

### Health Check
- `GET /api/health` - Application health check
- `GET /api/welcome` - Welcome message

### Products (with @Transactional)
- `POST /api/products` - Create product (INSERT with @Transactional)
- `GET /api/products/{id}` - Get product by ID (GET with @Transactional)
- `GET /api/products` - Get all products
- `GET /api/products/paginated` - **Get products with pagination (10 per page)** ⭐
- `GET /api/products/active` - Get active products with pagination
- `GET /api/products/search` - Search products with pagination
- `PUT /api/products/{id}` - Update product (UPDATE with @Transactional)
- `DELETE /api/products/{id}` - Delete product

### Orders (with @Transactional)
- `POST /api/orders` - Create order (INSERT with @Transactional)
- `GET /api/orders/{id}` - Get order by ID (GET with @Transactional)
- `GET /api/orders/order-number/{orderNumber}` - Get order by order number
- `GET /api/orders` - Get all orders
- `GET /api/orders/paginated` - **Get orders with pagination (10 per page)** ⭐
- `GET /api/orders/customer/{email}` - Get orders by customer email with pagination
- `PATCH /api/orders/{id}/status` - Update order status (UPDATE with @Transactional)
- `DELETE /api/orders/{id}` - Delete order

### External API Integration (Nested API Calls) ⭐
- `GET /api/integration/external-posts` - **Fetch posts from external API (JSONPlaceholder)**
- `GET /api/integration/product-with-external/{id}` - **Get product + external data (nested calls)**
- `GET /api/integration/external-post/{id}` - Fetch single post from external API

## 📮 Using Postman Collection

1. Import the Postman collection: `Maybank_Assessment_API.postman_collection.json`

2. The collection includes:
   - Pre-configured base URL: `http://localhost:8080`
   - All API endpoints with sample requests
   - Proper headers and request bodies
   - Query parameters for pagination

3. Test the API endpoints in this order:
   - Health Check → Create Products → Get Products Paginated → Create Orders → External API Integration

## 🔍 Logging

### Log Files Location
All logs are stored in the `logs/` directory:

- `logs/application.log` - General application logs with rolling policy
- `logs/request-response.log` - Request/Response logs for all API calls

### Request/Response Logging Example
```json
{
  "timestamp": "2025-11-23T10:30:00",
  "type": "REQUEST",
  "method": "POST",
  "uri": "/api/products",
  "headers": {"Content-Type": "application/json"},
  "body": "{\"name\":\"Laptop\",\"price\":5499.99}"
}
```

### Log Configuration
- Rolling policy: Daily with 10MB max size per file
- Retention: 30 days
- Total size cap: 1GB
- Separate loggers for request/response and application logs

## 🔒 @Transactional Implementation

All database operations use `@Transactional`:

- **INSERT operations**: `@Transactional` on create methods
- **UPDATE operations**: `@Transactional` on update methods  
- **GET operations**: `@Transactional(readOnly = true)` on read methods

Example:
```java
@Transactional
public ProductResponse createProduct(ProductRequest request) {
    // Transactional insert operation
    Product savedProduct = productRepository.save(product);
    return mapToResponse(savedProduct);
}

@Transactional(readOnly = true)
public ProductResponse getProductById(Long id) {
    // Transactional read operation
    return productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
}
```

## 📄 Pagination Examples

### Get Products with Pagination (10 per page)
```http
GET http://localhost:8080/api/products/paginated?page=0&size=10&sortBy=name&sortDir=asc
```

Response:
```json
{
  "content": [...],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalPages": 5,
  "totalElements": 50,
  "last": false,
  "first": true
}
```

### Get Orders with Pagination
```http
GET http://localhost:8080/api/orders/paginated?page=0&size=10&sortBy=createdAt&sortDir=desc
```

## 🌐 External API Integration

The application demonstrates nested API calls:

### Example 1: Direct External API Call
```
Client (Postman) → Your API (/api/integration/external-posts) → JSONPlaceholder API
```

### Example 2: Combined Data from Multiple Sources
```
Client (Postman) → Your API (/api/integration/product-with-external/1) 
                 ↓
                 ├─→ Internal Database (Product)
                 └─→ External API (JSONPlaceholder Post)
```

The external API used is **JSONPlaceholder** (`https://jsonplaceholder.typicode.com`), a free fake API for testing.

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.4.0
- **Java Version**: 21
- **Database**: MSSQL Server with HikariCP connection pooling
- **ORM**: Hibernate JPA
- **Logging**: Logback with SLF4J
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Maven
- **External API Client**: RestTemplate

## 🧪 Testing the Application

1. **Health Check**:
   ```bash
   curl http://localhost:8080/api/health
   ```

2. **Create a Product**:
   ```bash
   curl -X POST http://localhost:8080/api/products \
     -H "Content-Type: application/json" \
     -d '{"name":"Laptop","description":"High-performance laptop","price":5499.99,"stockQuantity":50,"category":"Electronics","active":true}'
   ```

3. **Get Products with Pagination**:
   ```bash
   curl "http://localhost:8080/api/products/paginated?page=0&size=10"
   ```

4. **Test External API Integration**:
   ```bash
   curl http://localhost:8080/api/integration/external-posts
   ```

## 📝 Key Features Implemented

✅ **Clean Architecture**: Separation of concerns with Controller, Service, Repository layers  
✅ **Comprehensive Logging**: All requests/responses logged to dedicated files  
✅ **Database Transactions**: @Transactional on all database operations  
✅ **Pagination**: 10 records per page with sorting support  
✅ **Validation**: Input validation using Jakarta Bean Validation  
✅ **Exception Handling**: Global exception handler with standardized error responses  
✅ **External API Integration**: Nested API calls to third-party services  
✅ **Connection Pooling**: HikariCP for optimal database performance  
✅ **RESTful Design**: Following REST best practices  
✅ **Postman Collection**: Complete API documentation for testing  

## 📞 Support

For any questions or issues, please refer to the Postman collection or check the application logs in the `logs/` directory.

## 🎯 Assessment Checklist

- [x] Java Spring Boot application
- [x] Structured project for maintainability and readability
- [x] REST APIs testable via Postman with collection provided
- [x] Request/Response logging to log files
- [x] MSSQL database connection (TESTDB)
- [x] @Transactional implementation for INSERT, UPDATE, GET methods
- [x] Pagination with 10 records per page
- [x] External API integration with nested calls

---

**Maybank Assessment Application** - Developed with Spring Boot 3.4.0 and Java 21
