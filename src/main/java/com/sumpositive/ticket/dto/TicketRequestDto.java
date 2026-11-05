package com.sumpositive.ticket.dto;

import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record TicketRequestDto(

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title cannot exceed 200 characters")
        String title,

        String description,

        TicketStatus status,

        @NotNull(message = "Priority is required")
        TicketPriority priority,

        Long assigneeId,

        OffsetDateTime dueDate

) {
}