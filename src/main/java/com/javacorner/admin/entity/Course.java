package com.javacorner.admin.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Basic
    @Column(name = "course_name", nullable = false, length = 45)
    private String courseName;

    @Basic
    @Column(name = "course_description", nullable = false, length = 64)
    private String courseDescription;

    @Basic
    @Column(name = "course_duration", nullable = false, length = 45)
    private String courseDuration;


    //Every course is taught by a single instructor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", referencedColumnName = "instructor_id", nullable = false)
    private Instructor instructor;

    // Every course can have multiple students
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrolled_in",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "student_id")})
    private Set<Student> students = new HashSet<>();

    public Course() {
    }

    public void assignStudentToCourse(Student student) {

        this.students.add(student);
        student.getCourses().add(this);
    }

    public void removeStudentFromCourse(Student student) {
        this.students.remove(student);
        student.getCourses().remove(this);
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;

        return courseId.equals(course.courseId) && Objects.equals(courseName, course.courseName) && Objects.equals(courseDescription, course.courseDescription) && Objects.equals(courseDuration, course.courseDuration);
    }

    @Override
    public int hashCode() {
        int result = courseId.hashCode();
        result = 31 * result + Objects.hashCode(courseName);
        result = 31 * result + Objects.hashCode(courseDescription);
        result = 31 * result + Objects.hashCode(courseDuration);
        return result;
    }


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(String courseDuration) {
        this.courseDuration = courseDuration;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", courseDuration='" + courseDuration + '\'' +
                '}';
    }
}
