package com.scolarity_management.service;

import com.scolarity_management.dao.InstructorDao;
import com.scolarity_management.dao.UserDao;
import com.scolarity_management.dto.InstructorDTO;
import com.scolarity_management.entity.Course;
import com.scolarity_management.entity.Instructor;
import com.scolarity_management.entity.User;
import com.scolarity_management.mapper.InstructorMapper;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService{

    private final UserDao userDao;
    private InstructorDao instructorDao ;
    private InstructorMapper instructorMapper ;
    private UserService userService;
    private CourseService courseService;

    public InstructorServiceImpl(InstructorDao instructorDao, InstructorMapper instructorMapper, UserService userService, UserDao userDao, CourseService courseService) {
        this.instructorDao = instructorDao;
        this.instructorMapper = instructorMapper;
        this.userService = userService;
        this.userDao = userDao;
        this.courseService = courseService;
    }

    @Override
    public Instructor loadInstructorById(Long instructorId) {
        return instructorDao.findById(instructorId).orElseThrow(()-> new RuntimeException("Instructor with ID "+instructorId+" not found"));
    }

    /**
     * What it does:
     * Step 1: Retrieves the list of Instructor objects from the instructorsPage (getContent()).
     * Step 2: Converts the list into a stream so we can process each Instructor.
     * Step 3: Maps each Instructor to an InstructorDTO using the instructorMapper.
     * Step 4: Collects the transformed InstructorDTO objects into a new list.
     * Step 5: Wraps the list of InstructorDTO objects in a PageImpl object, returning the paginated result.
     */
    @Override
    public Page<InstructorDTO> findInstructorByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<Instructor> instructorsPage = instructorDao.findInstructorsByName(name, pageRequest);
        return new PageImpl<>(instructorsPage.getContent().stream().map(instructor -> instructorMapper.fromInstructorToDTO(instructor)).collect(Collectors.toList()),pageRequest,instructorsPage.getTotalElements());
    }

    @Override
    public InstructorDTO loadInstructorByEmail(String email) {
        return instructorMapper.fromInstructorToDTO(instructorDao.findInstructorByEmail(email)) ;
    }

    /**
     * The use of the UserService instead of directly using a constructor or the UserDao for creating the User object
     * is based on a separation of concerns principle and the layered architecture commonly used in Spring-based applications.
     * he main reasons to use UserService in this case are to encapsulate business logic and ensure maintainability.
     * The UserService handles user creation with necessary logic (validations, password encryption, etc.), while the UserDao focuses on data persistence.
     * Using the service layer also promotes separation of concerns and makes your code easier to manage and test in the long term.
     */
    @Override
    public InstructorDTO createInstructor(InstructorDTO instructorDTO) {
        // We first create the user
        User user = userService.createUser(instructorDTO.getUser().getEmail(),instructorDTO.getUser().getPassword());
        // Then we create the instructor
        Instructor instructor = instructorMapper.fromDTOToInstructor(instructorDTO);
        instructor.setUser(user);
        Instructor savedInstructor = instructorDao.save(instructor);
        userService.assignRoleToUser(user.getEmail(),"Instructor");
        return instructorMapper.fromInstructorToDTO(savedInstructor);
    }

    /**
     *
    *The Mapper does not handle nested objects or complex relationships. It only copies simple properties.
     * The InstructorDTO doesn’t contain enough information about the User and Courses relationships, so you need to manually set these properties in the Instructor entity.
     * By manually setting the User and Courses, you ensure that when you update the Instructor, these important relationships are preserved and not lost during the mapping process.
     */

    @Override
    public InstructorDTO updateInstructor(InstructorDTO instructorDTO) {
        // we need to fetch the instructor object from the database in order to get the user and courses of that instructor , because when we need to perform an update , we need to change the instructor informations
        // yet we don't want to lose any pf the courses or the user of that instructor
        Instructor loadedInstructor = loadInstructorById(instructorDTO.getInstructorId());
        Instructor dtoInstructor = instructorMapper.fromDTOToInstructor(instructorDTO);
        dtoInstructor.setUser(loadedInstructor.getUser());
        dtoInstructor.setCourses(loadedInstructor.getCourses());
        Instructor updatedInstructor = instructorDao.save(dtoInstructor);
        return instructorMapper.fromInstructorToDTO(updatedInstructor);
    }

    @Override
    public List<InstructorDTO> FetchInstructors() {
        //This is of type of list since we will be using it as a dropdown list to choose the instructor for the admin when managin a course
        return instructorDao.findAll().stream().map(instructor -> instructorMapper.fromInstructorToDTO(instructor)).collect(Collectors.toList());
    }

    @Override
    public void removeInstructor(Long instructorId) {
        //When deleting an instructor, we also want to delete the user and courses associated with that instructor.
        Instructor instructorToDelete = loadInstructorById(instructorId);
        //In order to delete a course we need to use the course service
        for (Course course: instructorToDelete.getCourses()){
            courseService.removeCourse(course.getCourseId());
        }
        instructorDao.deleteById(instructorId);
    }
}
