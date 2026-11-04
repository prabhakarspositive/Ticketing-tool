package com.sumpositive.user.dto;

import com.sumpositive.user.enums.UserRole;
import com.sumpositive.user.enums.UserStatus;

import java.time.OffsetDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        UserRole role,
        UserStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
