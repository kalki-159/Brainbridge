# 🌉 BrainBridge

### Connect. Learn. Collaborate.

BrainBridge is a **peer connection and collaboration platform** designed to help people discover and connect with others based on their **complementary skills, learning goals, interests, and collaboration needs**.

The main idea is simple:

> **What I know + What I want to learn → Find someone who can complement me.**

For example, a person who knows **Java** but wants to learn **Spring Boot** can connect with someone who knows **Spring Boot** and wants to improve their **Java** skills.

---

## ✨ Features

- 👤 User Registration and Login
- 📝 User Profile Creation and Management
- 💡 Skills and Learning Goals
- 🎯 Interest and Collaboration Preferences
- 🤝 Complementary Skill Matching
- 📊 Compatibility Score
- 🔍 Potential Match Discovery
- 📩 Connection Requests
- ✅ Accept / Reject Requests
- 🔐 BCrypt Password Hashing
- 🌐 REST APIs
- 🗄️ MySQL Database
- 📱 Responsive Web Interface

---

## 🛠️ Technology Stack

### Backend
- **Java**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Hibernate**
- **MySQL**
- **Maven**
- **REST APIs**

### Frontend
- **HTML**
- **CSS**
- **JavaScript**

---

## 🏗️ System Architecture

```text
                    ┌─────────────────────┐
                    │       Browser       │
                    │ HTML/CSS/JavaScript │
                    └──────────┬──────────┘
                               │
                               │ HTTP / REST API
                               ▼
                    ┌─────────────────────┐
                    │   Spring Boot API   │
                    └──────────┬──────────┘
                               │
                 ┌─────────────┼─────────────┐
                 ▼             ▼             ▼
           Controller       Service      Repository
                               │             │
                               └──────┬──────┘
                                      ▼
                              JPA / Hibernate
                                      │
                                      ▼
                                   MySQL
