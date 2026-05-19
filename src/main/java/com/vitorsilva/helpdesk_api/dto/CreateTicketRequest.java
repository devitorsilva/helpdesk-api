package com.vitorsilva.helpdesk_api.dto;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload to create a ticket")
public class CreateTicketRequest {

    @Schema(description = "Ticket title", example = "Need new password to access ERP")
    @NotBlank(message = "Title is required")
    @Size(min=10, max=50, message = "Title must be between 10 and 50 characters")
    private String title;

    @Schema(description = "Detailed description of the issue", example = "I tried to log in 5 times and now i'm blocked and need to get a new password")
    @NotBlank(message = "Description is required")
    @Size(min=10, max=500, message = "Description must be between 10 and 500 characters")
    private String description;

    @Schema(description = "Ticket priority", example = "HIGH")
    @NotNull(message = "Priority is required")
    private TicketPriority priority;

    @Schema(description = "Requester full name", example = "Vitor Silva")
    @NotBlank(message = "Requester name is required")
    private String requesterName;

    @Schema(description = "Requester email", example = "vitor.pro@outlook.com")
    @NotBlank(message = "Requester email is required")
    @Email(message = "Requester email must be valid")
    private String requesterEmail;

    @Schema(description = "Assigned To", example = "IT Guy")
    private String assignedTo;
}
