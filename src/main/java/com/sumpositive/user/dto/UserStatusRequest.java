package com.sumpositive.user.dto;

import com.sumpositive.user.enums.UserStatus;
import jakarta.validation.constraints.NotNull;

public record UserStatusRequest(
        @NotNull(message = "User status is required")
        UserStatus status
) {
}
