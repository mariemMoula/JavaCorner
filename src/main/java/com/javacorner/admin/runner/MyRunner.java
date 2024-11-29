package com.javacorner.admin.runner;

import com.javacorner.admin.dto.CourseDTO;
import com.javacorner.admin.dto.InstructorDTO;
import com.javacorner.admin.dto.StudentDTO;
import com.javacorner.admin.dto.UerDTO;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.mapper.StudentMpper;
import com.javacorner.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
//This class if for testing and initialization  purposes only
@Component
public class MyRunner implements CommandLineRunner {
    /**
     * If you did not add the @Autowired annotation, you would need to manually instantiate the RoleService or use constructor injection.
     */
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private InstructorService instructorService;

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService ;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
        createInstructors();
        createCourses();
        StudentDTO student =createStudent();
        assignCourseToStudent(student);
        createStudents();
    }


    private void createRoles() {
        Arrays.asList("Admin","Instructor","Student").forEach(role -> {
            roleService.createRole(role);
        });
    }
    private void createAdmin() {
        userService.createUser("admin@gmail.com", "adminPassword");
        userService.assignRoleToUser("admin@gmail.com", "Admin");

    }
    private void createInstructors() {
        for(int i = 0; i < 11; i++) {
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setFirstName("Instructor" + i+"FN");
            instructorDTO.setLastName("Instructor" + i+"LN");
            instructorDTO.setSummary("master"+i);
            UerDTO userDTO = new UerDTO();
            userDTO.setEmail("instructor" + i + "@gmail.com");
            userDTO.setPassword("instructor"+i+"Password");
            instructorDTO.setUser(userDTO);
            instructorService.createInstructor(instructorDTO);

        }

    }

    private void createCourses() {
        for(int i=0; i<11; i++) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseDescription("Java "+i);
            courseDTO.setCourseDuration(i+" months");
            courseDTO.setCourseName("Java Course "+i);
            InstructorDTO instructorDTO = new InstructorDTO();
            instructorDTO.setInstructorId(1L);
            courseDTO.setInstructorDTO(instructorDTO);
            courseService.CreateCourse(courseDTO);

        }
    }

    private StudentDTO createStudent() {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setFirstName("StudentFN");
        studentDTO.setLastName("StudentLN");
        studentDTO.setLevel("Beginner");
        UerDTO userDTO = new UerDTO();
        userDTO.setEmail("student@gmail.com");
        userDTO.setPassword("StudentPassword");
        studentDTO.setUser(userDTO);
        return  studentService.createStudent(studentDTO);

    }

    private void assignCourseToStudent(StudentDTO student) {
        courseService.assignStudentToCourse(1L, student.getStudentId());

    }

    private void createStudents() {
        for (int i = 0; i < 11; i++) {
            StudentDTO studentDTO = new StudentDTO();
            studentDTO.setFirstName("Student" + i + "FN");
            studentDTO.setLastName("Student" + i + "LN");
            studentDTO.setLevel("Beginner");
            UerDTO userDTO = new UerDTO();
            userDTO.setEmail("student" + i + "@gmail.com");
            userDTO.setPassword("Student" + i + "Password");
            studentDTO.setUser(userDTO);
            studentService.createStudent(studentDTO);
        }
    }


}
