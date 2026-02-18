# Blog Management REST API

A comprehensive RESTful API for managing blog posts, categories, and comments built with **Spring Boot 3.x**, **Spring Data JPA**, and **Hibernate**.

---

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing](#testing)
- [Configuration](#configuration)
- [Logging](#logging)
- [Error Handling](#error-handling)

---

## ✨ Features

✅ **RESTful API Endpoints** for blog posts, categories, and comments
✅ **JPA Entities** with proper relationships and validations
✅ **Request Validation** using Jakarta validation annotations
✅ **Pagination & Sorting** for list endpoints
✅ **Global Exception Handling** with custom error responses
✅ **H2 In-Memory Database** for development
✅ **PostgreSQL** configuration for production
✅ **OpenAPI/Swagger** documentation with interactive UI
✅ **Comprehensive Logging** with SLF4J and Logback
✅ **Unit Tests** with JUnit 5 and Mockito
✅ **Spring Security Ready** architecture
✅ **CORS Support** for cross-origin requests

---

## 🛠 Technology Stack

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Programming Language |
| **Spring Boot** | 3.2.1 | Framework |
| **Spring Data JPA** | 3.2.1 | ORM |
| **Hibernate** | 6.4.0 | Database Mapping |
| **H2 Database** | Latest | Development DB |
| **PostgreSQL** | 42.7.1 | Production DB |
| **Maven** | 3.8+ | Build Tool |
| **JUnit 5** | Latest | Unit Testing |
| **Mockito** | Latest | Mocking Framework |
| **Swagger/OpenAPI** | 2.0.2 | API Documentation |
| **SLF4J & Logback** | Latest | Logging |

---

## 📂 Project Structure

```
week6-spring-blog-api/
│
├── src/main/java/com/blogapi/
│   ├── BlogApiApplication.java          # Main Spring Boot Application
│   ├── controller/
│   │   ├── PostController.java          # REST endpoints for posts
│   │   ├── CategoryController.java      # REST endpoints for categories
│   │   └── CommentController.java       # REST endpoints for comments
│   ├── service/
│   │   ├── PostService.java             # Business logic for posts
│   │   ├── CategoryService.java         # Business logic for categories
│   │   └── CommentService.java          # Business logic for comments
│   ├── repository/
│   │   ├── PostRepository.java          # JPA repository for posts
│   │   ├── CategoryRepository.java      # JPA repository for categories
│   │   └── CommentRepository.java       # JPA repository for comments
│   ├── model/
│   │   ├── entity/
│   │   │   ├── Post.java                # Post entity
│   │   │   ├── Category.java            # Category entity
│   │   │   └── Comment.java             # Comment entity
│   │   └── dto/
│   │       ├── PostRequest.java         # Post request DTO
│   │       ├── PostResponse.java        # Post response DTO
│   │       ├── CategoryRequest.java     # Category request DTO
│   │       ├── CategoryResponse.java    # Category response DTO
│   │       ├── CommentRequest.java      # Comment request DTO
│   │       ├── CommentResponse.java     # Comment response DTO
│   │       └── ApiResponse.java         # Generic API response wrapper
│   ├── exception/
│   │   ├── ResourceNotFoundException.java # Custom exception
│   │   └── GlobalExceptionHandler.java    # Global error handler
│   └── config/
│       └── SwaggerConfig.java           # Swagger/OpenAPI configuration
│
├── src/main/resources/
│   ├── application.properties           # Default configuration
│   ├── application-dev.properties       # Development profile
│   ├── application-prod.properties      # Production profile
│   └── logback-spring.xml              # Logging configuration
│
├── src/test/java/com/blogapi/
│   ├── service/
│   │   ├── PostServiceTest.java         # Tests for PostService
│   │   └── CategoryServiceTest.java     # Tests for CategoryService
│   └── ...
│
├── pom.xml                              # Maven configuration
├── README.md                            # Project documentation
└── .gitignore                          # Git ignore file
```

---

## 📦 Prerequisites

Before running the project, ensure you have:

- **Java Development Kit (JDK)** 17 or higher
- **Maven** 3.8 or higher
- **Git** for version control
- Optional: **PostgreSQL** 12+ for production environment

### Installation Steps

1. **Install Java:**
   ```bash
   # Check Java version
   java -version
   ```

2. **Install Maven:**
   ```bash
   # Check Maven version
   mvn -version
   ```

3. **Install PostgreSQL (Optional for Production):**
   Download from: https://www.postgresql.org/download/

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
cd C:\Users\irsha\visualCode\week6-spring-blog-api
```

### Step 2: Build the Project

```bash
# Navigate to project directory
cd week6-spring-blog-api

# Clean and build using Maven
mvn clean install
```

### Step 3: Verify Build

```bash
# Check if JAR file is created
ls target/blog-api-0.0.1-SNAPSHOT.jar
```

---

## ▶️ Running the Application

### Option 1: Using Maven

```bash
# Run with development profile
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"

# Or simply (dev is default)
mvn spring-boot:run
```

### Option 2: Using JAR File

```bash
# Build the jar
mvn clean package

# Run the jar
java -jar target/blog-api-0.0.1-SNAPSHOT.jar

# Run with specific profile
java -jar target/blog-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Application Startup Output

```
==============================================
🎉 APPLICATION STARTED SUCCESSFULLY 🎉
==============================================

Spring Boot Version: 3.2.1
Java Version: 17
Active Profile: dev
Server Port: 8080
Database: H2 (In-Memory)

📊 Available Resources:
- H2 Console: http://localhost:8080/h2-console
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs

---

## 📘 Documentation & Development URLs

The application provides three helpful web interfaces to aid development and testing:

1. **Swagger UI (`/swagger-ui.html`)**
   - Interactive HTML page generated by Springdoc OpenAPI.
   - Browse all REST endpoints, view request/response models, and send sample requests directly
     from the browser. Useful for manual exploratory testing.
   - After the server starts, open in your browser and click any operation to expand it. Use the
     **"Try it out"** button, fill in parameters/body, and execute to see real responses.
   - Example output when invoking **GET `/api/posts`** via Swagger:
     ```json
     {
       "status": 200,
       "message": "Posts retrieved successfully",
       "data": { ... }
     }
     ```

2. **Raw OpenAPI JSON (`/api-docs`)**
   - Exposes the full OpenAPI 3.0 specification as JSON.
   - Useful when integrating with API clients, generating SDKs, or verifying schema.
   - You can curl this endpoint:
     ```bash
     curl http://localhost:8080/api-docs | jq .
     ```
   - The returned JSON starts like:
     ```json
     {
       "openapi": "3.0.1",
       "info": {
         "title": "Blog Management REST API",
         "description": "Comprehensive RESTful API for posts, categories, and comments",
         ...
       },
       ...
     }
     ```

3. **H2 Database Console (`/h2-console`)**
   - Web UI for the in‑memory H2 database used in **dev** profile.
   - Before using, make sure the application is running and profile is `dev`.
   - Access it at the URL above and use the following credentials (these match
     `application-dev.properties`):
     - **JDBC URL:** `jdbc:h2:mem:testdb`
     - **User:** `sa`
     - **Password:** *(leave blank)*
   - Once connected, run SQL queries against `POSTS`, `CATEGORIES`, or `COMMENTS` tables.
     For example:
     ```sql
     SELECT * FROM POSTS;
     ```
   - The console is great for verifying that sample data was inserted, inspecting schema, and
     manually mutating records during development.

Each of the three resources is available as soon as the Spring Boot application starts.  They
provide different views of the API: interactive, specification, and underlying data store.

---

==============================================
```

---

## 🔌 API Endpoints

### Base URL
```
http://localhost:8080/api
```

### Posts API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/posts` | Get all posts with pagination |
| GET | `/posts/{id}` | Get post by ID |
| POST | `/posts` | Create new post |
| PUT | `/posts/{id}` | Update post |
| DELETE | `/posts/{id}` | Delete post |
| GET | `/posts/category/{categoryId}` | Get posts by category |
| GET | `/posts/search?searchTerm=...` | Search posts |

### Categories API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/categories` | Get all categories |
| GET | `/categories/{id}` | Get category by ID |
| POST | `/categories` | Create new category |
| PUT | `/categories/{id}` | Update category |
| DELETE | `/categories/{id}` | Delete category |
| GET | `/categories/search?searchTerm=...` | Search categories |

### Comments API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/posts/{postId}/comments` | Get comments for post |
| GET | `/posts/{postId}/comments/approved` | Get approved comments |
| POST | `/posts/{postId}/comments` | Add comment to post |
| PUT | `/posts/{postId}/comments/{commentId}` | Update comment |
| DELETE | `/posts/{postId}/comments/{commentId}` | Delete comment |
| PUT | `/posts/{postId}/comments/{commentId}/approve` | Approve comment |
| PUT | `/posts/{postId}/comments/{commentId}/reject` | Reject comment |

---

## 📝 Sample API Requests

### 1. Create Category

```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Technology",
    "description": "Latest tech news and tutorials"
  }'
```

**Response:**
```json
{
  "status": 201,
  "message": "Category created successfully",
  "data": {
    "id": 1,
    "name": "Technology",
    "description": "Latest tech news and tutorials",
    "createdAt": "2024-01-25T10:30:00",
    "updatedAt": "2024-01-25T10:30:00"
  },
  "timestamp": "2024-01-25T10:30:00"
}
```

### 2. Create Post

```bash
curl -X POST http://localhost:8080/api/posts \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Getting Started with Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone, production-grade Spring based applications...",
    "author": "John Doe",
    "categoryId": 1
  }'
```

**Response:**
```json
{
  "status": 201,
  "message": "Post created successfully",
  "data": {
    "id": 1,
    "title": "Getting Started with Spring Boot",
    "content": "Spring Boot makes it easy to create stand-alone...",
    "author": "John Doe",
    "categoryId": 1,
    "categoryName": "Technology",
    "createdAt": "2024-01-25T10:30:00",
    "updatedAt": "2024-01-25T10:30:00"
  },
  "timestamp": "2024-01-25T10:30:00"
}
```

### 3. Get All Posts with Pagination

```bash
curl "http://localhost:8080/api/posts?page=0&size=5&sort=createdAt,desc"
```

### 4. Add Comment to Post

```bash
curl -X POST http://localhost:8080/api/posts/1/comments \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Great tutorial! Very helpful for beginners.",
    "author": "Jane Smith"
  }'
```

### 5. Search Posts

```bash
curl "http://localhost:8080/api/posts/search?searchTerm=spring&page=0&size=10"
```

### 6. Get Posts by Category

```bash
curl http://localhost:8080/api/posts/category/1
```

### 7. Update Post

```bash
curl -X PUT http://localhost:8080/api/posts/1 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Advanced Spring Boot Techniques",
    "content": "Learn advanced techniques in Spring Boot...",
    "author": "John Doe",
    "categoryId": 1
  }'
```

### 8. Approve Comment

```bash
curl -X PUT http://localhost:8080/api/posts/1/comments/1/approve
```

### 9. Delete Post

```bash
curl -X DELETE http://localhost:8080/api/posts/1
```

---

## 🧪 Testing

### Running Unit Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PostServiceTest

# Run with coverage
mvn test jacoco:report
```

### Running Integration Tests

```bash
# Run all tests including integration tests
mvn verify
```

### Test Coverage

The project includes unit tests for:
- **PostService** - Post CRUD operations and searches
- **CategoryService** - Category management
- **CommentService** - Comment operations

---

## ⚙️ Configuration

### Application Properties

#### Development Profile (`application-dev.properties`)
- **Database**: H2 In-Memory
- **URL**: `http://localhost:8080`
- **H2 Console**: `http://localhost:8080/h2-console`
- **Logging Level**: DEBUG
- **SQL Display**: Enabled

#### Production Profile (`application-prod.properties`)
- **Database**: PostgreSQL
- **Connection Pool**: 20 max connections
- **Logging Level**: WARN
- **Error Messages**: Minimal details
- **Response Compression**: Enabled

### Switching Profiles

```bash
# Development (default)
mvn spring-boot:run

# Production
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"

# Using JAR
java -jar target/blog-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

---

## 📊 Logging

### Log Files Location

**Development:**
```
logs/app-dev.log
```

**Production:**
```
/var/log/app/blog-api.log
```

### Log Levels

| Logger | Level |
|--------|-------|
| `com.blogapi` | DEBUG (dev), WARN (prod) |
| Spring Web | DEBUG (dev), WARN (prod) |
| Hibernate SQL | DEBUG (dev), WARN (prod) |

### Log Output Format

```
2024-01-25 10:30:00 [main] DEBUG com.blogapi.service.PostService - Creating new post with title: Spring Boot Guide
```

---

## ⚠️ Error Handling

### Error Response Format

```json
{
  "status": 404,
  "message": "Resource Not Found",
  "error": "Post not found with id: 999",
  "timestamp": "2024-01-25T10:30:00"
}
```

### Validation Error Response

```json
{
  "status": 400,
  "message": "Validation Failed",
  "data": {
    "title": "Title is required",
    "content": "Content is required",
    "author": "Author is required"
  },
  "timestamp": "2024-01-25T10:30:00"
}
```

### Error Types Handled

| Status | Error Type | Description |
|--------|-----------|-------------|
| 400 | Bad Request | Invalid request parameters or validation failure |
| 404 | Not Found | Resource not found |
| 500 | Internal Server Error | Unexpected server error |

---

## 📚 Documentation

### Swagger/OpenAPI Documentation

Access the interactive API documentation:
```
http://localhost:8080/swagger-ui.html
```

### API Documentation in JSON

```
http://localhost:8080/api-docs
```

---

## 🗄️ Database

### Entity Relationships

```
Category (1) ----> (Many) Post (1) ----> (Many) Comment
   ↓
  Posts
   └─ Comments
```

### Database Initialization

The application automatically creates tables on startup in development mode:
- `categories` table
- `posts` table
- `comments` table

### H2 Console Access

**URL**: `http://localhost:8080/h2-console`

**Credentials (Development):**
- Driver: `org.h2.Driver`
- URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (leave empty)

---

## 📋 Quality Checklist

✅ Clear project description and objectives
✅ Step-by-step setup instructions
✅ Well-organized code structure
✅ Complete API endpoint documentation
✅ Sample API requests with responses
✅ Comprehensive error handling
✅ Unit tests with examples
✅ Logging configuration
✅ Multiple environment profiles
✅ OpenAPI/Swagger documentation
✅ Input validation
✅ Proper HTTP status codes

---

## 🔗 Useful Resources

- [Spring Boot Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Documentation](https://spring.io/projects/spring-data-jpa)
- [Swagger/OpenAPI Documentation](https://swagger.io/)
- [RESTful API Design Guide](https://restfulapi.net/)

---

## 📞 Support

For issues or questions:
1. Check the logs in `logs/` directory
2. Access H2 console to inspect database
3. Use Swagger UI for API testing
4. Review error messages in API responses

---

## 📄 License

This project is provided as-is for educational purposes.

---

**Created:** February 2026
**Version:** 1.0.0
**Status:** Production Ready
