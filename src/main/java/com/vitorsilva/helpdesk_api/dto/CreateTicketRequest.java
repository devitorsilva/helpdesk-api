package com.vitorsilva.helpdesk_api.dto;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTicketRequest {

    @NotBlank(message = "Title is required")
    @Size(min=10, max=50, message = "Title must be between 10 and 50 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min=10, max=500, message = "Description must be between 10 and 50 characters")
    private String description;

    @NotNull(message = "Priorty is required")
    private TicketPriority priority;

    @NotBlank(message = "Requester name is required")
    private String requesterName;

    @NotBlank(message = "Requester name is required")
    @Email(message = "Requester email must be valid")
    private String requesterEmail;
}
