package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.TicketResponse;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.event.TicketEvent;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.messaging.TicketEventPublisher;
import com.vitorsilva.helpdesk_api.repository.TicketRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketEventPublisher ticketEventPublisher;
    private static final String ACTION_CREATED = "CREATED";
    private static final String ACTION_RESOLVED = "RESOLVED";

    public TicketService(TicketRepository ticketRepository, TicketEventPublisher ticketEventPublisher) {
        this.ticketRepository = ticketRepository;
        this.ticketEventPublisher = ticketEventPublisher;
    }

    public TicketResponse create(CreateTicketRequest request) {
        Ticket newTicket = new Ticket();
        newTicket.setTitle(request.getTitle());
        newTicket.setDescription(request.getDescription());
        newTicket.setPriority(request.getPriority());
        newTicket.setPriorityOrder(request.getPriority().getOrder());
        newTicket.setRequesterName(request.getRequesterName());
        newTicket.setRequesterEmail(request.getRequesterEmail());
        newTicket.setStatus(TicketStatus.OPEN);
        newTicket.setStatusOrder(newTicket.getStatus().getOrder());
        newTicket.setAssignedTo(request.getAssignedTo());
        newTicket.setCreatedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(newTicket);
        TicketResponse response = toResponse(savedTicket);

        publishTicketEvents(savedTicket, ACTION_CREATED);

        return response;
    }

    public Ticket findById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id:" + id));
    }

    public TicketResponse findResponseById(Long id) {
        return toResponse(this.findById(id));
    }

    public Page<TicketResponse> findAllByFilter(TicketStatus status, TicketPriority priority, Pageable pageable) {
        Page<Ticket> ticketPage;

        if (status != null && priority != null) {
            ticketPage = ticketRepository.findAllByStatusAndPriority(status, priority, pageable);
        } else if (status != null) {
            ticketPage = ticketRepository.findAllByStatus(status, pageable);
        } else if (priority != null) {
            ticketPage = ticketRepository.findAllByPriority(priority, pageable);
        } else {
            ticketPage = ticketRepository.findAll(pageable);
        }

        return ticketPage.map(this::toResponse);
    }

    public Page<TicketResponse> findAllByTerm(String term, Pageable pageable) {
        Page<Ticket> ticketPage = ticketRepository.findAllByTerm(term, pageable);
        return ticketPage.map(this::toResponse);
    }

    public TicketResponse update(Long id, UpdateTicketRequest updateTicketRequest) {
        Ticket ticket = this.findById(id);
        if (updateTicketRequest.getDescription() != null) {
            ticket.setDescription(updateTicketRequest.getDescription());
        }
        if (updateTicketRequest.getTitle() != null) {
            ticket.setTitle(updateTicketRequest.getTitle());
        }
        if (updateTicketRequest.getStatus() != null) {
            ticket.setStatus(updateTicketRequest.getStatus());
            ticket.setStatusOrder(updateTicketRequest.getStatus().getOrder());
        }
        if (updateTicketRequest.getPriority() != null) {
            ticket.setPriority(updateTicketRequest.getPriority());
            ticket.setPriorityOrder(updateTicketRequest.getPriority().getOrder());
        }
        if (updateTicketRequest.getAssignedTo() != null) {
            ticket.setAssignedTo(updateTicketRequest.getAssignedTo());
        }

        ticket.setUpdatedAt(LocalDateTime.now());

        TicketResponse response =  toResponse(ticketRepository.save(ticket));

        if(ticket.getStatus() == TicketStatus.RESOLVED) {
            publishTicketEvents(ticket, ACTION_RESOLVED);
        }

        return response;
    }

    public void delete(Long id) {
        Ticket ticket = this.findById(id);
        ticketRepository.delete(ticket);
    }

    private TicketResponse toResponse(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.setId(ticket.getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus() != null ? ticket.getStatus().name() : null);
        response.setPriority(ticket.getPriority() != null ? ticket.getPriority().name() : null);
        response.setRequesterName(ticket.getRequesterName());
        response.setRequesterEmail(ticket.getRequesterEmail());
        response.setAssignedTo(ticket.getAssignedTo());
        response.setCreatedAt(ticket.getCreatedAt());
        response.setUpdatedAt(ticket.getUpdatedAt());

        return response;
    }

    private void publishTicketEvents(Ticket ticket, String action) {
        TicketEvent event = new TicketEvent();
        event.setTicketId(ticket.getId());
        event.setTitle(ticket.getTitle());
        event.setDescription(ticket.getDescription());
        event.setTicketPriority(ticket.getPriority());
        event.setRequesterName(ticket.getRequesterName());
        event.setRequesterEmail(ticket.getRequesterEmail());
        event.setCreatedAt(ticket.getCreatedAt());
        event.setUpdatedAt(ticket.getUpdatedAt());

        if(action.equals(ACTION_CREATED)){
            ticketEventPublisher.publishTicketCreated(event);

        }else if(action.equals(ACTION_RESOLVED)){
            ticketEventPublisher.publishTicketResolved(event);
        }
    }
}
