package com.vitorsilva.helpdesk_api.event;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketEvent {

    private Long ticketId;
    private String title;
    private String description;
    private TicketPriority ticketPriority;
    private String requesterName;
    private String requesterEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}