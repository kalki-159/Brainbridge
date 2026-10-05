# 🌉 BrainBridge

### Connect. Learn. Collaborate.

BrainBridge is a peer-connection and collaboration platform that helps people discover and connect with others based on **complementary skills, learning goals, interests, and collaboration needs**.

> **Core idea:** What I know + what I want to learn + what I want to achieve → find people who can complement me.

For example, a person who knows **Java** but wants to learn **Spring Boot** can be matched with someone who knows **Spring Boot** but wants to improve **Java**.

---

## ✨ Features

- User registration and login
- Profile creation and editing
- Skills, learning goals, interests and collaboration preferences
- Rule-based compatibility matching
- Compatibility score and ranking
- Profile discovery
- Connection requests
- Pending / Accepted / Rejected connection status
- REST API backend
- MySQL database
- Responsive frontend
- BCrypt password hashing

---

## 🛠️ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- REST APIs

### Frontend
- HTML
- CSS
- JavaScript

---

## 🏗️ Architecture

```text
Browser
   ↓
HTML / CSS / JavaScript
   ↓
REST API
   ↓
Controller
   ↓
Service
   ↓
Repository
   ↓
JPA / Hibernate
   ↓
JDBC Driver
   ↓
MySQL
```

### Backend layers

- **Controller** → receives HTTP requests and returns responses
- **Service** → contains business logic
- **Repository** → handles database operations
- **Entity** → represents persistent database data
- **DTO** → carries request data between frontend and backend

---

## 📁 Project Structure

```text
BrainBridge/
├── pom.xml
├── README.md
├── .gitignore
├── database/
│   └── schema.sql
└── src/
    ├── main/
    │   ├── java/com/brainbridge/
    │   │   ├── BrainBridgeApplication.java
    │   │   ├── config/
    │   │   │   ├── CorsConfig.java
    │   │   │   └── PasswordConfig.java
    │   │   ├── controller/
    │   │   │   ├── ConnectionController.java
    │   │   │   ├── MatchController.java
    │   │   │   └── UserController.java
    │   │   ├── dto/
    │   │   │   ├── LoginRequest.java
    │   │   │   ├── ProfileUpdateRequest.java
    │   │   │   └── RegisterRequest.java
    │   │   ├── entity/
    │   │   │   ├── ConnectionRequest.java
    │   │   │   └── User.java
    │   │   ├── repository/
    │   │   │   ├── ConnectionRequestRepository.java
    │   │   │   └── UserRepository.java
    │   │   └── service/
    │   │       ├── ConnectionService.java
    │   │       ├── MatchingService.java
    │   │       └── UserService.java
    │   └── resources/
    │       ├── application.properties
    │       └── static/
    │           ├── index.html
    │           ├── style.css
    │           └── script.js
    └── test/
```

---

## ⚙️ Requirements

Install:

- JDK 17+
- Maven 3.9+
- MySQL 8+

---

## 🚀 Setup

### 1. Create the database

Open MySQL:

```sql
CREATE DATABASE brainbridge;
```

Or run:

```text
database/schema.sql
```

### 2. Configure MySQL

Open:

```text
src/main/resources/application.properties
```

Set your MySQL username and password.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/brainbridge
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 3. Run the application

From the project root:

```bash
mvn spring-boot:run
```

Backend:

```text
http://localhost:8080
```

Frontend:

```text
http://localhost:8080
```

The frontend is served directly by Spring Boot.

---

## 🔌 Main API Endpoints

### Authentication

```text
POST /api/users/register
POST /api/users/login
```

### Profile

```text
GET  /api/users/{id}
PUT  /api/users/{id}
GET  /api/users
```

### Matching

```text
GET /api/matches/{userId}
```

### Connections

```text
POST /api/connections
GET  /api/connections/{userId}
PUT  /api/connections/{id}/accept
PUT  /api/connections/{id}/reject
```

---

## 🧠 Matching Logic

BrainBridge does not simply find people with identical interests.

It looks for **complementary relationships**.

Example:

```text
User A:
Skills → Java, SQL
Wants → Spring Boot

User B:
Skills → Spring Boot
Wants → Java

        ↓

Complementary match
        ↓

Compatibility score
        ↓

Ranked recommendation
```

The current implementation calculates a rule-based compatibility score using:

- User A's learning goals vs User B's skills
- User B's learning goals vs User A's skills
- Shared interests
- Collaboration preferences

AI/ML-based recommendations can be added later.

---

## 🔐 Password Security

Passwords are not stored as plain text.

BrainBridge uses **BCrypt hashing** before storing passwords.

---

## 🧪 Testing

Useful scenarios:

- Successful registration
- Duplicate email registration
- Invalid login
- Successful login
- Profile update
- Match discovery
- Sending a connection request
- Duplicate connection request prevention
- Self-connection prevention
- Accepting a request
- Rejecting a request

Postman can be used to test the REST APIs independently of the frontend.

---

## 🔮 Future Enhancements

- Real-time chat using WebSockets
- Notifications
- Better personalized recommendation algorithm
- AI-based matching
- JWT authentication and authorization
- Database indexing and caching
- Cloud deployment
- Group collaboration and project rooms

---

## 💡 Interview Explanation

**BrainBridge is a peer-connection and collaboration platform designed to connect people based on complementary skills, shared goals, interests, and collaboration needs.**

The backend uses Java and Spring Boot, the database is MySQL, and the frontend uses HTML, CSS and JavaScript. The backend follows a Controller → Service → Repository architecture, with Spring Data JPA and Hibernate handling database interaction.

The matching system is rule-based: it compares users' skills and learning goals in both directions, calculates a compatibility score, and ranks potential connections.

---

## 📌 Important

This repository is a learning/project implementation. Do not commit real passwords, database credentials, API keys or other secrets.

For production use, credentials should be supplied through environment variables or secure configuration.
