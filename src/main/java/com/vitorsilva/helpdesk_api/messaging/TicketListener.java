package com.vitorsilva.helpdesk_api.messaging;

import com.vitorsilva.helpdesk_api.event.TicketEvent;
import com.vitorsilva.helpdesk_api.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TicketListener {
    private final EmailService emailService;

    public TicketListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.ticket-created}")
    public void handleTicketCreated(TicketEvent event) {
        emailService.sendTicketCreatedEmail(event);
    }

    @RabbitListener(queues = "${app.rabbitmq.queue.ticket-resolved}")
    public void handleTicketResolved(TicketEvent event) {
        emailService.sendTicketResolvedEmail(event);
    }
}