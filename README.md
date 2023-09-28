# Java Spring Boot Cinema Application

Welcome to the Java Spring Boot Cinema Application! This server-side rendering application is designed to provide an immersive movie-watching experience. It offers a wide range of features, including user registration, movie search and filtering, ticket booking, and more. Below, we'll walk you through the key features and dependencies of this application.

## Key Features

### User Management
- **User Registration**: New users can create accounts with unique usernames and passwords.
- **User Login**: Registered users can log in securely to access their profiles and book tickets.

### Movie Database
- **Search and Filter**: Users can search and filter movies by category to find their favorite films.
- **Movie Details**: Detailed movie pages provide descriptions, trailers, and user reviews.

### Admin Authority
- **Admin Features**: Users with ADMIN authorities can add new movies to the database, expanding the selection of available films.

### Profile Management
- **Profile Page**: Users can view and update their profiles, change passwords, and even delete their accounts if needed.
- **Ticket Reservations**: Users can view and dismiss ticket reservations.

### Security
- **Spring Security**: Robust security measures are in place to protect user data and application resources.
- **Validation**: Front-end validations are implemented with Bootstrap, and back-end validations ensure data integrity.

## Dependencies

The project's `pom.xml` file lists the following key dependencies:

- **Spring Boot**: The core framework for building Java applications.
- **Spring Security**: For implementing robust security measures.
- **Spring Data JPA**: Simplifies database operations with JPA.
- **Bootstrap**: Provides a responsive and user-friendly front-end design.
- **H2 Database**: An in-memory database for storing user and movie data during development.
- **Thymeleaf**: A templating engine for server-side rendering.

## Getting Started

To run this application on your local machine, follow these steps:

1. Clone this repository: `git clone https://github.com/your_username/Java-Spring-App-Cinema.git`
2. Navigate to the project directory: `cd Java-Spring-App-Cinema`
3. Build and run the application: `./mvnw spring-boot:run`
4. Access the application in your web browser at `http://localhost:8080`

## Contact Information

If you have any questions or need more information about this amateur app, please feel free to contact the developer:

- **Developer**: Your Name
- **Email**: your.email@example.com

Thank you for checking out our Java Spring Boot Cinema Application! We hope you enjoy using it to explore and watch your favorite movies.
