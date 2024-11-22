package com.javacorner.admin.dao;

import com.javacorner.admin.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long>{
    List<Course> findByCourseName(String courseName);
    List<Course> findCoursesByCourseNameContains(String keyword);
    // here we are using the database tables and fields because we are using native query
    @Query(value ="select * from courses as c where c.course_id in (select e.course_id from enrolled_in as e where e.student_id=: studentId )", nativeQuery = true)
    List<Course> getCoursesByStudentId(@Param("studentId") Long studentId);
    //The name in the @Param annotation must match the named parameter in the query.
}
