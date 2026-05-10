package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.demo.service.UserService;
import com.example.demo.model.User;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable int id) {
        return service.getUserById(id);
    }

    @PostMapping("/")
    public String addUser(@RequestBody User user) {
        service.addUser(user);
        return "User added";
    }
}