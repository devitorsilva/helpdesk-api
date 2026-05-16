package com.vitorsilva.helpdesk_api.controller;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket create(@RequestBody @Valid CreateTicketRequest request) {
        return ticketService.create(request);
    }

    @GetMapping("/{id}")
    public Ticket findById(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @GetMapping
    public Page<Ticket> findAllByFilter(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority,
            Pageable pageable) {

        return ticketService.findAllByFilter(status, priority, pageable);
    }

    @PatchMapping("/{id}")
    public Ticket update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTicketRequest request) {
        return ticketService.update(id, request);
    }
}
