package com.scolarity_management.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id",  nullable = false)
    private Long studentId;

    @Basic
    @Column(name = "first_name", nullable = false,length = 45)
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false,length = 45)
    private String lastName;

    @Basic
    @Column(name = "level", nullable = false,length = 64)
    private String level;

    // the child entity is the students and the master entity will be the courses

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY)
    // The "students" in the mappedBy attribute is the name of the field in the Course class that maps the relationship.
    // every student can enroll in multiple courses
    private Set<Course> courses = new HashSet<>();

    // every student is a user
    @OneToOne(cascade = CascadeType.REMOVE)// so that when deleting a student , their user will be deleted
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",nullable = false)
    private User user;

    public Student() {
    }

    public Student(String level, String lastName, String firstName, User user) {
        this.level = level;
        this.lastName = lastName;
        this.firstName = firstName;
        this.user = user;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;

        return studentId.equals(student.studentId) && Objects.equals(firstName, student.firstName) && Objects.equals(lastName, student.lastName) && Objects.equals(level, student.level);
    }

    @Override
    public int hashCode() {
        int result = studentId.hashCode();
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(level);
        return result;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
