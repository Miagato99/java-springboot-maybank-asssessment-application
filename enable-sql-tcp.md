# Enable TCP/IP for SQL Server Express - Step by Step Guide

## Current Issue
**Error:** `The server SQLEXPRESS is not configured to listen with TCP/IP.`

**Root Cause:** SQL Server Express has TCP/IP protocol disabled by default.

---

## ✅ Solution: Enable TCP/IP Protocol

### **Step 1: Open SQL Server Configuration Manager**

**Method A:**
1. Press `Win + R`
2. Type one of these (try in order):
   - `SQLServerManager16.msc` (SQL Server 2022)
   - `SQLServerManager15.msc` (SQL Server 2019)
   - `SQLServerManager14.msc` (SQL Server 2017)
   - `SQLServerManager13.msc` (SQL Server 2016)
3. Press Enter

**Method B:**
- Search for **"SQL Server Configuration Manager"** in the Start Menu

---

### **Step 2: Enable TCP/IP**

1. In **SQL Server Configuration Manager**, expand:
   ```
   SQL Server Network Configuration
   └── Protocols for SQLEXPRESS
   ```

2. You'll see a list of protocols:
   - Shared Memory (Enabled)
   - Named Pipes (Enabled/Disabled)
   - **TCP/IP (Disabled)** ← This is what we need to enable

3. **Right-click** on **TCP/IP** → Select **Enable**

4. A warning appears: *"Any changes made will be saved; however, they will not take effect until the service is stopped and restarted."*
   - Click **OK**

---

### **Step 3: Configure TCP/IP Port (Recommended)**

1. **Right-click** on **TCP/IP** → Select **Properties**

2. Go to the **IP Addresses** tab

3. Scroll down to the **IPALL** section (at the bottom)

4. Configure:
   - **TCP Dynamic Ports**: *(leave empty or clear it)*
   - **TCP Port**: `1433`

5. Click **Apply** → Click **OK**

---

### **Step 4: Restart SQL Server Express (CRITICAL!)**

**The changes won't take effect until you restart the service!**

**Method A: Using Services (GUI)**
1. Press `Win + R`
2. Type `services.msc` and press Enter
3. Find **SQL Server (SQLEXPRESS)**
4. Right-click → **Restart**

**Method B: Command Prompt (Run as Administrator)**
1. Right-click **Command Prompt** → **Run as Administrator**
2. Run:
```cmd
net stop "MSSQL$SQLEXPRESS"
net start "MSSQL$SQLEXPRESS"
```

---

### **Step 5: Verify TCP/IP is Working**

After restarting, verify SQL Server is listening on port 1433:

```cmd
netstat -ano | findstr :1433
```

**Expected output:**
```
TCP    0.0.0.0:1433          0.0.0.0:0              LISTENING       [PID]
TCP    [::]:1433             [::]:0                 LISTENING       [PID]
```

---

### **Step 6: Update Application Configuration (Already Done)**

Your `application.properties` is already configured correctly:

```properties
spring.datasource.url=jdbc:sqlserver://localhost;instanceName=SQLEXPRESS;databaseName=TESTDB;encrypt=true;trustServerCertificate=true;integratedSecurity=true
```

---

### **Step 7: Run the Application**

After completing all steps above, run:

```bash
mvn spring-boot:run
```

---

## 🎯 Expected Successful Output

When the application starts successfully with MSSQL, you should see:

```
2025-11-24 XX:XX:XX [restartedMain] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Starting...
2025-11-24 XX:XX:XX [restartedMain] INFO  com.zaxxer.hikari.pool.HikariPool - HikariPool-1 - Added connection com.microsoft.sqlserver.jdbc.SQLServerConnection@XXXXXXXX
2025-11-24 XX:XX:XX [restartedMain] INFO  com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
```

Followed by Hibernate creating tables:

```
Hibernate: create table orders (id bigint not null, created_date datetime2(6), product_id bigint, quantity integer not null, status varchar(255), total_price float(53) not null, primary key (id))
Hibernate: create table products (id bigint not null, active bit not null, category varchar(255), created_date datetime2(6), description varchar(1000), name varchar(255), price float(53) not null, stock_quantity integer not null, updated_date datetime2(6), primary key (id))
```

And finally:

```
Started AssessmentApplication in X.XXX seconds (process running for X.XXX)
```

---

## 🧪 Test the Application

Once running, test these endpoints:

### **1. Health Check**
```bash
curl http://localhost:8080/api/health
```

**Expected Response:**
```json
{
  "status": "UP",
  "timestamp": "2025-11-24T10:30:00",
  "application": "Maybank Assessment Application"
}
```

### **2. Create a Product**
```bash
curl -X POST http://localhost:8080/api/products ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Laptop\",\"description\":\"High-performance laptop\",\"price\":5499.99,\"stockQuantity\":50,\"category\":\"Electronics\",\"active\":true}"
```

### **3. Get All Products (Paginated)**
```bash
curl http://localhost:8080/api/products/paginated?page=0&size=10
```

### **4. Test External API Integration**
```bash
curl http://localhost:8080/api/integration/external-posts
```

---

## 🚨 Troubleshooting

### **Issue: "Access is denied" when restarting service**
**Solution:** You must run Command Prompt as Administrator (right-click → Run as administrator)

### **Issue: Still getting connection errors after restart**
**Solution:** 
1. Check Windows Firewall isn't blocking port 1433
2. Verify SQL Server Browser is running: `sc query SQLBrowser`
3. Check TCP/IP is enabled: Open SQL Server Configuration Manager again

### **Issue: "Cannot find SQL Server Configuration Manager"**
**Solution:** 
- SQL Server Express might be a very old or new version
- Try searching in Start Menu or use SQL Server Management Studio to configure instead

---

## 📝 Summary Checklist

- [ ] Open SQL Server Configuration Manager
- [ ] Enable TCP/IP protocol for SQLEXPRESS
- [ ] Set TCP Port to 1433 in IPALL section
- [ ] Restart SQL Server (SQLEXPRESS) service
- [ ] Verify port 1433 is listening (`netstat -ano | findstr :1433`)
- [ ] Run `mvn spring-boot:run`
- [ ] Test health endpoint at `http://localhost:8080/api/health`

---

**After completing these steps, your application will connect to MSSQL Server Express successfully!** 🚀
