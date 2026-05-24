package com.vitorsilva.helpdesk_api.entity;

import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String title;
    String description;

    @Enumerated(EnumType.STRING)
    TicketStatus status;
    Integer statusOrder;

    @Enumerated(EnumType.STRING)
    TicketPriority priority;
    Integer priorityOrder;

    String requesterName;
    String requesterEmail;
    String assignedTo;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<TicketComment> comments;

}
