```markdown
# OKS API Service

This is a Java-based API service for the OKS project. It uses Maven for dependency management and Spring Boot as the framework.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

- Java 8 or higher
- Maven
- MySQL

### Installing

1. Clone the repository
```bash
git clone https://github.com/selenavuk/oks-api-service.git
```

2. Navigate into the cloned repository
```bash
cd oks-api-service
```

3. Install the dependencies
```bash
mvn install
```

## Setting Up the Database

The application uses MySQL for data storage. Ensure you have MySQL installed and running.

The application is configured to connect to a MySQL database at `jdbc:mysql://localhost:3306/oks` with the username `root` and password `roots`. You can change these settings in the `application-develop.properties` file.

## Running the application

You can run the application using the following command:

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8081`.

## Built With

- [Java](https://www.java.com/) - The programming language used
- [Maven](https://maven.apache.org/) - Dependency Management
- [Spring Boot](https://spring.io/projects/spring-boot) - The framework used for the API service
- [MySQL](https://www.mysql.com/) - Database

```
