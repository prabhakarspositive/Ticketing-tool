
package com.sumpositive.ticket.service;

import com.sumpositive.ticket.dto.TicketRequestDto;
import com.sumpositive.ticket.dto.TicketUpdateDto;
import com.sumpositive.ticket.dto.TicketStatusUpdateDto;
import com.sumpositive.ticket.dto.TicketResponseDto;
import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;

import java.time.OffsetDateTime;
import java.util.List;

public interface TicketService {

    // Create ticket
    TicketResponseDto createTicket(
            TicketRequestDto request,
            Long currentUserId
    );

    // Get all tickets with dynamic filtering
    List<TicketResponseDto> getAllTickets(
            TicketStatus status,
            TicketPriority priority,
            Long assigneeId,
            Long createdById,
            String title,
            String description,
            OffsetDateTime dueBefore,
            Boolean unassigned
    );

    // Get ticket by ID
    TicketResponseDto getTicketById(Long id);

    // Update ticket
    TicketResponseDto updateTicket(
            Long id,
            TicketUpdateDto request,
            Long currentUserId
    );

    // Delete ticket
    void deleteTicket(
            Long id,
            Long currentUserId
    );

    // Update ticket status
    TicketResponseDto updateTicketStatus(
            Long id,
            TicketStatusUpdateDto request,
            Long currentUserId
    );
}
