Project Title: Employee Leave Management System (Backend API)

Brief Description: Robust backend for an Employee Leave Management System using Spring Boot (Java) and MySQL. The system exposes RESTful APIs for all core functionalities, including employee data management, leave application submission, status tracking, and managerial approval/rejection workflows. Rigorously tested and validated API endpoints using Postman, ensuring data integrity and reliable service operation for managing employee leave requests and balances.

Technologies Used: Spring Boot, Java, MySQL, Maven, Postman.

Prerequisites:
Java JDK (e.g., 11 or 17)
Maven
MySQL Server
Postman

Database Setup:

Ensure MySQL is running.

Create the database: CREATE DATABASE IF NOT EXISTS leave_management_db;
(Optional: Create a specific MySQL user and grant privileges).

Application Configuration:
Navigate to src/main/resources/.

Copy application.properties.example to application.properties.

Open application.properties and update spring.datasource.username and spring.datasource.password 
with your MySQL credentials.

Building the Project:
mvn clean install

Running the Project:

mvn spring-boot:run

The application will start on http://localhost:8080 (or your configured port).

API Endpoints / Testing with Postman:

A brief list of the main API endpoints.

"A Postman collection is provided in the postman/ directory (LeaveManagementAPI.postman_collection.json)."


"To use it:

Open Postman.

Click 'Import'.

Upload the LeaveManagementAPI.postman_collection.json file.
(If you provided an environment file: Import LeaveManagement.postman_environment.json and select it as the active environment.)

You can now send requests to the API endpoints."
