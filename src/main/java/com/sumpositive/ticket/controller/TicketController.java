package com.sumpositive.ticket.controller;

import com.sumpositive.ticket.dto.TicketRequestDto;
import com.sumpositive.ticket.dto.TicketResponseDto;
import com.sumpositive.ticket.dto.TicketStatusUpdateDto;
import com.sumpositive.ticket.dto.TicketUpdateDto;
import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;
import com.sumpositive.ticket.service.TicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

// =========================================
// TICK-01: CREATE TICKET
// =========================================

    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(
            @Valid @RequestBody TicketRequestDto request
    ) {

        // TODO: Replace with authenticated user ID
        Long currentUserId = 2L;

        TicketResponseDto response =
                ticketService.createTicket(request, currentUserId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

// =========================================
// TICK-02 & TICK-08: GET ALL TICKETS
// =========================================

    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(

            @RequestParam(required = false) TicketStatus status,

            @RequestParam(required = false) TicketPriority priority,

            @RequestParam(required = false) Long assigneeId,

            @RequestParam(required = false) Long createdById,

            @RequestParam(required = false) String title,

            @RequestParam(required = false) String description,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            OffsetDateTime dueBefore,

            @RequestParam(required = false) Boolean unassigned

    ) {

        List<TicketResponseDto> tickets =
                ticketService.getAllTickets(
                        status,
                        priority,
                        assigneeId,
                        createdById,
                        title,
                        description,
                        dueBefore,
                        unassigned
                );

        return ResponseEntity.ok(tickets);
    }

// =========================================
// TICK-02: GET TICKET BY ID
// =========================================

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(
            @PathVariable(value ="id" ) Long ticketId
    ) {

        TicketResponseDto response =
                ticketService.getTicketById(ticketId);

        return ResponseEntity.ok(response);
    }

// =========================================
// TICK-03: UPDATE TICKET
// =========================================

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDto> updateTicket(

            @PathVariable Long id,

            @Valid @RequestBody TicketUpdateDto request

    ) {

        // TODO: Replace with authenticated user ID
        Long currentUserId = 1L;

        TicketResponseDto response =
                ticketService.updateTicket(
                        id,
                        request,
                        currentUserId
                );

        return ResponseEntity.ok(response);
    }

// =========================================
// TICK-04: DELETE TICKET
// =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long id
    ) {

        // TODO: Replace with authenticated user ID
        Long currentUserId = 1L;

        ticketService.deleteTicket(id, currentUserId);

        return ResponseEntity.noContent().build();
    }

// =========================================
// TICK-06: UPDATE TICKET STATUS
// =========================================

    @PatchMapping("/{id}/status")
    public ResponseEntity<TicketResponseDto> updateTicketStatus(

            @PathVariable Long id,

            @Valid @RequestBody TicketStatusUpdateDto request

    ) {

        // TODO: Replace with authenticated user ID
        Long currentUserId = 1L;

        TicketResponseDto response =
                ticketService.updateTicketStatus(
                        id,
                        request,
                        currentUserId
                );

        return ResponseEntity.ok(response);
    }


}