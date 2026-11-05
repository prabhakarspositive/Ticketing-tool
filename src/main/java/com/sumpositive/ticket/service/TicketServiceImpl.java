package com.sumpositive.ticket.service;

import com.sumpositive.ticket.dao.TicketRepo;
import com.sumpositive.ticket.dto.TicketRequestDto;
import com.sumpositive.ticket.dto.TicketResponseDto;
import com.sumpositive.ticket.dto.TicketStatusUpdateDto;
import com.sumpositive.ticket.dto.TicketUpdateDto;
import com.sumpositive.ticket.entity.Ticket;
import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;
import com.sumpositive.ticket.specification.TicketSpecification;
import com.sumpositive.user.dao.UserRepo;
import com.sumpositive.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService{

    private final TicketRepo ticketRepo;
    private final UserRepo userRepo;


    // =========================================
    // TICK-01: CREATE TICKET
    // =========================================

    @Override
    @Transactional
    public TicketResponseDto createTicket(
            TicketRequestDto request,
            Long currentUserId
    ) {

        // Find the authenticated user
        User creator = userRepo.findById(currentUserId)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Creator not found with ID: " + currentUserId
                        )
                );

        Ticket ticket = new Ticket();

        // Set ticket details
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());

        // Apply default status
        ticket.setStatus(
                request.status() != null
                        ? request.status()
                        : TicketStatus.TO_DO
        );

        // Apply default priority
        ticket.setPriority(
                request.priority() != null
                        ? request.priority()
                        : TicketPriority.MEDIUM
        );

        // Set creator
        ticket.setCreatedBy(creator);

        // Set optional assignee
        if (request.assigneeId() != null) {

            User assignee = userRepo.findById(request.assigneeId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Assignee not found with ID: "
                                            + request.assigneeId()
                            )
                    );

            ticket.setAssignee(assignee);
        }

        // Set optional due date
        ticket.setDueDate(request.dueDate());

        // Save ticket
        Ticket savedTicket = ticketRepo.save(ticket);

        return mapToResponse(savedTicket);
    }

    // =========================================
    // TICK-02: GET ALL TICKETS
    // DYNAMIC FILTERING
    // =========================================

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDto> getAllTickets(
            TicketStatus status,
            TicketPriority priority,
            Long assigneeId,
            Long createdById,
            String title,
            String description,
            OffsetDateTime dueBefore,
            Boolean unassigned
    ) {

        // Reject contradictory filters
        if (Boolean.TRUE.equals(unassigned)
                && assigneeId != null) {

            throw new IllegalArgumentException(
                    "Cannot filter by assigneeId and unassigned=true together"
            );
        }

        // Combine all dynamic specifications
        Specification<Ticket> specification =
                Specification.allOf(

                        TicketSpecification.hasStatus(status),

                        TicketSpecification.hasPriority(priority),

                        TicketSpecification.hasAssignee(assigneeId),

                        TicketSpecification.hasCreator(createdById),

                        TicketSpecification.hasTitle(title),

                        TicketSpecification.hasDescription(description),

                        TicketSpecification.dueBefore(dueBefore),

                        TicketSpecification.isUnassigned(unassigned)
                );

        // Execute dynamic query
        List<Ticket> tickets = ticketRepo.findAll(specification);

        // Convert entities into response DTOs
        return tickets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================
    // TICK-02: GET TICKET BY ID
    // =========================================

    @Override
    @Transactional(readOnly = true)
    public TicketResponseDto getTicketById(Long id) {

        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ticket not found with ID: " + id
                        )
                );

        return mapToResponse(ticket);
    }

    // =========================================
    // TICK-03: UPDATE TICKET
    // =========================================

    @Override
    @Transactional
    public TicketResponseDto updateTicket(
            Long id,
            TicketUpdateDto request,
            Long currentUserId
    ) {

        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ticket not found with ID: " + id
                        )
                );

        // Update ticket details
        ticket.setTitle(request.title());

        ticket.setDescription(request.description());

        ticket.setStatus(request.status());

        ticket.setPriority(request.priority());

        // Update or remove assignee
        if (request.assigneeId() != null) {

            User assignee = userRepo.findById(request.assigneeId())
                    .orElseThrow(() ->
                            new EntityNotFoundException(
                                    "Assignee not found with ID: "
                                            + request.assigneeId()
                            )
                    );

            ticket.setAssignee(assignee);

        } else {

            ticket.setAssignee(null);
        }

        // Update or remove due date
        ticket.setDueDate(request.dueDate());

        // Save updated ticket
        Ticket updatedTicket = ticketRepo.save(ticket);

        return mapToResponse(updatedTicket);
    }

    // =========================================
    // TICK-04: DELETE TICKET
    // =========================================

    @Override
    @Transactional
    public void deleteTicket(
            Long id,
            Long currentUserId
    ) {

        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ticket not found with ID: " + id
                        )
                );

        ticketRepo.delete(ticket);
    }


    // =========================================
    // TICK-06: UPDATE TICKET STATUS
    // =========================================
    @Transactional
    @Override
    public TicketResponseDto updateTicketStatus(
            Long id,
            TicketStatusUpdateDto request,
            Long currentUserId
    ) {

        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Ticket not found with ID: " + id
                        )
                );

        if (request.status() == null) {

            throw new IllegalArgumentException(
                    "Ticket status cannot be null"
            );
        }

        // Update status
        ticket.setStatus(request.status());

        Ticket updatedTicket = ticketRepo.save(ticket);

        return mapToResponse(updatedTicket);
    }
    // =========================================
    // ENTITY TO RESPONSE DTO MAPPING
    // =========================================

    private TicketResponseDto mapToResponse(Ticket ticket) {

        return new TicketResponseDto(

                ticket.getId(),

                ticket.getTitle(),

                ticket.getDescription(),

                ticket.getStatus(),

                ticket.getPriority(),

                ticket.getCreatedBy().getId(),

                ticket.getCreatedBy().getFullName(),

                ticket.getAssignee() != null
                        ? ticket.getAssignee().getId()
                        : null,

                ticket.getAssignee() != null
                        ? ticket.getAssignee().getFullName()
                        : null,

                ticket.getCreatedAt(),

                ticket.getUpdatedAt()
        );
    }
}
