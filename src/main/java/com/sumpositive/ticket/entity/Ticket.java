package com.sumpositive.ticket.entity;

import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;
import com.sumpositive.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TicketPriority priority = TicketPriority.MEDIUM;

    // User who created the ticket
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private User createdBy;

    // User assigned to work on the ticket
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "assignee_id"
    )
    private User assignee;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

}