package com.vitorsilva.helpdesk_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Response payload for a ticket comment")
public class TicketCommentResponse {

    @Schema(description = "Comment id", example = "1")
    private Long id;

    @Schema(description = "Related ticket id", example = "10")
    private Long ticketId;

    @Schema(description = "Ticket comment", example = "I need you to fix my computer until Friday")
    private String comment;

    @Schema(description = "Author name", example = "Vitor Silva")
    private String authorName;

    @Schema(description = "Comment creation timestamp", example = "2026-05-24T12:30:00")
    private LocalDateTime createdAt;
}