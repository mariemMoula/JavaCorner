package com.javacorner.admin.service;

import com.javacorner.admin.dto.StudentDTO;
import com.javacorner.admin.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    Student loadStudentById(Long id);
    Page<StudentDTO> loadStudentsByName(String name, int page, int size);
    StudentDTO loadStudentByEmail(String email, int page, int size);
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(StudentDTO studentDTO);
    void removeStudent(Long id);
}
