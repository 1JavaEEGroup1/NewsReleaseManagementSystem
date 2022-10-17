package com.example.newsreleasemanagementsystem.controller;

import com.example.newsreleasemanagementsystem.domian.User;
import com.example.newsreleasemanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jhlyh
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String test() {
        return "right";
    }

    @PostMapping("/register")
    public String setUser(@RequestBody User user) {
        userRepository.save(user);
        System.out.println(user);
        return "OK";
    }
}
