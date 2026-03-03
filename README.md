Task Management Microservices API

A scalable Task Management backend built with Spring Boot microservices architecture.
This project is part of my portfolio and is designed with clean separation of concerns, security, and future extensibility in mind.

🧩 Architecture Overview

The system currently consists of:

TM_Auth_Service → Handles authentication & authorization

tm-common-entities → Shared domain models across services

More microservices → 🚧 Coming soon



The architecture follows microservices best practices to ensure:

✅ Loose coupling

✅ Independent deployment

✅ Scalability

✅ Maintainability



⚙️ Tech Stack

Java 17+

Spring Boot

Spring Security

JWT Authentication

Maven (multi-module)

PostgreSQL

REST APIs

📦 Modules
🔐 TM_Auth_Service

Authentication microservice responsible for:

User registration

Login

JWT token generation

Token validation

Security configuration

Future plans:

Refresh tokens

Role-based access

OAuth2 / Google login

API gateway integration

📚 tm-common-entities

Shared Maven module containing common domain models used across microservices.

Purpose:

Avoid duplication

Maintain consistency

Enable reuse across services



🗺️ Project Structure
task-management-project/
│
├── tm-common-entities/
├── TM_Auth_Service/
└── (more services coming soon)



🔑 Authentication Flow

User sends login request

Auth service validates credentials

JWT token is generated

Client uses token for protected APIs

Services validate token
