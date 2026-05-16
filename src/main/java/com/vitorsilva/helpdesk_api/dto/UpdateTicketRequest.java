package com.vitorsilva.helpdesk_api.dto;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateTicketRequest {

    @Size(min = 10, max = 50, message = "Title must be between 10 and 50 characters")
    private String title;

    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
    private String description;

    private TicketPriority priority;
    private TicketStatus status;
    private String assignedTo;
}
