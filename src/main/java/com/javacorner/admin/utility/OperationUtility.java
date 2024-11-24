package com.javacorner.admin.utility;

import com.javacorner.admin.dao.*;
import com.javacorner.admin.entity.*;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public class OperationUtility {


    // for launching
    public static void usersOperations(UserDao userDao) {
        createUsers(userDao);
        //updateUser(userDao);
        //deleteUser(userDao);
        //fetchUser(userDao);
    }

    public static void rolesOperations(RoleDao roleDao) {
        createRoles(roleDao);
        //updateRole(roleDao);
        //deleteRole(roleDao);
        //fetchRole(roleDao);

    }

    public static void instructorsOperations(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        createInstructors(userDao, instructorDao, roleDao);
        //updateInstructor(instructorDao);
        //removeInstructor(instructorDao);
        //fetchInstructors(instructorDao);


    }

    public static void studentsOperations(UserDao userDao, RoleDao roleDao, StudentDao studentDao) {
        createStudents(userDao, roleDao, studentDao);
        //updateStudent(studentDao);
        //removeStudent(studentDao);
        fetchStudents(studentDao);

    }

    public static void coursesOperations(CourseDao courseDao, InstructorDao instructorDao, StudentDao studentDao) {
        //createCourses(courseDao, instructorDao);
        //updateCourse(courseDao);
        //removeCourse(courseDao);
        //fetchCourses(courseDao);
        assignStudentsToCourses(courseDao, studentDao); // we are doing it in the course operations because the coursse is the owning side of the relationship
        //fetchCoursesForStudent(courseDao);
    }


    private static void createUsers(UserDao userDao) {
        User user1 = new User("pass1", "user1@gmail.com");
        userDao.save(user1);
        User user2 = new User("pass2", "user2@gmail.com");
        userDao.save(user2);
        User user3 = new User("pass3", "user3@gmail.com");
        userDao.save(user3);
        User user4 = new User("pass4", "user4@gmail.com");
        userDao.save(user4);

    }

    private static void updateUser(UserDao userDao) {
        User user = userDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setEmail("userEmailUpdated@gmail.com");
        userDao.save(user);

    }

    private static void deleteUser(UserDao userDao) {
        User user = userDao.findById(3L).orElseThrow(() -> new EntityNotFoundException("User not found"));
        userDao.delete(user);
        // or simply use  userDao.deleteById(3L);
    }

    private static void fetchUser(UserDao userDao) {
        userDao.findAll().forEach(user -> System.out.println(user.toString()));

    }

    private static void createRoles(RoleDao roleDao) {
        Role role1 = new Role("ADMIN");
        roleDao.save(role1);
        Role role2 = new Role("Instructor");
        roleDao.save(role2);
        Role role3 = new Role("Student");
        roleDao.save(role3);
    }

    private static void updateRole(RoleDao roleDao) {
        Role role = roleDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Role Not Found"));
        role.setName("NewAdmin");
        roleDao.save(role);
    }

    private static void deleteRole(RoleDao roleDao) {
        roleDao.deleteById(2L);
    }

    private static void fetchRole(RoleDao roleDao) {
        roleDao.findAll().forEach(role -> System.out.println(role.toString()));
    }

    public static void assignRolesToUsers(RoleDao roleDao, UserDao userDao) {
        Role role = roleDao.findByName("Admin");
        if (role == null) throw new EntityNotFoundException("Role Not Found");
        List<User> users = userDao.findAll();
        users.forEach(user -> {
            user.assignRoleToUser(role);
            userDao.save(user);

        });

    }

    private static void createInstructors(UserDao userDao, InstructorDao instructorDao, RoleDao roleDao) {
        Role role = roleDao.findByName("Instructor");
        if (role == null) throw new EntityNotFoundException("Role Not Found ");


        User user1 = new User("pass1", "instructorUser1@gmail.com");
        userDao.save(user1);
        user1.assignRoleToUser(role);
        userDao.save(user1);


        Instructor instructor1 = new Instructor("instructor1FN", "instructor1LN", "Experienced Instructor", user1);
        instructorDao.save(instructor1);
        /***************************************************************************/
        User user2 = new User("pass2", "instructorUser2@gmail.com");
        userDao.save(user2); // we need to save the user first to get the id
        user2.assignRoleToUser(role); // we need to assign the role to the user
        userDao.save(user2); // we need to save the user after assigning the role


        Instructor instructor2 = new Instructor("instructor2FN", "instructor2LN", "Senior Instructor", user2);
        instructorDao.save(instructor2);


    }


    private static void fetchInstructors(InstructorDao instructorDao) {
        instructorDao.findAll().forEach(instructor -> System.out.println(instructor.toString()));
    }

    private static void removeInstructor(InstructorDao instructorDao) {
        instructorDao.deleteById(1L);
    }

    private static void updateInstructor(InstructorDao instructorDao) {
        Instructor instructor = instructorDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Instructor Not Found"));
        instructor.setSummary("New Summary");
        instructorDao.save(instructor);

    }

    private static void createStudents(UserDao userDao, RoleDao roleDao, StudentDao studentDao) {
        Role role = roleDao.findByName("Student");
        if (role == null) throw new EntityNotFoundException("Role Not Found ");

        User user1 = new User("pass1", "sdtUser1@gmail.com");
        userDao.save(user1);
        user1.assignRoleToUser(role);
        userDao.save(user1);

        Student student1 = new Student("master", "student1LN", "student1FN", user1);
        studentDao.save(student1);
        /****************************************************/
        User user2 = new User("pass2", "sdtUser2@gmail.com");
        userDao.save(user2);
        user2.assignRoleToUser(role);
        userDao.save(user2);



        Student student2 = new Student("PHD", "student2LN", "student2FN", user2);
        studentDao.save(student2);


    }

    private static void updateStudent(StudentDao studentDao) {
        Student student = studentDao.findById(2L).orElseThrow(() -> new EntityNotFoundException("Student Not Found"));
        student.setFirstName("UpdatedStusentFN");
        student.setLastName("UpdatedStusentLN");
        studentDao.save(student);


    }

    private static void removeStudent(StudentDao studentDao) {
        studentDao.deleteById(1L);
    }

    private static void fetchStudents(StudentDao studentDao) {
        studentDao.findAll().forEach(student -> System.out.println(student.toString()));
    }


    private static void createCourses(CourseDao courseDao, InstructorDao instructorDao) {
        Instructor instructor = instructorDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Instructor Not Found"));

        Course course1 = new Course("Java", "Java Programming", "3 months", instructor);
        courseDao.save(course1);

        Course course2 = new Course("Python", "Python Programming", "2 months", instructor);
        courseDao.save(course2);

        Course course3 = new Course("Spring", "Spring Framework", "3 months", instructor);
        courseDao.save(course3);


    }

    private static void updateCourse(CourseDao courseDao) {
        Course course = courseDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Course Not Found"));
        course.setCourseDescription("Updated Description");
        courseDao.save(course);
    }

    private static void fetchCourses(CourseDao courseDao) {
        courseDao.findAll().forEach(course -> System.out.println(course.toString()));
    }

    private static void removeCourse(CourseDao courseDao) {
        courseDao.deleteById(2L);
    }

    private static void assignStudentsToCourses(CourseDao courseDao, StudentDao studentDao) {
        Optional<Student> student1 = studentDao.findById(1L);
        Optional<Student> student2 = studentDao.findById(2L);
        Course course = courseDao.findById(1L).orElseThrow(() -> new EntityNotFoundException("Course Not Found"));
        student1.ifPresent(student -> {
            course.assignStudentToCourse(student);

        });

        student2.ifPresent(student -> {
            course.assignStudentToCourse(student);

        });
        courseDao.save(course);
    }

    private static void fetchCoursesForStudent(CourseDao courseDao) {
        courseDao.getCoursesByStudentId(1L).forEach(course -> System.out.println(course.toString()));
    }

}
