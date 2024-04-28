package com.ucd.CenterRDV.controllers;

import com.ucd.CenterRDV.models.ApplicationUser;
import com.ucd.CenterRDV.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<ApplicationUser> getUser(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ApplicationUser getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }
}
