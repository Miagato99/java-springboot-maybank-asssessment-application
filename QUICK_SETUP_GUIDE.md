# Quick Setup Guide - Maybank Assessment Application

This guide will help you set up and run the application quickly.

## Step 1: Database Setup

### Option A: Using SQL Server Management Studio (SSMS)
1. Open SSMS and connect to your local SQL Server
2. Open the file `database_setup.sql`
3. Execute the script (F5) to create the `TESTDB` database

### Option B: Using Command Line
```bash
sqlcmd -S localhost -U sa -P yourPassword -i database_setup.sql
```

### Option C: Manual Setup
```sql
CREATE DATABASE TESTDB;
```

## Step 2: Configure Database Connection

1. Open `src/main/resources/application.properties`
2. Update the database credentials:
   ```properties
   spring.datasource.username=sa
   spring.datasource.password=yourPassword
   ```

**Note**: Replace `yourPassword` with your actual SQL Server password.

## Step 3: Build and Run

### Build the project:
```bash
mvn clean install
```

### Run the application:
```bash
mvn spring-boot:run
```

**Alternative**: Run from your IDE (Eclipse/IntelliJ):
- Open the project
- Run `AssessmentApplication.java` as Java Application

## Step 4: Verify Application is Running

Open your browser or use curl:
```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{
  "status": "UP",
  "timestamp": "2025-11-23T10:30:00",
  "application": "Maybank Assessment Application"
}
```

## Step 5: Import Postman Collection

1. Open Postman
2. Click **Import**
3. Select `Maybank_Assessment_API.postman_collection.json`
4. The collection will be imported with all endpoints

## Step 6: Test the APIs

### Test Order (Recommended):

1. **Health Check** - Verify application is running
   ```
   GET http://localhost:8080/api/health
   ```

2. **Create Products** - Add some products first
   ```
   POST http://localhost:8080/api/products
   Body: {
     "name": "Laptop Dell XPS 15",
     "description": "High-performance laptop",
     "price": 5499.99,
     "stockQuantity": 50,
     "category": "Electronics",
     "active": true
   }
   ```

3. **Get Products with Pagination** - Test pagination (Requirement #6)
   ```
   GET http://localhost:8080/api/products/paginated?page=0&size=10
   ```

4. **Create Order** - Place an order
   ```
   POST http://localhost:8080/api/orders
   Body: {
     "customerName": "John Doe",
     "customerEmail": "john.doe@example.com",
     "productId": 1,
     "quantity": 2,
     "notes": "Urgent delivery"
   }
   ```

5. **Get Orders with Pagination** - View orders
   ```
   GET http://localhost:8080/api/orders/paginated?page=0&size=10
   ```

6. **External API Integration** - Test nested API call (Requirement #7)
   ```
   GET http://localhost:8080/api/integration/external-posts
   ```

7. **Product with External Data** - Test multiple nested API calls
   ```
   GET http://localhost:8080/api/integration/product-with-external/1
   ```

## Step 7: Check Logs

All logs are automatically created in the `logs/` directory:

### View Request/Response Logs:
```bash
# Windows
type logs\request-response.log

# Linux/Mac
cat logs/request-response.log
```

### View Application Logs:
```bash
# Windows
type logs\application.log

# Linux/Mac
cat logs/application.log
```

## Troubleshooting

### Issue: Cannot connect to database
**Solution**: 
- Verify SQL Server is running
- Check credentials in `application.properties`
- Ensure TESTDB database exists
- Check firewall settings

### Issue: Port 8080 already in use
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Issue: Tables not created
**Solution**: 
- Check database connection
- Verify `spring.jpa.hibernate.ddl-auto=update` in application.properties
- Check application logs for errors

### Issue: Maven build fails
**Solution**:
```bash
mvn clean install -U
```

## Requirements Checklist

✅ **Requirement #1**: Java Spring Boot Application  
✅ **Requirement #2**: Structured project architecture  
✅ **Requirement #3**: REST APIs with Postman collection  
✅ **Requirement #4**: Request/Response logging to files  
✅ **Requirement #5**: MSSQL database connection with @Transactional  
✅ **Requirement #6**: Pagination with 10 records per page  
✅ **Requirement #7**: External API integration (nested calls)  

## Key Endpoints Summary

| Category | Endpoint | Description |
|----------|----------|-------------|
| Health | `GET /api/health` | Check application status |
| Products | `GET /api/products/paginated` | **Pagination: 10 per page** ⭐ |
| Products | `POST /api/products` | Create product (@Transactional) |
| Orders | `GET /api/orders/paginated` | **Pagination: 10 per page** ⭐ |
| Orders | `POST /api/orders` | Create order (@Transactional) |
| Integration | `GET /api/integration/external-posts` | **Nested API call** ⭐ |
| Integration | `GET /api/integration/product-with-external/{id}` | **Multiple nested calls** ⭐ |

## Next Steps

1. Review the complete documentation in `API_DOCUMENTATION.md`
2. Test all endpoints using the Postman collection
3. Check the logs to see request/response logging in action
4. Review the code structure to understand the 3-tier architecture

## Support

If you encounter any issues:
1. Check the application logs in `logs/application.log`
2. Review the error messages in the console
3. Refer to `API_DOCUMENTATION.md` for detailed information

---

**Happy Testing!** 🚀
