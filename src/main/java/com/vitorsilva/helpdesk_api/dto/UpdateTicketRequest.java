package com.vitorsilva.helpdesk_api.dto;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload to update ticket ")
public class UpdateTicketRequest {

    @Schema(description = "Ticket title", example = "Need new password to access ERP")
    @Size(min = 10, max = 50, message = "Title must be between 10 and 50 characters")
    private String title;

    @Schema(description = "Detailed description of the issue", example = "I tried to log in 5 times and now i'm blocked and need to get a new password")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Schema(description = "Ticket priority", example = "MEDIUM")
    private TicketPriority priority;
    @Schema(description = "Ticket status", example = "IN_PROGRESS")
    private TicketStatus status;
    @Schema(description = "Assigned technician name", example = "Silva Vitor")
    private String assignedTo;
}
