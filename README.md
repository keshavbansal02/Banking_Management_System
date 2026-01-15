#  Banking Management System (Java-JDBC)

A robust console-based Banking Application built using **Java** and **MySQL**. This project demonstrates the implementation of core **OOPs concepts**, **JDBC** for database connectivity, and **Transaction Management** to ensure data integrity.

##  Features

### User Management
* **Registration & Login:** Secure User Registration and Login system.
* **Validation:** Verification of existing users to prevent duplicate registration.

### Account Management
* **Account Opening:** Open a new bank account with uniquely generated account numbers.
* **Email Linking:** Check for existing bank accounts linked to a user's email.

### Banking Operations
* **Debit:** Withdraw money with real-time balance and PIN validation.
* **Credit:** Deposit money securely into the account.
* **Transfer:** Securely transfer funds between two accounts using transaction rollback logic.
* **Balance Inquiry:** Instant check of current balance using security PIN.

### Transaction Integrity
* **ACID Properties:** Full implementation of ACID properties using `setAutoCommit(false)`, `commit()`, and `rollback()`.

---

## 🛠️ Prerequisites

* **Java Development Kit (JDK):** Version 17 or higher.
* **Database:** MySQL Server (8.0 or higher).
* **Driver:** MySQL Connector/J (JDBC Driver).

---

Developed By
Keshav Bansal
