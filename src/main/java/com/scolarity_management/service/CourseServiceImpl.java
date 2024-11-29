package com.scolarity_management.service;

import com.scolarity_management.dao.CourseDao;
import com.scolarity_management.dao.InstructorDao;
import com.scolarity_management.dao.StudentDao;
import com.scolarity_management.dto.CourseDTO;
import com.scolarity_management.entity.Course;
import com.scolarity_management.entity.Instructor;
import com.scolarity_management.entity.Student;
import com.scolarity_management.mapper.CourseMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Transactional
@Service
public class CourseServiceImpl implements CourseService{

    private CourseDao courseDao;
    private CourseMapper courseMapper;
    private InstructorDao  instructorDao;
    private StudentDao studentDao;
    @Autowired
    public CourseServiceImpl(CourseDao courseDao,CourseMapper courseMapper ,InstructorDao instructorDao,StudentDao studentDao) {
        this.courseDao = courseDao;
        this.courseMapper= courseMapper;
        this.instructorDao=instructorDao;
        this.studentDao=studentDao;

    }

    @Override
    public Course loadCourseById(Long courseId) {
        return courseDao.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course with ID "+courseId+" not found"));
    }

    @Override
    public CourseDTO CreateCourse(CourseDTO courseDTO) {
        Course course = courseMapper.fromCourseDTOToCourse(courseDTO);
        Instructor instructor = instructorDao.findById(courseDTO.getInstructorDTO().getInstructorId()).orElseThrow(()-> new EntityNotFoundException("Instructor with ID "+courseDTO.getInstructorDTO().getInstructorId()+" not found"));
        course.setInstructor(instructor);
        Course savedCourse = courseDao.save(course);
        return courseMapper.fromCourseToCourseDTO(savedCourse) ;
    }

    @Override
    public CourseDTO updateCourse(CourseDTO courseDTO) {
        Course loadedCourse = courseDao.findById(courseDTO.getCourseId()).orElseThrow(()-> new EntityNotFoundException("Course with ID "+courseDTO.getCourseId()+" not found"));
        //The instructor will be found thanks to its ID , that what we will recieve in  request body
        Instructor instructor = instructorDao.findById(courseDTO.getInstructorDTO().getInstructorId()).orElseThrow(()-> new EntityNotFoundException("Instructor with ID "+courseDTO.getInstructorDTO().getInstructorId()+" not found"));
        Course course = courseMapper.fromCourseDTOToCourse(courseDTO);
        course.setInstructor(instructor);
        course.setStudents(loadedCourse.getStudents());
        Course savedCourse = courseDao.save(course);
        return courseMapper.fromCourseToCourseDTO(savedCourse);
    }

    @Override
    public Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesPage =courseDao.findCoursesByCourseNameContains(keyword, pageRequest);

        return new PageImpl<>(coursesPage.getContent().stream().map(course -> courseMapper.fromCourseToCourseDTO(course)).collect(Collectors.toList()),pageRequest,coursesPage.getTotalElements());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        //We are using the student ID because that what will be given to us in the request body
        Student student = studentDao.findById(studentId).orElseThrow(()-> new EntityNotFoundException("Student with ID "+studentId+" not found"));
        //Course course = courseDao.findById(courseId).orElseThrow(()-> new EntityNotFoundException("Course with ID "+courseId+" not found"));
        Course course = loadCourseById(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public Page<CourseDTO> fetchCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> studentCoursesPage =courseDao.getCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(studentCoursesPage.getContent().stream().map(course -> courseMapper.fromCourseToCourseDTO(course)).collect(Collectors.toList()),pageRequest,studentCoursesPage.getTotalElements());
    }

    @Override
    public Page<CourseDTO> fetchNotEnrolledCoursesForStudent(Long studentId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> nonEnrolledInCoursesPage =courseDao.getNonEnrolledInCoursesByStudentId(studentId, pageRequest);
        return new PageImpl<>(nonEnrolledInCoursesPage.getContent().stream().map(course -> courseMapper.fromCourseToCourseDTO(course)).collect(Collectors.toList()),pageRequest,nonEnrolledInCoursesPage.getTotalElements());
    }

    @Override
    public void removeCourse(Long courseId) {
        courseDao.deleteById(courseId);

    }

    @Override
    public Page<CourseDTO> fetchCoursesByInstructor(Long instructorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Course> coursesByInstructor =courseDao.getCoursesByInstructorId(instructorId, pageRequest);
        return new PageImpl<>(coursesByInstructor.getContent().stream().map(course -> courseMapper.fromCourseToCourseDTO(course)).collect(Collectors.toList()),pageRequest,coursesByInstructor.getTotalElements());


    }
}
/**
 * Characteristics:
 *
 * The logic for loading a Course is delegated to a separate method (loadCourseById).
 * Reusability: Any method that needs to load a course can simply call loadCourseById.
 * Advantages:
 *
 * Reusability: The loadCourseById method centralizes the logic for retrieving a course. If other methods (e.g., deleteCourse, updateCourse) need to fetch a course, they can reuse this logic.
 * Consistency: If you need to add extra behavior to course loading (e.g., logging, additional validation), you only need to update loadCourseById, and all dependent methods benefit from it.
 * Cleaner main method: The assignStudentToCourse method focuses on the business logic rather than retrieval logic.
 */