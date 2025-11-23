# ⚠️ IMPORTANT: Lombok Configuration Required

## Issue

The project uses **Lombok** annotations (`@Data`, `@Getter`, `@Setter`, `@Slf4j`, etc.) to reduce boilerplate code. These annotations need to be processed by your IDE and Maven to generate the required getter/setter/logging methods.

## Solutions

### Option 1: Enable Lombok Plugin in Your IDE (Recommended)

#### For IntelliJ IDEA:
1. Go to `File` → `Settings` → `Plugins`
2. Search for "Lombok"
3. Install the **Lombok Plugin**
4. Restart IntelliJ IDEA
5. Go to `File` → `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors`
6. Check "Enable annotation processing"
7. Click `Apply` and `OK`
8. Run `mvn clean install`

#### For Eclipse:
1. Download `lombok.jar` from https://projectlombok.org/download
2. Run: `java -jar lombok.jar`
3. The installer will find your Eclipse installation
4. Click `Install/Update`
5. Restart Eclipse
6. Right-click project → `Maven` → `Update Project`

#### For VS Code:
1. Install the extension: **Language Support for Java by Red Hat**
2. Install the extension: **Lombok Annotations Support**
3. Reload VS Code
4. The extensions will handle Lombok automatically

### Option 2: Build Without Lombok (Quick Fix)

If you want to test immediately without setting up Lombok, I can refactor the code to not use Lombok annotations. However, this is not recommended as it will add a lot of boilerplate code.

### Option 3: Command Line Build (Works if IDE is the issue)

Sometimes Maven can build successfully even if the IDE shows errors:

```bash
# Clean and build
mvn clean package -DskipTests

# If that works, run the application
java -jar target/assessment-application-1.0.0.jar
```

## Verification

After enabling Lombok, verify it's working:

1. Open any entity file (e.g., `Product.java`)
2. You should be able to use auto-complete for `product.getName()` even though no `getName()` method is explicitly written
3. Build should complete without errors:
   ```bash
   mvn clean install
   ```

## What Lombok Does

Lombok annotations automatically generate:

- `@Data` → Generates `@Getter`, `@Setter`, `@ToString`, `@EqualsAndHashCode`, `@RequiredArgsConstructor`
- `@Slf4j` → Creates a `log` field for logging
- `@RequiredArgsConstructor` → Creates a constructor with required arguments (final fields)
- `@AllArgsConstructor` → Creates a constructor with all arguments
- `@NoArgsConstructor` → Creates a no-argument constructor

This significantly reduces code verbosity in our entities, DTOs, and services.

## Files Using Lombok

All files in these packages use Lombok:
- `com.maybank.assessment.entity.*` (Product, Order)
- `com.maybank.assessment.dto.*` (All DTOs)
- `com.maybank.assessment.service.*` (All services)
- `com.maybank.assessment.controller.*` (All controllers)
- `com.maybank.assessment.interceptor.*` (LoggingInterceptor)
- `com.maybank.assessment.config.*` (WebConfig)
- `com.maybank.assessment.exception.*` (ErrorResponse)

## Next Steps

1. **Enable Lombok in your IDE** using Option 1 above
2. **Rebuild the project**: `mvn clean install`
3. **Update database credentials** in `application.properties`
4. **Run the application**: `mvn spring-boot:run`
5. **Test with Postman** using the provided collection

## Need Help?

If you continue to face issues:
1. Check that annotation processing is enabled in your IDE
2. Verify Lombok plugin is installed and active
3. Try rebuilding from command line: `mvn clean install -U`
4. Check Maven console output for specific Lombok-related errors

---

**The code is complete and correct - it just needs Lombok to be properly configured!** ✅
