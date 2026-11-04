
package com.sumpositive.user.controller;

import com.sumpositive.user.dto.UserCreateRequest;
import com.sumpositive.user.dto.UserUpdateRequest;
import com.sumpositive.user.dto.UserStatusRequest;
import com.sumpositive.user.dto.UserResponse;
import com.sumpositive.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Register a new user
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserCreateRequest request) {

        UserResponse response = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update user details
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequest request) {

        return ResponseEntity.ok(
                userService.updateUser(id, request)
        );
    }

    // Update user status
    @PatchMapping("/{id}/status")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UserStatusRequest request) {

        return ResponseEntity.ok(
                userService.updateUserStatus(id, request)
        );
    }

    // Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
