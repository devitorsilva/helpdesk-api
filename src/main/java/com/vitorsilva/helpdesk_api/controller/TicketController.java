package com.vitorsilva.helpdesk_api.controller;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @Operation(summary = "Create ticket")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<Ticket> create(@RequestBody @Valid CreateTicketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket by id" )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ticket found"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")
    })
    public Ticket findById(@PathVariable Long id) {
        return ticketService.findById(id);
    }

    @GetMapping
    @Operation(summary = "List all tickets", description = "Returns paginated tickets with optional status and priority filters")
    public Page<Ticket> findAllByFilter(
            @RequestParam(required = false) TicketStatus status,
            @RequestParam(required = false) TicketPriority priority,
            Pageable pageable) {

        return ticketService.findAllByFilter(status, priority, pageable);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update ticket")
    public Ticket update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTicketRequest request) {
        return ticketService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete ticket")
    public void delete(@PathVariable Long id){
        ticketService.delete(id);
    }
}
