package com.javacorner.admin.dao;

import com.javacorner.admin.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InstructorDao extends JpaRepository<Instructor, Long> {
    // here we are using the java classes   because we are using JPQL
    @Query(value = "select i from Instructor i where i.firstName like %:name% or i.lastName like %:name%") // this is not a native SQL query so we use  the classes name not the db name
    List<Instructor> findInstructorsByName(@Param("name") String name); // here we re defining the parameter name that we re using in the query


    @Query(value = "select i from Instructor i where i.user.email = :email")
    List<Instructor> findInstructorByEmail(@Param("email") String email);



}