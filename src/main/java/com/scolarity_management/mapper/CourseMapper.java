package com.scolarity_management.mapper;

import com.scolarity_management.dto.CourseDTO;
import com.scolarity_management.entity.Course;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

    //Dependency Injection of the InstructorMapper with constructor injection

    private final InstructorMapper instructorMapper;

    @Autowired
    public CourseMapper(InstructorMapper instructorMapper) {
        this.instructorMapper = instructorMapper;
    }
    // Entity-> DTO
    public CourseDTO fromCourseToCourseDTO(Course course) {


        CourseDTO courseDTO = new CourseDTO();
        BeanUtils.copyProperties(course, courseDTO);
        // We need to do a mapping from the instructor entity to the instructorDTO
        // because the courseDTO  contains ann instructorDTO as argument
        //So we will inject by constructor
        courseDTO.setInstructorDTO(instructorMapper.fromInstructorToDTO(course.getInstructor()));
        return courseDTO;
    }
    // DTO -> Entity
    public Course fromCourseDTOToCourse(CourseDTO courseDTO) {
        Course course = new Course();
        BeanUtils.copyProperties(courseDTO, course);
        /*/
        Notice that we did not set the instructor by the courseDTO , bcz when the user does a new post request ,
        they'll only send the instructorId , then from that instructorId we will fetch the instructor object from the database
        in the service layer and then we will set the instructor object to the course object that we need to save ,
        Similarly for the update request

         */
        return course;
    }

}
