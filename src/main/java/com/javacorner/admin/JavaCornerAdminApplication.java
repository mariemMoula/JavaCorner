package com.javacorner.admin;

import com.javacorner.admin.dao.*;
import com.javacorner.admin.utility.OperationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaCornerAdminApplication implements CommandLineRunner {
    @Autowired
    private UserDao userDao ;

    @Autowired
    private CourseDao courseDao ;

    @Autowired
    private InstructorDao instructorDao;

    @Autowired
    private StudentDao studentDao ;

    @Autowired
    private RoleDao roleDao ;

    public static void main(String[] args) {
        SpringApplication.run(JavaCornerAdminApplication.class, args);
    }

    // Now when our application starts, the run method will be executed.
    @Override
    public void run(String... args) throws Exception {
        //we' ll connect to our utility class and test the operations
    //       OperationUtility.usersOperations(userDao);
   //     OperationUtility.rolesOperations(roleDao);
  //      OperationUtility.assignRolesToUsers(roleDao,userDao);
  //      OperationUtility.instructorsOperations(userDao, instructorDao, roleDao);
  //      OperationUtility.studentsOperations(userDao, roleDao, studentDao);
        OperationUtility.coursesOperations(courseDao, instructorDao, studentDao);




    }
}
/*, you cannot execute custom code directly within the SpringApplication.run(JavaCornerAdminApplication.class, args); line.
 This line is responsible for starting the Spring Boot application and initializing the application context.
To execute custom code after the application has started, you should override the run method of the CommandLineRunner interface, as shown in your current implementation.
 */

/*Here is a brief explanation:
SpringApplication.run(JavaCornerAdminApplication.class, args); starts the Spring Boot application.
Overriding the run method in the CommandLineRunner interface allows you to execute custom code after the application context is fully loaded.*/