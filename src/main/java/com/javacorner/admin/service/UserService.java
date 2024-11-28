package com.javacorner.admin.service;

import com.javacorner.admin.entity.User;

public interface UserService {
    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);
    //We did not create a methode to remove user because it won't be needed since the we set the cascade type on the relationship between the user & the instructor and the student & user to cascade type remove
}
/**
 * Why use String email, String roleName instead of User user, Role role?
 * 1. Abstraction and Business Logic Separation
 * The service layer is responsible for defining business logic, not necessarily for directly dealing with entity objects.
 * Using String email and String roleName decouples the service logic from the entity objects (User and Role)
 * and instead focuses on the business intent (assigning a role to a user by their email and the role's name).
 *
 * 2.Avoiding Entity Leaks
 * Passing User and Role entities around (e.g., from controller to service) can cause problems:
 * The entities might be detached if the persistence context is closed.
 * You might accidentally update fields on the entities without persisting them, leading to subtle bugs.
 * Instead, the service can handle the responsibility of fetching the User and Role from the database.
 */