package com.scolarity_management.mapper;


import com.scolarity_management.dto.StudentDTO;
import com.scolarity_management.entity.Student;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class StudentMpper {

    // Entity-> DTO
    public StudentDTO fromStudentToDTO(Student student ){
        StudentDTO studentDTO = new StudentDTO(); // create a new StudentDTO object
        //studentDTO.setStudentId(student.getStudentId()); // This is a way to set the values of the StudentDTO object
        BeanUtils.copyProperties(student, studentDTO); // This is another way to set the values of the StudentDTO object
        return studentDTO;
    }
    // DTO -> Entity
    public Student fromDTOToStudent(StudentDTO studentDTO){
        Student student = new Student(); // create a new Student object
        //student.setStudentId(studentDTO.getStudentId()); // This is a way to set the values of the Student object
        BeanUtils.copyProperties(studentDTO, student); // This is another way to set the values of the Student object
        return student;
    }

}
