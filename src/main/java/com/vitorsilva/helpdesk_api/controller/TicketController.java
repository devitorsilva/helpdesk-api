package com.vitorsilva.helpdesk_api.controller;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket saveTicket(@RequestBody @Valid CreateTicketRequest request){
        return ticketService.createTicket(request);
    }
}
