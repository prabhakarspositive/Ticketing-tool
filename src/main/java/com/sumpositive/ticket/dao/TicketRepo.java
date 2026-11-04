package com.sumpositive.ticket.dao;

import com.sumpositive.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,Long> {
    // Find tickets created by a particular user
    List<Ticket> findByCreatedById(Long userId);

    // Find tickets assigned to a particular user
    List<Ticket> findByAssigneeId(Long userId);
}
