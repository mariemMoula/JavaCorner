package com.javacorner.admin.runner;

import com.javacorner.admin.service.RoleService;
import com.javacorner.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {
    /**
     * If you did not add the @Autowired annotation, you would need to manually instantiate the RoleService or use constructor injection.
     */
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
    }



    private void createRoles() {
        Arrays.asList("Admin","Instructor","Student").forEach(role -> {
            roleService.createRole(role);
        });
    }
    private void createAdmin() {
        userService.createUser("admin@gmail.com", "adminPassword");
        userService.assignRoleToUser("admin@gmail.com", "Admin");

    }
}
