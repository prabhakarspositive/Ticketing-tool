package com.sumpositive.ticket.dto;

import com.sumpositive.ticket.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record TicketStatusUpdateDto(
        @NotNull(message = "Status is required")
        TicketStatus status
) {
}
