package com.vitorsilva.helpdesk_api.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Response model when something works bad")
public class ErrorResponse {
    @Schema(description = "HTTP status code", example = ("400"))
    private int status;
    @Schema(description = "Error message", example = ("Ticket title is required"))
    private String message;
    @Schema(description = "Detailed list of validation or business errors",
            example = "[\"Title is required\", \"Requester email must be valid\"]")
    private List<String> errors;
    @Schema(description = "Timestamp", example = ("2026-05-19T21:54:00"))
    private LocalDateTime timestamp;
}
