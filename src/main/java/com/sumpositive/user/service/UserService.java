
package com.sumpositive.user.service;

import com.sumpositive.user.dao.UserRepo;
import com.sumpositive.user.dto.UserCreateRequest;
import com.sumpositive.user.dto.UserResponse;
import com.sumpositive.user.dto.UserUpdateRequest;
import com.sumpositive.user.dto.UserStatusRequest;
import com.sumpositive.user.entity.User;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    // Register a new user
    public UserResponse createUser(UserCreateRequest request) {

        if (userRepo.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();

        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );
        user.setRole(request.role());

        User savedUser = userRepo.save(user);

        return mapToResponse(savedUser);
    }

    // Get all users
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {

        return userRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Get user by ID
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID: " + id)
                );

        return mapToResponse(user);
    }

    // Update user
    public UserResponse updateUser(Long id, UserUpdateRequest request) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID: " + id)
                );

        if (!user.getEmail().equals(request.email())
                && userRepo.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already registered");
        }

        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setRole(request.role());

        return mapToResponse(userRepo.save(user));
    }

    // Update user status
    public UserResponse updateUserStatus(
            Long id,
            UserStatusRequest request
    ) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID: " + id)
                );

        user.setStatus(request.status());

        return mapToResponse(userRepo.save(user));
    }

    // Delete user
    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found with ID: " + id)
                );

        if (!user.getCreatedTickets().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot delete user who has created tickets"
            );
        }

        if (!user.getAssignedTickets().isEmpty()) {
            throw new IllegalArgumentException(
                    "Cannot delete user who has assigned tickets"
            );
        }

        userRepo.delete(user);
    }

    // Convert Entity to Response DTO
    private UserResponse mapToResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
