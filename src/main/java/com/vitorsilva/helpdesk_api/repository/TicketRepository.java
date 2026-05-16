package com.vitorsilva.helpdesk_api.repository;

import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByStatusAndPriority(TicketStatus status, TicketPriority priority);

    List<Ticket> findAllByStatus(TicketStatus status);

    List<Ticket> findAllByPriority(TicketPriority priority);
}
