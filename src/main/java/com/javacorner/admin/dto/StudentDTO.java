package com.javacorner.admin.dto;



public class StudentDTO {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String level;
    private UerDTO user;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public UerDTO getUser() {
        return user;
    }

    public void setUser(UerDTO user) {
        this.user = user;
    }
}
