# 🍔 FoodKart - Online Food Ordering System

FoodKart is a backend microservices-based online food ordering system built using Spring Boot. It allows users to register, browse top-rated nearby restaurants and cuisines, add food items to their cart, place orders, and securely make payments — all from a single platform.

---

## 🚀 Features

- 🔐 User registration, login, and logout with secure JWT-based authentication
- 🏠 Dynamic homepage showcasing top-rated nearby restaurants and cuisines
- 🔎 Advanced search and filtering for restaurants, cuisines, and food types
- 🛒 Add food items to cart from selected restaurants
- 📦 Place orders and process payments securely
- 💳 Integrated payment gateway
- 📜 View past order history
- ⚙️ Built as a scalable microservices architecture
- 📡 Kafka-based asynchronous messaging
- 🧠 Caching support with Redis

---

## 🛠️ Tech Stack

| Technology         | Description                                  |
|--------------------|----------------------------------------------|
| **Java 17+**       | Primary programming language                 |
| **Spring Boot**    | Core framework for building microservices    |
| **Spring Security**| Secure endpoints and user authentication     |
| **JWT**            | Token-based authentication                   |
| **PostgreSQL**     | Relational database for data persistence     |
| **Spring Data JPA**| ORM for DB interactions                      |
| **Kafka**          | Messaging system for asynchronous processes  |
| **Redis**          | Caching for faster response times            |
| **Docker**         | Containerized deployment                     |
| **Git**            | Version control                              |

---

## 🧩 Microservices Overview

The project is composed of the following services:

- **User Service** – Manages registration, authentication (login/logout), and user profile
- **Restaurant Service** – Manages restaurants, cuisines, and food items
- **Order Service** – Handles cart operations, order placement, and order history
- **Payment Service** – Simulates integration with a payment gateway
- **Search Service** – Provides filtered search results for restaurants/food
- **Notification Service** *(Optional)* – Uses Kafka for async order confirmation or status updates

---

## 📐 Project Structure
foodkart/
│
├── user-service/
├── restaurant-service/
├── order-service/
├── payment-service/
├── search-service/
├── common-utils/ # Shared models and DTOs
├── api-gateway/ # (Optional) For routing and authentication


---

## 🧪 How to Run

### 1. Prerequisites

- Java 17+
- Docker & Docker Compose
- PostgreSQL (if not using Docker)
- Kafka & Redis (locally or via Docker)

### 2. Clone the repository

```bash
git clone https://github.com/your-username/foodkart.git
cd foodkart


| Service            | URL                     |
| ------------------ | ----------------------- |
| User Service       |  http://localhost:9091  |
| Restaurant Service |  http://localhost:9092  |
| Order Service      |  http://localhost:9094  |
| Payment Service    |  http://localhost:9099  |
| Cart Service       |  http://localhost:9093  |
