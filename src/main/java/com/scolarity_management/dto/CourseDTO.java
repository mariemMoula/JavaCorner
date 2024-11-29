package com.scolarity_management.dto;

public class CourseDTO {
    private Long courseId;
    private String courseName;
    private String courseDuraton;
    private String courseDescription;
    private InstructorDTO instructorDTO;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public InstructorDTO getInstructorDTO() {
        return instructorDTO;
    }

    public void setInstructorDTO(InstructorDTO instructorDTO) {
        this.instructorDTO = instructorDTO;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseDuration() {
        return courseDuraton;
    }

    public void setCourseDuration(String courseDuraton) {
        this.courseDuraton = courseDuraton;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
