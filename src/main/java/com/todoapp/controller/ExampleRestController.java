package com.todoapp.controller;

import com.todoapp.model.User;
import com.todoapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleRestController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allUser")
    public List<User> alluser(){
        return userRepository.findAll();
    }
}
