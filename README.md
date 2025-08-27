# Spring Security Demo

A Spring Boot application demonstrating Spring Security integration with form-based authentication and role-based authorization.

## Features

- Spring Boot 3.2.0
- Spring Security with form-based login
- Role-based access control (USER and ADMIN roles)
- Thymeleaf templates for web interface
- In-memory user details service

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/hagonin/spring-security-3.5.git
   cd spring-security
   ```

2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

3. Open your browser and navigate to: `http://localhost:8080`

### Test Users

The application includes two pre-configured users:

- **User**: `user` / `password` (USER role)
- **Admin**: `admin` / `admin` (ADMIN role)

### Available Endpoints

- `/` and `/home` - Public pages (no authentication required)
- `/secured` - Requires authentication 
- `/admin` - Requires ADMIN role
- `/login` - Custom login page
- `/logout` - Logout endpoint

## Project Structure

```
src/
├── main/
│   ├── java/com/example/springsecurity/
│   │   ├── SpringSecurityApplication.java
│   │   ├── config/SecurityConfig.java
│   │   └── controller/HomeController.java
│   └── resources/
│       ├── application.properties
│       └── templates/
│           ├── home.html
│           ├── secured.html
│           ├── admin.html
│           └── login.html
└── test/
```

## Security Configuration

The application uses Spring Security with:

- Form-based authentication
- BCrypt password encoding
- Role-based authorization
- Custom login page
- In-memory user details service

## Technologies Used

- Spring Boot 3.2.0
- Spring Security
- Spring Web
- Thymeleaf
- Maven
