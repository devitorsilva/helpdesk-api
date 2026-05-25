package com.vitorsilva.helpdesk_api.messaging;

import com.vitorsilva.helpdesk_api.event.TicketEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TicketEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.queue.ticket-created}")
    private String ticketCreatedQueue;

    @Value("${app.rabbitmq.queue.ticket-resolved}")
    private String ticketResolvedQueue;

    public TicketEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTicketCreated(TicketEvent event) {
        rabbitTemplate.convertAndSend(ticketCreatedQueue, event);
    }

    public void publishTicketResolved(TicketEvent event) {
        rabbitTemplate.convertAndSend(ticketResolvedQueue, event);
    }
}