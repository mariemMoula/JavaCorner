package com.javacorner.admin.web;

import com.javacorner.admin.service.UserService;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class UserRestController {

    //The only api we will expose in this controller is the one that enbales us to check if an  email exists or not the database

    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public boolean checkIfEmailExists(@RequestParam(name="email", defaultValue = "")String email){
        return userService.loadUserByEmail(email) != null;
    }
}
