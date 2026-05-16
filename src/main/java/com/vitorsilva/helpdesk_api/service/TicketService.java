package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

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

    public Ticket findById(Long id){
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id:" + id));
    }

    public Page<Ticket> findAllByFilter(TicketStatus status, TicketPriority priority, Pageable pageable) {
        if(status != null && priority != null){
            return ticketRepository.findAllByStatusAndPriority(status,priority,pageable);
        }

        if(status != null){
            return ticketRepository.findAllByStatus(status,pageable);
        }

        if(priority != null){
            return ticketRepository.findAllByPriority(priority,pageable);
        }

        return ticketRepository.findAll(pageable);
    }

    public Ticket update(Long id, UpdateTicketRequest updateTicketRequest){
        Ticket ticket = this.findById(id);
        if(updateTicketRequest.getDescription() != null){
            ticket.setDescription(updateTicketRequest.getDescription());
        }
        if(updateTicketRequest.getTitle() != null){
            ticket.setTitle(updateTicketRequest.getTitle());
        }
        if(updateTicketRequest.getStatus() != null){
            ticket.setStatus(updateTicketRequest.getStatus());
        }
        if(updateTicketRequest.getPriority() != null){
            ticket.setPriority(updateTicketRequest.getPriority());
        }
        if(updateTicketRequest.getAssignedTo() != null){
            ticket.setAssignedTo(updateTicketRequest.getAssignedTo());
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }
}
