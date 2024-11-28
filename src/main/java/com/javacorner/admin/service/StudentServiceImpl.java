package com.javacorner.admin.service;

import com.javacorner.admin.dao.StudentDao;
import com.javacorner.admin.dto.StudentDTO;
import com.javacorner.admin.entity.Course;
import com.javacorner.admin.entity.Student;
import com.javacorner.admin.entity.User;
import com.javacorner.admin.mapper.StudentMpper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;


@Transactional
@Service
public class StudentServiceImpl implements StudentService{

    private final StudentDao studentDao ;
    private StudentMpper studentMpper;
    private UserService userService;
    public StudentServiceImpl(StudentDao studentDao, StudentMpper studentMpper, UserService userService) {
        this.studentDao = studentDao;
        this.studentMpper = studentMpper;
        this.userService = userService;
    }

    @Override
    public Student loadStudentById(Long id) {
        return studentDao.findById(id).orElseThrow(()-> new EntityNotFoundException("Student with ID "+id+" not found"));
    }

    @Override
    public Page<StudentDTO> loadStudentsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Student> studentsPage=studentDao.findByName(name,pageRequest);
        return new PageImpl<>(studentsPage.getContent().stream().map(student -> studentMpper.fromStudentToDTO(student)).toList(),studentsPage.getPageable(),studentsPage.getTotalElements());
    }

    @Override
    public StudentDTO loadStudentByEmail(String email, int page, int size) {
        return studentMpper.fromStudentToDTO(studentDao.findStudentByEmail(email));
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        // To create a student , we need to create a user , and for that we will use the user service
        User user = userService.createUser(studentDTO.getUser().getEmail(),studentDTO.getUser().getPassword());
        userService.assignRoleToUser(studentDTO.getUser().getEmail(),"Student");
        Student newStudent = studentMpper.fromDTOToStudent(studentDTO);
        newStudent.setUser(user);
        Student savedUser=studentDao.save(newStudent);
        return studentMpper.fromStudentToDTO(studentDao.save(savedUser));
    }

    @Override
    public StudentDTO updateStudent(StudentDTO studentDTO) {
        //only the name , email ect are updated , yet the courss or user are not updated , and we don t wan to lose them
        Student student = studentDao.findById(studentDTO.getStudentId()).orElseThrow(()-> new EntityNotFoundException("Student with ID "+studentDTO.getStudentId()+" not found"));
        Student newStudent = studentMpper.fromDTOToStudent(studentDTO);
        newStudent.setUser(student.getUser());
        newStudent.setCourses(student.getCourses());
        Student updatedStudent =studentDao.save(newStudent);
        return studentMpper.fromStudentToDTO(updatedStudent);
    }

    @Override
    public void removeStudent(Long id) {
        Student student = loadStudentById(id);
        //To remove a student , we need to remove the student subscription to that course
        Iterator<Course> courseIterator = student.getCourses().iterator();
        if(courseIterator.hasNext()){
            Course course = courseIterator.next();
            course.getStudents().remove(student);
        }
        studentDao.deleteById(id);
    }
}
