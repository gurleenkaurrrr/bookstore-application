# 📚 Bookstore Management System

A Java desktop application for managing a bookstore — built with clean OOP principles, layered MVC architecture, and a loyalty rewards system powered by the State Pattern.

## Overview
This application simulates a real-world bookstore where customers can browse books, earn loyalty points, and redeem discounts — while owners manage inventory and customer accounts. Built as part of an object-oriented software design course at Toronto Metropolitan University.

## Features

**👤 Authentication**
- Separate login flows for Owners and Customers
- Secure username/password authentication

**🛍️ Customer**
- Browse available books and their prices
- Earn loyalty points on purchases
- Redeem points for discounts at checkout
- Automatic tier upgrades between Silver and Gold status

**🔑 Owner**
- Add and remove books from inventory
- View and manage all registered customers
- Manage the full bookstore from a dedicated dashboard

**💾 Persistence**
- Save and load bookstore data from files between sessions

## Architecture
The project follows a clean 3-layer MVC structure:

├── Model Layer
│ ├── BookStore.java — Singleton; manages books and customers
│ ├── Book.java — Book entity (name, price)
│ ├── User.java — Abstract base for all users
│ ├── Customer.java — Extends User; holds points and status
│ └── Owner.java — Singleton; extends User
├── State Layer
│ ├── CustomerStatus.java — Abstract state interface
│ ├── SilverStatus.java — Concrete state: Silver tier
│ └── GoldStatus.java — Concrete state: Gold tier
└── GUI Layer
├── BookStoreApp.java — Entry point
├── LoginPanel.java — Login screen
├── OwnerStartPanel.java — Owner dashboard
├── OwnerCustomersPanel.java — Customer management view
├── CustomerStartPanel.java — Customer dashboard
└── CustomerCostPanel.java — Checkout and points redemption


## Design Patterns

| Pattern | Where Used | Purpose |
|---|---|---|
| Singleton | BookStore, Owner | Ensures a single shared instance |
| State Pattern | CustomerStatus, SilverStatus, GoldStatus | Dynamic loyalty tier behaviour |
| Inheritance | User → Customer, User → Owner | Shared authentication logic |

## Technologies
- **Language:** Java
- **GUI:** Java Swing
- **Paradigm:** Object-Oriented Design (OOP)

## Getting Started

**Prerequisites:** Java JDK 8 or higher

```bash
# Clone the repository
git clone https://github.com/gurleenkaurrrr/bookstore-application.git

# Navigate into the project
cd BookstoreApp

# Compile
javac src/**/*.java

# Run
java -cp src bookstoreapp.BookStoreApp
```

## Course Info
Built for COE528 — Object-Oriented Engineering at Toronto Metropolitan University, demonstrating practical use of design patterns, layered architecture, and UML modelling.

---
*Academic project — Toronto Metropolitan University*
