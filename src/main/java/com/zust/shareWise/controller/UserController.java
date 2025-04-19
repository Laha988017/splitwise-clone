package com.zust.shareWise.controller;

import com.zust.shareWise.model.User;
import com.zust.shareWise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<User>> registerUsersInBulk(@RequestBody List<User> users) {
        List<User> createdUsers = new ArrayList<>();
        for (User user : users) {
            if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
                createdUsers.add(userRepository.save(user));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
    }
}
