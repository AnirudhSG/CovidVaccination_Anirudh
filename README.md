Sure, here's a README file for your VaccinationController APIs:

---

# Vaccination System APIs

This repository contains the source code for APIs related to a Vaccination System. These APIs are designed to manage citizen registration, vaccination status tracking, and related operations.

## Technologies Used

- Java
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Hibernate
- Postgres(Assuming based on Spring Data JPA usage)

## API Endpoints

### 1. Register Citizen

- **Endpoint**: POST `/api/citizens/register`
- **Description**: Registers a new citizen in the vaccination system.
- **Request Body**: CitizenEntity
- **Response**: Newly registered citizen or error message if validation fails.

### 2. Get All Citizens

- **Endpoint**: GET `/api/citizens/list/all`
- **Description**: Retrieves a list of all registered citizens.
- **Response**: List of CitizenEntity objects or error message.

### 3. Get Partially Vaccinated Citizens

- **Endpoint**: GET `/api/citizens/list/status/partial`
- **Description**: Retrieves a list of citizens with vaccination status as PARTIAL.
- **Response**: List of CitizenEntity objects or error message.

### 4. Get Fully Vaccinated Citizens

- **Endpoint**: GET `/api/citizens/list/status/full`
- **Description**: Retrieves a list of citizens with vaccination status as FULL.
- **Response**: List of CitizenEntity objects or error message.

### 5. Get Citizen by Aadhar Number

- **Endpoint**: GET `/api/citizens/list/{aadhar}`
- **Description**: Retrieves a single citizen by their Aadhar number.
- **Response**: CitizenEntity object or error message.

## Usage

1. Clone the repository.
2. Set up the necessary database configurations.
3. Run the Spring Boot application.
4. Access the APIs using the provided endpoints.

## Contribution

Contributions are welcome! If you find any issues or have suggestions for improvement, feel free to open an issue or submit a pull request.
