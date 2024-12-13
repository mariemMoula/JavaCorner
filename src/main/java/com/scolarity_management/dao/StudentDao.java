package com.scolarity_management.dao;

import com.scolarity_management.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StudentDao extends JpaRepository<Student, Long> {
    // this is not a native query so we are using the java classes
    @Query(value = "select s from Student s where s.firstName like %:name% or s.lastName like %:name%")
    Page<Student> findByName(@Param("name") String name, PageRequest pageRequest);

    @Query(value = "select s from Student s where s.user.email = :email")
    Student findStudentByEmail( @Param("email") String email);

}
