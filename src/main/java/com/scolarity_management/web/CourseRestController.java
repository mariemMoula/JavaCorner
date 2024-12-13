package com.scolarity_management.web;

import com.scolarity_management.dto.CourseDTO;
import com.scolarity_management.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseRestController {

    private CourseService courseService;

    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Page<CourseDTO> searchCourses(@RequestParam(name="keyWord",defaultValue = "")String keyWord,
                                         @RequestParam(name="page",defaultValue = "0")int page,
                                         @RequestParam(name="size",defaultValue = "5")int size){

        return courseService.findCoursesByCourseName(keyWord,page,size);

    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('admin', 'instructor')")
    public void deleteCourse(@PathVariable Long courseId){
        courseService.removeCourse(courseId);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('admin', 'instructor')")
    public CourseDTO saveCourse(@RequestBody CourseDTO courseDTO){
        return courseService.CreateCourse(courseDTO);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('admin', 'instructor')")
    public CourseDTO updateCourse(@RequestBody CourseDTO courseDTO,@PathVariable Long courseId){
        courseDTO.setCourseId(courseId);
        return courseService.updateCourse(courseDTO);
    }

    @PostMapping("/{courseId}/enroll/students/{studentId}")
    @PreAuthorize("hasAnyRole('admin', 'instructor', 'student')")
    public void enrollStudentInCourse(@PathVariable Long courseId,@PathVariable Long studentId){
        courseService.assignStudentToCourse(courseId,studentId);
    }
}
/*
1. @PathVariable
Purpose: Used to extract variables from the URI path (the URL itself).
Location of data: The data is passed as part of the URL path. Typically used for dynamic parts of the URL (e.g., resource identifiers).
Usage: Often used in RESTful APIs to extract values from the URL path
2. @RequestParam
Purpose: Used to extract query parameters from the request URL.
Location of data: The data is passed as part of the query string in the URL, which comes after the ? symbol.
Usage: Typically used for optional parameters, search filters, or other parameters sent via the URL query string.
 */


/**
 * The courseDTO class is a Data Transfer Object. It is used to encapsulate the data that is sent to or received from the client in your API.
 *
 * When you're creating a new course (e.g., through a POST request), the courseDTO object will not have a courseId initially, because the courseId is typically generated by the database (i.e., an auto-incremented field).
 * When you're updating an existing course (e.g., through a PUT request), the courseDTO will include the courseId, because the client should send the ID of the course that is being updated.
 */