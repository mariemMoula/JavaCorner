package com.javacorner.admin.dao;

import com.javacorner.admin.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentDao extends JpaRepository<Student, Long> {
    // this is not a native query so we are using the java classes
    @Query(value = "select s from Student s where s.firstName like %:name% or s.lastName like %:name%")
    List<Student> findByName(@Param("name") String name);

    @Query(value = "select s from Student s where s.user.email = :email")
    List<Student> findStudentByEmail( @Param("email") String email);

}
