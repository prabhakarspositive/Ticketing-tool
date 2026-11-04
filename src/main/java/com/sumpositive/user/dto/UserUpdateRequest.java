package com.sumpositive.user.dto;

import com.sumpositive.user.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @NotBlank(message = "Full name is required")
        @Size(max = 100)
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter a valid email address")
        @Size(max = 150)
        String email,

        @NotNull(message = "User role is required")
        UserRole role
) {
}
