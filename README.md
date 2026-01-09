# Full-Stack Project Task Manager

A full-stack web application for managing projects and tasks, built with **Spring Boot** (backend) and **React** (frontend).
The application focuses on **security**, **clean architecture**, and **clear business logic**, while remaining simple and intuitive to use.

---

## Features Overview

* Secure authentication with **JWT**
* User-scoped projects
* Task management with due dates
* Automatic project progress calculation
* Overdue task highlighting
* Clean UI with Tailwind CSS
* Persistent database using Docker

---

## Functional Requirements

### 1. Authentication

* Login using **email + password**
* Authentication handled with **Spring Security**
* JWT token issued on login
* All API routes are protected except `/login`

### 2. Projects Management

Users can:

* Create a project (title + optional description)
* List their own projects
* View project details

### 3. Tasks Management

Inside a project, users can:

* Create a task with:

  * title
  * description
  * due date
* Mark a task as completed
* Delete a task
* List all tasks for a project
* See overdue tasks visually highlighted
* Prevent selecting a due date in the past

### 4. Project Progress

For each project, the backend returns:

* Total number of tasks
* Number of completed tasks
* Progress percentage (displayed as a progress bar)

---

## Technical Stack

### Backend

* **Java 17**
* **Spring Boot**
* **Spring Security + JWT**
* **Spring Data JPA**
* **MySQL**
* **Docker & Docker Compose**

### Frontend

* **React**
* **Tailwind CSS**
* **Axios**

---

## Architecture & Technical Decisions

### Security

* Implemented **Spring Security** with:

  * `UserDetails` & `UserDetailsService`
  * Password hashing (BCrypt)
  * JWT authentication filter
* Stateless authentication using JWT
* All protected routes require a valid token

### Clean Architecture

* Clear separation of concerns:

  * Controllers
  * Services
  * Repositories
  * DTOs
* DTOs used to:

  * Avoid exposing entities
  * Validate inputs
  * Control API responses
* Centralized exception handling using a global exception handler

### Data Persistence

* MySQL database
* Persistent storage via Docker volumes
* A **user seeder** initializes users in the database on application startup

---

## Installation & Setup

### Prerequisites

* Docker & Docker Compose
* Node.js (v18+ recommended)
* npm

---

## Running the Backend & Database

From the project root directory:

```bash
docker-compose down -v
docker-compose up --build
```

**Backend runs on:**
[http://localhost:8080](http://localhost:8080)

**Database:**
Runs inside Docker with persistent storage.

---

## Running the Frontend

Navigate to the frontend directory:

```bash
npm install
npm start
```

**Frontend runs on:**
[http://localhost:3000](http://localhost:3000)

---

## Demo Video

A short demo video (1–2 minutes) is included, showing:

* Application running
* Authentication flow
* Project & task management
* Progress calculation
* Key technical decisions

**Demo video link:**
👉 https://drive.google.com/file/d/1UKNWBJjfv_hvsW4tyjW5vbiQdzZOBDGz/view?usp=drive_link

---

## Demo Flow (What the Video Shows)

1. Login using email & password
2. Create a new project
3. Add tasks with due dates
4. Mark tasks as completed and observe progress updates
5. Delete a task and confirm consistent state
6. Highlight overdue tasks and validation behavior

---

## Bonus Features Implemented

* Docker Compose setup (backend + database)
* Pagination on task listing
* Input validation
* Global error handling
* Overdue task highlighting
* Clean folder structure

---

## Future Improvements

* Role-based access control
* Refresh tokens
* Unit and integration tests
* Search & filtering for tasks
* Frontend Dockerization

