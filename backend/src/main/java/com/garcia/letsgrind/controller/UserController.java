package com.garcia.letsgrind.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garcia.letsgrind.model.UserEntity;
import com.garcia.letsgrind.repository.UserRepository;

@RestController
@RequestMapping("/api/helloWorld")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<UserEntity> getAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public UserEntity create(@RequestBody UserEntity userEntity) {
        return userRepository.save(userEntity);
    }
}
