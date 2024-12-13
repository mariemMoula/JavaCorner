### Project Description

**Scolarity Management** is a Spring Boot application designed to manage user authentication and authorization. It provides a comprehensive solution for managing user roles and permissions. The application features functionalities for managing courses, such as searching, creating, updating, deleting courses, and enrolling students in courses. Built using Java and Maven, Scolarity Management offers a robust and scalable solution for web applications.

### Technologies Used
- Java
- Spring Boot
- Maven
- Spring Security
- BCryptPasswordEncoder
- MySQL Database (or any other database you used)
- Postman & Swagger (for API testing)

### Functionalities

**User Authentication and Authorization**: The application uses Spring Security to manage user authentication and authorization. It includes a `UserDetailsServiceImpl` class that loads user details from the database and creates a `UserDetails` object, which is used by Spring Security to authenticate and authorize users. The application supports role-based access control, ensuring that only users with the appropriate roles can access certain endpoints.

**Course Management**: The `CourseRestController` class defines several endpoints to interact with course data. Users can search for courses, create new courses, update existing courses, delete courses, and enroll students in courses. The application uses DTOs (Data Transfer Objects) to transfer data between the client and the server.

**Instructor Management**: The `InstructorRestController` class provides endpoints for managing instructors. Users can search for instructors, get a list of all instructors, delete instructors, create new instructors, update existing instructors, and fetch courses taught by a specific instructor. The application ensures that only users with the `ADMIN` role can access these endpoints.

**Student Enrollment**: The application allows students to enroll in available courses, ensuring seamless integration of enrollment data with the course and instructor management features.

**Data Validation and Error Handling**: The application incorporates robust data validation mechanisms to ensure data integrity and meaningful error handling to guide users when incorrect inputs are provided.

**Dashboard and Reporting**: A dashboard provides insights into user activities, enrollment statistics, and course popularity, helping administrators make informed decisions.

**Search and Filtering**: Advanced search and filtering options enable users to quickly find specific courses, instructors, or students based on various criteria.

### Security

**Role-Based Access Control**: The application uses Spring Security's method-level security annotations to restrict access to certain endpoints based on user roles. For example, the `@PreAuthorize` annotation is used to ensure that only users with the `ADMIN` or `INSTRUCTOR` roles can access specific endpoints. This ensures that sensitive operations are only performed by authorized users.

**Password Encoding**: The application uses the `BCryptPasswordEncoder` to encode user passwords before storing them in the database. This adds an extra layer of security by ensuring that passwords are stored in a hashed format, making it difficult for attackers to retrieve the original passwords even if they gain access to the database.
