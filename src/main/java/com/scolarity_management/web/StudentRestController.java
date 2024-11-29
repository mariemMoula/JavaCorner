package com.scolarity_management.web;


import com.scolarity_management.dto.CourseDTO;
import com.scolarity_management.dto.StudentDTO;
import com.scolarity_management.entity.User;
import com.scolarity_management.service.CourseServiceImpl;
import com.scolarity_management.service.StudentService;
import com.scolarity_management.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    private StudentService studentService ;
    private UserService userService ;
    private CourseServiceImpl courseService;

    public StudentRestController(StudentService studentService, UserService userService, CourseServiceImpl courseService) {
        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping()
    @PreAuthorize("hasRole('admin')")
    public Page<StudentDTO> searchStudents(@RequestParam(name="keyword",defaultValue = "")String keyword,
                                           @RequestParam(name="page",defaultValue = "0")int page,
                                           @RequestParam(name="size",defaultValue = "5")int size){
        return studentService.loadStudentsByName(keyword,page,size);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public void deleteStudent(@PathVariable Long id){
        studentService.removeStudent(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('admin')")
    public StudentDTO createStudent(@RequestBody StudentDTO studentDTO){
        //We need to  verify if the user already exits
        User user = userService.loadUserByEmail(studentDTO.getUser().getEmail());
        if (user != null){
            throw new IllegalArgumentException("User with email "+studentDTO.getUser().getEmail()+" already exists");
        }
        return studentService.createStudent(studentDTO);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public StudentDTO updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO){
        studentDTO.setStudentId(id);
        return studentService.updateStudent(studentDTO);
    }

    @GetMapping("/{studentId}/courses")
    public Page<CourseDTO> getStudentCourses(@PathVariable Long studentId,
                                             @RequestParam(name="page",defaultValue = "0")int page,
                                             @RequestParam(name="size",defaultValue = "5")int size){

        return courseService.fetchCoursesForStudent(studentId,page,size);
    }

    @GetMapping("/{studentId}/other-courses")
    public Page<CourseDTO> getStudentOtherCourses(@PathVariable Long studentId,
                                                  @RequestParam(name="page",defaultValue = "0")int page,
                                                  @RequestParam(name="size",defaultValue = "5")int size){
        return courseService.fetchNotEnrolledCoursesForStudent(studentId,page,size);
    }
    @GetMapping("/find")
    public StudentDTO findStudentByEmail(@RequestParam(name="email")String email){
        return studentService.loadStudentByEmail(email,0,1);
    }
}
