package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Ticket create(CreateTicketRequest request) {
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

    public List<Ticket> findAll(){
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id){
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id:" + id));
    }

    public List<Ticket> findAllByFilter(TicketStatus status, TicketPriority priority) {
        if(status != null && priority != null){
            return ticketRepository.findAllByStatusAndPriority(status,priority);
        }

        if(status != null){
            return ticketRepository.findAllByStatus(status);
        }

        if(priority != null){
            return ticketRepository.findAllByPriority(priority);
        }

        return ticketRepository.findAll();
    }
}
