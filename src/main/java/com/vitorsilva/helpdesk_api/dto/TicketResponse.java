package com.vitorsilva.helpdesk_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Response payload for a ticket")
public class TicketResponse {

    @Schema(description = "Ticket id", example = "1")
    private Long id;

    @Schema(description = "Ticket title", example = "Login page returns 500 error")
    private String title;

    @Schema(description = "Detailed description of the issue", example = "Users cannot log in after the latest deployment")
    private String description;

    @Schema(description = "Ticket status", example = "OPEN")
    private String status;

    @Schema(description = "Ticket priority", example = "HIGH")
    private String priority;

    @Schema(description = "Requester full name", example = "Vitor Silva")
    private String requesterName;

    @Schema(description = "Requester email", example = "vitor@email.com")
    private String requesterEmail;

    @Schema(description = "Assigned technician name", example = "Ana Souza")
    private String assignedTo;

    @Schema(description = "Creation timestamp", example = "2026-05-24T13:39:12")
    private LocalDateTime createdAt;

    @Schema(description = "Last update timestamp", example = "2026-05-24T14:10:00")
    private LocalDateTime updatedAt;
}