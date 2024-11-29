package com.scolarity_management.service;

import com.scolarity_management.dto.StudentDTO;
import com.scolarity_management.entity.Student;
import org.springframework.data.domain.Page;

public interface StudentService {
    Student loadStudentById(Long id);
    Page<StudentDTO> loadStudentsByName(String name, int page, int size);
    StudentDTO loadStudentByEmail(String email, int page, int size);
    StudentDTO createStudent(StudentDTO studentDTO);
    StudentDTO updateStudent(StudentDTO studentDTO);
    void removeStudent(Long id);
}
