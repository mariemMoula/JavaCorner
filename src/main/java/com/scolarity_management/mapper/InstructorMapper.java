package com.scolarity_management.mapper;




import com.scolarity_management.dto.InstructorDTO;
import com.scolarity_management.entity.Instructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
/*f you do not add the @Service annotation to the InstructorMapper class,
Spring will not recognize it as a Spring-managed bean.
This means that you will not be able to inject it into other components using Spring's dependency injection.
As a result, you would need to manually instantiate the InstructorMapper class wherever it is needed,
which can lead to more boilerplate code and less efficient resource management.*/

@Service
public class InstructorMapper {

    // Entity-> DTO
    public InstructorDTO fromInstructorToDTO(Instructor instructor ){
        InstructorDTO instructorDTO = new InstructorDTO(); // create a new InstructorDTO object
        //instructorDTO.setInstructorId(instructor.getInstructorId()); // This is a way to set the values of the InstructorDTO object
        BeanUtils.copyProperties(instructor, instructorDTO); // This is another way to set the values of the InstructorDTO object
        return instructorDTO;
    }

    // DTO -> Entity
    public Instructor fromDTOToInstructor(InstructorDTO instructorDTO){
        Instructor instructor = new Instructor(); // create a new Instructor object
        //instructor.setInstructorId(instructorDTO.getInstructorId()); // This is a way to set the values of the Instructor object
        BeanUtils.copyProperties(instructorDTO, instructor); // This is another way to set the values of the Instructor object
        return instructor;
    }

}
/**
 * If you make the methods in the InstructorMapper class static, you can call them without needing an instance of the class.
 * This would eliminate the need for the @Service annotation and dependency injection.
 * However, using static methods can make testing and maintaining the code more difficult, as it reduces flexibility and makes it harder to mock dependencies.
 */
