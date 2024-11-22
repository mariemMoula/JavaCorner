package com.javacorner.admin.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instructor_id",nullable = false)
    private Long instructorId ;
    @Basic
    @Column(name = "first_name",nullable = false,length = 45)
    private String firstName ;

    @Basic
    @Column(name = "last_name",nullable = false,length = 45)
    private String lastName ;
    @Basic
    @Column(name = "summary",nullable = false,length = 64)
    private String summary ;

    // every instructor has a list of courses but a single course belongs to a single instructor
    @OneToMany(mappedBy ="instructor",fetch = FetchType.LAZY)
    // The "instructor" in the mappedBy attribute is the name of the field in the Course class that maps the relationship.
    private Set<Course> courses = new HashSet<>();

    // each instructor is a user while a user might or might not be an instructor
    @OneToOne(cascade = CascadeType.REMOVE) // in case we want to remove an instructor , we remove that instructor to be automatically removed as well
    @JoinColumn(name = "user_id",referencedColumnName = "user_id",nullable = false)
    private User user;

    public Instructor() {
    }

    public Instructor(String firstName, String lastName, String summary, User user) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.summary = summary;
        this.user = user;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        if (!(o instanceof Instructor that)) return false;

        return instructorId.equals(that.instructorId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        int result = instructorId.hashCode();
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(summary);
        return result;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "instructorId=" + instructorId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }
}
