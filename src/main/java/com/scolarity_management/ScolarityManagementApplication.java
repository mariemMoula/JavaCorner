package com.scolarity_management;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ScolarityManagementApplication {


    public static void main(String[] args) {
        SpringApplication.run(ScolarityManagementApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}





/*
import com.javacorner.admin.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import com.javacorner.admin.utility.OperationUtility;

    To use this part of the code add the implements CommandLineRunner  is the  ScolarityManagementApplication



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


*/


/*, you cannot execute custom code directly within the SpringApplication.run(ScolarityManagementApplication.class, args); line.
 This line is responsible for starting the Spring Boot application and initializing the application context.
To execute custom code after the application has started, you should override the run method of the CommandLineRunner interface, as shown in your current implementation.
 */

/*Here is a brief explanation:
SpringApplication.run(ScolarityManagementApplication.class, args); starts the Spring Boot application.
Overriding the run method in the CommandLineRunner interface allows you to execute custom code after the application context is fully loaded.*/