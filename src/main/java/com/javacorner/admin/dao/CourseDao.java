package com.javacorner.admin.dao;

import com.javacorner.admin.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long>{


   // Page<Course> findByCourseName(String courseName, Pageable pageable);
    Page<Course> findCoursesByCourseNameContains(String keyword, Pageable pageable);


    // Here we are using the database tables and fields because we are using native query
    @Query(value ="select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id=:studentId )", nativeQuery = true)
    Page<Course> getCoursesByStudentId(@Param("studentId") Long studentId,Pageable pageable);
    //The name in the @Param annotation must match the named parameter in the query.

    // Enables the student to view the courses he s not enrolled in so that he can enroll in them
    @Query(value ="select * from courses as c where c.course_id not in (select e.course_id from enrolled_in as e where e.student_id=:studentId )", nativeQuery = true)
    Page<Course> getNonEnrolledInCoursesByStudentId(@Param("studentId") Long studentId,Pageable pageable);

    // Get Courses by InstructorId
    @Query(value = "select c from Course as c where c.instructor.instructorId=:instructorId")
    Page<Course> getCoursesByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);

}
