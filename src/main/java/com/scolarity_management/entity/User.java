package com.scolarity_management.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id",nullable = false)
    private Long userId;

    @Basic
    @Column(name = "email",nullable = false,length =45 ,unique = true)
    private String email;

    @Basic
    @Column(name = "password",nullable = false,length =64 )
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)//we require the user to be fetched with its user for security manners
    @JoinTable(name = "user_role",
    joinColumns = {@JoinColumn(name = "user_id")},
    inverseJoinColumns = {@JoinColumn(name = "role_id")})
    // each user can have multiple roles
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy ="user" )
    // each user can be an instructor or a student , they both have relationship with this entity
    private Student student;

    @OneToOne(mappedBy ="user" ) // the user is slave  and  the master is instructor
    private Instructor instructor ;

    public User() {
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
    public void assignRoleToUser(Role role){
        this.roles.add(role);
        role.getUsers().add(this);
    }
    public void removeRoleFromUser(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        return userId.equals(user.userId) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + Objects.hashCode(email);
        result = 31 * result + Objects.hashCode(password);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +

                '}';
    }
}
