package com.scolarity_management.dto;

public class InstructorDTO {
    private Long InstructorId ;
    private String firstName ;
    private String lastName ;
    private String summary ;
    private UserDTO user ;

    public Long getInstructorId() {
        return InstructorId;
    }

    public void setInstructorId(Long instructorId) {
        InstructorId = instructorId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
