package com.gradleproject.controller;

import com.gradleproject.dto.UserRequest;
import com.gradleproject.dto.UserResponse;
import com.gradleproject.model.User;
import com.gradleproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getLogin(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> ResponseEntity.ok(new UserResponse(
                        user.getId(),
                        user.getLogin(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt())))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) {
        User newUser = new User(user.getLogin(), user.getName(), user.getEmail(), user.getPassword());
        User created = userService.createUser(newUser);
        UserResponse response = new UserResponse(
                created.getId(),
                created.getLogin(),
                created.getName(),
                created.getEmail(),
                created.getCreatedAt()
        );
        return ResponseEntity.ok(response);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
