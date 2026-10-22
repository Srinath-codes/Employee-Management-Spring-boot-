# Student Management System

A Spring Boot web application for managing student records. It uses Thymeleaf for server-rendered pages and MySQL for persistence.

## Features

- Create, view, edit, and delete student records, including bulk deletion.
- Register and log in with Spring Security; registration passwords are encoded.
- CAPTCHA support on the authentication flow.
- Request a password reset by email.
- Export student records to Excel and PDF.
- Store users, students, and password-reset tokens with Spring Data JPA.

## Requirements

- Java 17 or later
- MySQL

Maven Wrapper scripts are included, so a separate Maven installation is not required.

## Configuration

Create a MySQL database (default name: `demo`) and configure the application with environment variables:

| Variable | Default | Purpose |
| --- | --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/demo` | JDBC connection URL |
| `DB_USERNAME` | `root` | Database username |
| `DB_PASSWORD` | empty | Database password |
| `SERVER_PORT` | `8080` | HTTP port |
| `MAIL_HOST` | `smtp.gmail.com` | SMTP server |
| `MAIL_PORT` | `587` | SMTP port |
| `MAIL_USERNAME` | empty | SMTP account username |
| `MAIL_PASSWORD` | empty | SMTP account password or app password |

Set the mail variables to enable password-reset emails. Keep credentials out of source control.

## Run locally

From the repository root, run:

```bash
./mvnw spring-boot:run
```

On Windows, use:

```powershell
.\mvnw.cmd spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080). The application uses Hibernate schema updates (`ddl-auto=update`) for local development.

## Future advancements

- Add automated unit and integration tests for controllers, services, security, and exports.
- Add role-based authorization and restrict student operations to permitted users.
- Add server-side validation, friendly error pages, and consistent error handling.
- Add pagination, searching, and filtering for large student lists.
- Review security settings, including CSRF protection and password-reset token expiry validation.
- Replace automatic schema updates with versioned database migrations.
- Add API documentation and a documented production deployment setup.
- Add CI checks and container support.

## Contributing

Open an issue to discuss a larger change, then submit a pull request with a clear description of the change.
