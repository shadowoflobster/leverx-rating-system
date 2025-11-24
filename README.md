# LeverX Rating System

A web application built with **Spring Framework 6**, **Hibernate 6**, **PostgreSQL**, and **Redis** for managing user ratings. This project demonstrates authentication, role-based authorization, email verification, and RESTful APIs.

- Java 17
- Maven 3.9+
- PostgreSQL database
- Redis server 

### Project Structure
```bash
src
 ├─ main
 │   ├─ java
 │   │    ├─ adapters
 │   │    │    ├─ inbound
 │   │    │    │    ├─ dto
 │   │    │    │    ├─ rest
 │   │    │    │    └─ security
 │   │    │    └─ outbound
 │   │    │         ├─ entities
 │   │    │         ├─ jpaAdapters
 │   │    │         ├─ mappers
 │   │    │         └─ jpaRepositories
 │   │    ├─ application
 │   │    │    ├─ ports
 │   │    │    └─ services
 │   │    ├─ config
 │   │    └─ domain
 │   │         ├─ config
 │   │         └─ enums
 │   └─ resources
 └─ test
```

## Auth Endpoints
### Base path: /auth

| Method | Endpoint                            | Description                  | Auth   |
| ------ | ----------------------------------- | ---------------------------- | ------ |
| POST   | `/register`                         | Register a new user          | Public |
| POST   | `/login`                            | Login and receive JWT token  | Public |
| POST   | `/verify?email={email}&code={code}` | Verify user email with code  | Public |
| POST   | `/reset-password/{email}`           | Request password reset email | Public |
| POST   | `/reset-password`                   | Reset password with code     | Public |

## Comment Endpoints

### Base path: /users

| Method | Endpoint                                                   | Description                         | Auth   |
| ------ | ---------------------------------------------------------- | ----------------------------------- | ------ |
| POST   | `/{id}/comments`                                           | Add comment for target ID           | User   |
| GET    | `/comments/{id}`                                           | Get comment by comment ID           | Public |
| GET    | `/{id}/comments`                                           | Get all comments for target ID      | Public |
| GET    | `/{userId}/comments/{commentId}`                           | Get comment by user ID & comment ID | Public |
| GET    | `/comments/filter?authorId={authorId}&targetId={targetId}` | Get comments by author and target   | Public |
| PUT    | `/comments/{id}`                                           | Update comment by ID                | User   |
| DELETE | `/{id}`                                                    | Delete comment by ID                | User   |

## GameObject Endpoints

### Base path: /objects

| Method | Endpoint | Description                                                                                 | Auth                     |
| ------ | -------- | ------------------------------------------------------------------------------------------- | ------------------------ |
| POST   | `/`      | Add a new game object. If the referenced game does not exist, it will also create the game. | User (must be logged in) |

## Seller Endpoints

### Base path: /seller

| Method | Endpoint               | Description                                    | Auth |
| ------ | ---------------------- | ---------------------------------------------- | ---- |
| GET    | `/{id}/average-rating` | Get the average rating of a seller by ID.      | No   |
| GET    | `/top/{count}`         | Get the top `count` sellers by average rating. | No   |


## Admin Endpoints
### base path: /admin
| Method | Endpoint                       | Description                                       | Auth          |
| ------ | ------------------------------ | ------------------------------------------------- | ------------- |
| GET    | `/pending-users`               | Get all users pending approval                    | Administrator |
| GET    | `/pending-objects`             | Get all objects pending approval                  | Administrator |
| POST   | `/approve-object/{objectId}`   | Approve a pending object                          | Administrator |
| POST   | `/approve-user/{userId}`       | Approve a pending user (sends verification email) | Administrator |
| GET    | `/pending-comment`             | Get all comments pending approval                 | Administrator |
| POST   | `/approve-comment/{commentId}` | Approve a pending comment                         | Administrator |

