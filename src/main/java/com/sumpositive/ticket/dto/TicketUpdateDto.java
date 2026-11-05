package com.sumpositive.ticket.dto;

import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record TicketUpdateDto(

        @Size(max = 200)
        String title,

        String description,

        TicketStatus status,

        TicketPriority priority,

        Long assigneeId,

        OffsetDateTime dueDate

) {
}