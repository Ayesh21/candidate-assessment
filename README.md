# teleport-candidate-assessment
## Project Overview: 
### Candidate Assessment Task Management System
This system is designed to manage users, projects, and tasks in a collaborative environment. It supports assigning tasks to users, organizing them by project, and providing role-based interaction with task statuses, priorities, and deadlines.

#### Core Features
User Management
* Create new users via API with validation.
* Store and retrieve user information (ID, username, email).

Project Management
* Create new projects with ownership tracking.
* Prevent concurrent project creation per user using distributed locking (Redisson RLock).
* Use Async behavior in methods.

Task Management
* Create, assign, and track tasks.
* Query overdue tasks, user-specific assignments.
* Pagination and sorting support using Spring's Pageable.                                                                   
                                                                                                                        
#### Architecture
* Backend: Java 17 with Spring Boot 3.x
* Persistence: JPA/Hibernate with likely use of a relational DB
* Concurrency Handling: Redisson for distributed locks to manage race conditions (e.g. concurrent project creation).
* DTO Pattern: Clean separation of API-facing DTOs and entity models using transformers.
* Logging: Uses Logger for debugging key actions like user/project/task creation.
* Validation: Bean validation via @Valid annotations on controller input.
* Testing:
  * Unit testing with JUnit 5 and Mockito.
  *Controller testing with Spring’s MockMvc

#### Technology Stack
Programming Language
* Java 17 – Core language for backend development.

Backend Framework
* Spring Boot 3.4.5 – Rapid application development framework.
* Spring Web – For building RESTful APIs.
* Spring Validation – For validating request bodies (@Valid).
* Spring Security

Concurrency & Async Processing
* Spring Async – For non-blocking background task execution.
* Redisson – Redis-based distributed lock manager.
* RLock – Used to prevent race conditions in concurrent operations.

Data Layer
* MySQL – Primary relational database for persistent data.
* Redis – In-memory key-value store used for locking and possibly caching.

Testing
* JUnit 5 – For unit and integration testing.
* Mockito – Mocking framework for isolating unit tests.
* Spring MockMvc – For simulating HTTP requests in controller tests.
* Jacoco – Code coverage reports.

DevOps & CI/CD
* GitHub Actions – CI/CD pipelines for testing and deployment automation.
* Docker – Containerization of the application.

Documentation
* Swagger / Springdoc OpenAPI – Auto-generated API documentation from controller annotations.

Security
* Spring Security.

Build & Dependency Management
* Maven Build tool and dependency management.










