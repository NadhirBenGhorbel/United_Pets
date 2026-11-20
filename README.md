🐾 UnitedPets – Pet Adoption Platform

UnitedPets is a full-stack application designed to support a pet adoption center.
The platform allows users to adopt animals, place pets for adoption, and manage animal information through a secure and structured system.

It also includes a mobile companion app connected to the backend, allowing users to view their pets in real time.

🎯 Project Goal

The goal of UnitedPets is to provide a centralized digital platform for managing:

Pet adoption requests

Animal profiles and information

User accounts with roles and permissions

Real-time access to personal pet data via mobile

The system focuses on security, usability, and maintainable backend architecture.

🧱 Architecture

UnitedPets follows a client–server architecture:

Backend

Framework: Symfony (PHP)

Authentication and role-based access control

REST API for data exchange

SQL database for persistent storage

Business logic handled server-side

Frontend (Web)

Built using Symfony templating (Twig)

HTML / CSS / JavaScript

Dynamic rendering of data from the backend

Mobile Application

Built with FlutterFlow

Connected to backend APIs

Allows users to view and track their own pets in real time

🔐 Key Features

✔ User authentication and role management
✔ Add pets for adoption
✔ Browse and adopt pets
✔ Manage pet information
✔ API communication between services
✔ Mobile access to personal pet data

🧪 Code Quality & Structure

The project was developed with attention to:

Clear MVC architecture (Symfony)

Separation between presentation and business logic

Structured backend routing and controllers

Secure user management

Maintainable and scalable project structure

🎥 Demo

A demonstration video of the application (web + mobile) is available upon request.

🚀 Technologies Used

Backend

Symfony (PHP)

SQL Database

Frontend

Twig (Symfony templating)

HTML / CSS / JavaScript

Mobile

FlutterFlow

👨‍💻 Author

Nadhir Ben Ghorbel
Computer Engineering Student – Backend & Full-Stack Development
Paris, France

## Java application

This repository also contains a JavaFX desktop application and a Spring Boot
REST API for animal records.

### Run the REST API

Install JDK 17 or newer, Maven, and MySQL. Create the `united_pets` database
and configure its `animal` table. The existing database connection expects
MySQL at `localhost:3306`, username `root`, and an empty password; update
`utils.MyBD` if your local settings differ.

From the project folder, start the API:

```powershell
mvn spring-boot:run
```

The API listens at `http://localhost:8080`:

| Method | Path | Action |
| --- | --- | --- |
| `GET` | `/api/animals` | List animals |
| `GET` | `/api/animals/{id}` | Get one animal |
| `POST` | `/api/animals` | Create an animal (`201 Created`) |
| `PUT` | `/api/animals/{id}` | Update an animal |
| `DELETE` | `/api/animals/{id}` | Delete an animal (`204 No Content`) |

Example create request:

```powershell
Invoke-RestMethod -Method Post -Uri http://localhost:8080/api/animals `
  -ContentType "application/json" `
  -Body '{"name":"Milo","breed":"Mixed","status":"Available","type":"Dog","age":2,"enrollmentDate":"2026-11-20","image":"","description":"Friendly"}'
```

The API returns JSON, uses HTTP status codes for invalid or missing records,
and returns `503 Service Unavailable` when MySQL is not connected.

### Run the JavaFX app

With Maven installed, run:

```powershell
mvn javafx:run
```
