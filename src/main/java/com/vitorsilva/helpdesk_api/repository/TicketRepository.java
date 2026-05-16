package com.vitorsilva.helpdesk_api.repository;

import com.vitorsilva.helpdesk_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
