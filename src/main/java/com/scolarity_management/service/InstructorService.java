package com.scolarity_management.service;

import com.scolarity_management.dto.InstructorDTO;
import com.scolarity_management.entity.Instructor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InstructorService {

    Instructor loadInstructorById(Long instructorId);

    Page<InstructorDTO> findInstructorByName(String name ,int page , int size);

    InstructorDTO loadInstructorByEmail(String email);

    InstructorDTO createInstructor(InstructorDTO instructorDTO);

    InstructorDTO updateInstructor(InstructorDTO instructorDTO);

    List<InstructorDTO> FetchInstructors();

    void removeInstructor(Long instructorId);
}
