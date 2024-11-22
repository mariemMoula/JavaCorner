package com.javacorner.admin.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id",nullable = false)
    private Long roleId ;

    @Basic
    @Column(name = "name",nullable = false,length = 45,unique = true)
    private String name ;

    // multiple users can have the same role
    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> users= new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;

        return roleId.equals(role.roleId) && Objects.equals(name, role.name);
    }

    @Override
    public int hashCode() {
        int result = roleId.hashCode();
        result = 31 * result + Objects.hashCode(name);
        return result;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
