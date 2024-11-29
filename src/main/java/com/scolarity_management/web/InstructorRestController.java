package com.scolarity_management.web;


import com.scolarity_management.dto.CourseDTO;
import com.scolarity_management.dto.InstructorDTO;
import com.scolarity_management.entity.User;
import com.scolarity_management.service.CourseServiceImpl;
import com.scolarity_management.service.InstructorService;
import com.scolarity_management.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('admin')")
    public Page<InstructorDTO> searchInstructors(@RequestParam(name="keyWord",defaultValue = "")String keyWord,
                                                 @RequestParam(name="page",defaultValue = "0")int page,
                                                 @RequestParam(name="size",defaultValue = "5")int size){
        return instructorService.findInstructorByName(keyWord,page,size);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('admin')")
    public  List<InstructorDTO> getAllInstructors(){
        return instructorService.FetchInstructors();
    }

    @DeleteMapping("/{instructorId}")
    @PreAuthorize("hasRole('admin')")
    public void deleteInstructor(@PathVariable Long instructorId){
        instructorService.removeInstructor(instructorId);
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
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
    @PreAuthorize("hasRole('admin')")
    public InstructorDTO updateInstructor(@RequestBody InstructorDTO instructorDTO, @PathVariable Long instructorId){
        instructorDTO.setInstructorId(instructorId);
        return instructorService.updateInstructor(instructorDTO);
    }

    @GetMapping("/{instructorId}/courses")
    @PreAuthorize("hasRole('admin')")
    public Page<CourseDTO> fetchCoursesByInstructor(@PathVariable Long instructorId,
                                                    @RequestParam(name="page",defaultValue = "0")int page,
                                                    @RequestParam(name="size",defaultValue = "5")int size){
        return courseService.fetchCoursesByInstructor(instructorId,page,size);
    }

    @GetMapping("/findByEmail")
    @PreAuthorize("hasAnyRole('admin' )")
    public InstructorDTO findInstructorByEmail(@RequestParam(name="email")String email){
        return instructorService.loadInstructorByEmail(email);
    }

}
