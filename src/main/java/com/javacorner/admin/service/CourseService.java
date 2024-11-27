package com.javacorner.admin.service;

import com.javacorner.admin.dto.CourseDTO;
import com.javacorner.admin.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface CourseService {

    Course loadCourseById(Long courseId);
    CourseDTO CreateCourse(CourseDTO courseDTO);
    CourseDTO updateCourse(CourseDTO courseDTO);
    Page<CourseDTO> findCoursesByCourseName(String keyword, int page, int size);
    void assignStudentToCourse(Long courseId, Long studentId);
    Page<CourseDTO> fetchCoursesForStudent(Long studentId, int page, int size);
    Page<CourseDTO> fetchNotEnrolledCoursesForStudent(Long studentId, int page, int size);
    void removeCourse(Long courseId);
    Page<CourseDTO> fetchCoursesByInstructor(Long instructorId, int page, int size);
}
