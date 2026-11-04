package com.sumpositive.user.entity;

import com.sumpositive.ticket.entity.Ticket;
import com.sumpositive.user.enums.UserRole;
import com.sumpositive.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    // Tickets created by this user
    @OneToMany(
            mappedBy = "createdBy",
            fetch = FetchType.LAZY
    )
    private List<Ticket> createdTickets = new ArrayList<>();

    // Tickets assigned to this user
    @OneToMany(
            mappedBy = "assignee",
            fetch = FetchType.LAZY
    )
    private List<Ticket> assignedTickets = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        OffsetDateTime now = OffsetDateTime.now();

        this.createdAt = now;
        this.updatedAt = now;
    }

    // Automatically update timestamp before UPDATE
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

}
