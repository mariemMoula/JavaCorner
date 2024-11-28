package com.javacorner.admin.web;


import com.javacorner.admin.dto.CourseDTO;
import com.javacorner.admin.dto.InstructorDTO;
import com.javacorner.admin.entity.Instructor;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.service.CourseServiceImpl;
import com.javacorner.admin.service.InstructorService;
import com.javacorner.admin.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/instructors")
public class InstructorRestController {

    private InstructorService instructorService;
    private UserService userService;
    private CourseServiceImpl courseService;
    public InstructorRestController(InstructorService instructorService, UserService userService, CourseServiceImpl courseService) {
        this.instructorService = instructorService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping
    public Page<InstructorDTO> searchInstructors(@RequestParam(name="keyWord",defaultValue = "")String keyWord,
                                                 @RequestParam(name="page",defaultValue = "0")int page,
                                                 @RequestParam(name="size",defaultValue = "5")int size){
        return instructorService.findInstructorByName(keyWord,page,size);
    }

    @GetMapping("/all")
    public  List<InstructorDTO> getAllInstructors(){
        return instructorService.FetchInstructors();
    }

    @DeleteMapping("/{instructorId}")
    public void deleteInstructor(@PathVariable Long instructorId){
        instructorService.removeInstructor(instructorId);
    }

    @PostMapping
    public InstructorDTO saveInstructor(@RequestBody InstructorDTO instructorDTO){
        //We first need to do a test to confirm wether any user  with the email (which was sent) exists
        //Let s load the user
        User user = userService.loadUserByEmail(instructorDTO.getUser().getEmail());
        if(user != null){
            throw new RuntimeException("User with email "+instructorDTO.getUser().getEmail()+" Already Exists!");
        }else {
            //If the user does not exist, we can now create the instructor
            return instructorService.createInstructor(instructorDTO);
        }

    }

    @PutMapping("/{instructorId}")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO, @PathVariable Long instructorId){
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }

    @GetMapping("/{instructorId}/courses")
    public Page<CourseDTO> fetchCoursesByInstructor(@PathVariable Long instructorId,
                                                    @RequestParam(name="page",defaultValue = "0")int page,
                                                    @RequestParam(name="size",defaultValue = "5")int size){
        return courseService.fetchCoursesByInstructor(instructorId,page,size);
    }

    @GetMapping("/findByEmail")
    public InstructorDTO findInstructorByEmail(@RequestParam(name="email")String email){
        return instructorService.loadInstructorByEmail(email);
    }

}
