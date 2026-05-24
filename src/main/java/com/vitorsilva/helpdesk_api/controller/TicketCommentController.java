package com.vitorsilva.helpdesk_api.controller;

import com.vitorsilva.helpdesk_api.dto.TicketCommentRequest;
import com.vitorsilva.helpdesk_api.dto.TicketCommentResponse;
import com.vitorsilva.helpdesk_api.service.TicketCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tickets")
@CrossOrigin(origins = "http://localhost:5173")
@Tag(name = "Ticket Comments")
public class TicketCommentController {
    private final TicketCommentService ticketCommentService;

    public TicketCommentController(TicketCommentService ticketCommentService) {
        this.ticketCommentService = ticketCommentService;
    }

    @PostMapping("/{ticketId}/comments")
    @Operation(summary = "Create ticket comment")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Ticket comment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload")
    })
    public ResponseEntity<TicketCommentResponse> create(@PathVariable Long ticketId, @RequestBody @Valid TicketCommentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketCommentService.create(ticketId,request));
    }

    @GetMapping("/{ticketId}/comments")
    @Operation(summary = "List ticket comments" )
    @ApiResponses({@ApiResponse(responseCode = "200")})
    public List<TicketCommentResponse> findByTicketId(@PathVariable Long ticketId) {
        return ticketCommentService.findByTicketId(ticketId);
    }

    @PatchMapping("/{ticketId}/comments/{id}")
    @Operation(summary = "Update ticket comment")
    public TicketCommentResponse update(
            @PathVariable Long id,
            @RequestBody @Valid TicketCommentRequest request) {
        return ticketCommentService.update(id, request);
    }

    @DeleteMapping("/{ticketId}/comments/{id}")
    @Operation(summary = "Delete ticket comment")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Ticket comment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Ticket comment not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ticketCommentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
