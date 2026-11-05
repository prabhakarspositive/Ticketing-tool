
package com.sumpositive.ticket.specification;

import com.sumpositive.ticket.entity.Ticket;
import com.sumpositive.ticket.enums.TicketPriority;
import com.sumpositive.ticket.enums.TicketStatus;

import org.springframework.data.jpa.domain.Specification;

import java.time.OffsetDateTime;

public class TicketSpecification {

    public static Specification<Ticket> hasStatus(
            TicketStatus status) {

        return (root, query, cb) ->
                status == null ? null :
                        cb.equal(root.get("status"), status);
    }

    public static Specification<Ticket> hasPriority(
            TicketPriority priority) {

        return (root, query, cb) ->
                priority == null ? null :
                        cb.equal(root.get("priority"), priority);
    }

    public static Specification<Ticket> hasAssignee(
            Long assigneeId) {

        return (root, query, cb) ->
                assigneeId == null ? null :
                        cb.equal(
                                root.get("assignee").get("id"),
                                assigneeId
                        );
    }

    public static Specification<Ticket> hasCreator(
            Long createdById) {

        return (root, query, cb) ->
                createdById == null ? null :
                        cb.equal(
                                root.get("createdBy").get("id"),
                                createdById
                        );
    }

    public static Specification<Ticket> hasTitle(
            String title) {

        return (root, query, cb) ->
                title == null || title.isBlank() ? null :
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + title.trim().toLowerCase() + "%"
                        );
    }

    public static Specification<Ticket> dueBefore(
            OffsetDateTime dueBefore) {

        return (root, query, cb) ->
                dueBefore == null ? null :
                        cb.lessThanOrEqualTo(
                                root.<OffsetDateTime>get("dueDate"),
                                dueBefore
                        );
    }

    public static Specification<Ticket> isUnassigned(
            Boolean unassigned) {

        return (root, query, cb) -> {

            if (unassigned == null || !unassigned) {
                return null;
            }

            return cb.isNull(root.get("assignee"));
        };
    }

    // Search tickets by description (case-insensitive)
    public static Specification<Ticket> hasDescription(String description) {

        return (root, query, cb) ->
                description == null || description.isBlank()
                        ? null
                        : cb.like(
                        cb.lower(root.get("description")),
                        "%" + description.trim().toLowerCase() + "%"
                );
    }
}
