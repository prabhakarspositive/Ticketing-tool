package com.sumpositive.ticket.dto;

import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;

import java.time.OffsetDateTime;

public record TicketResponseDto(
        Long id,
        String title,
        String description,
        TicketStatus status,
        TicketPriority priority,
        Long createdById,
        String createdByName,
        Long assigneeId,
        String assigneeName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {

}
