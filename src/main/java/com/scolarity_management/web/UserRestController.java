package com.scolarity_management.web;

import com.scolarity_management.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PreAuthorize("hasRole('admin')")
public class UserRestController {

    //The only api we will expose in this controller is the one that enables us to check if an  email exists or not the database

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public boolean checkIfEmailExists(@RequestParam(name="email", defaultValue = "")String email){
        return userService.loadUserByEmail(email) != null;
    }
}
