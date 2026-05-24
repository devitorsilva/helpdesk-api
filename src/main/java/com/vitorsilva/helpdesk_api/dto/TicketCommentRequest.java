package com.vitorsilva.helpdesk_api.dto;

import com.vitorsilva.helpdesk_api.entity.Ticket;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request payload to create a ticket comment")
public class TicketCommentRequest {

    @Schema(description = "Ticket comment", example = "I need that you fix my computer until friday")
    @NotBlank(message = "Comment is required")
    @Size(min=10, max=500, message = "Comment must be between 10 and 500 characters")
    private String comment;

    @Schema(description = "Author name", example = "Vitor Silva")
    @NotBlank(message = "Author name is required")
    @Size(min=5, max=50, message = "Author name must be between 5 and 50 characters")
    private String authorName;
}
