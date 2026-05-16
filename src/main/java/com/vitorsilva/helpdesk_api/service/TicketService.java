package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(CreateTicketRequest request) {
        Ticket newTicket = new Ticket();
        newTicket.setTitle(request.getTitle());
        newTicket.setDescription(request.getDescription());
        newTicket.setPriority(request.getPriority());
        newTicket.setRequesterName(request.getRequesterName());
        newTicket.setRequesterEmail(request.getRequesterEmail());
        newTicket.setStatus(TicketStatus.OPEN);
        newTicket.setCreatedAt(LocalDateTime.now());

        return ticketRepository.save(newTicket);
    }
}
