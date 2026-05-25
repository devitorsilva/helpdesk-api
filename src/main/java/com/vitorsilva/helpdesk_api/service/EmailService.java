package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.event.TicketEvent;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendTicketCreatedEmail(TicketEvent event) {
        System.out.println("Sending email...");
        System.out.println("Subject: Ticket Created");
        System.out.println("To: " + event.getRequesterEmail());
        System.out.println("Hello " + event.getRequesterName() + ", your ticket was created successfully.");
        System.out.println("Ticket id: " + event.getTicketId());
        System.out.println("Title: " + event.getTitle());
    }

    public void sendTicketResolvedEmail(TicketEvent event) {
        System.out.println("Sending email...");
        System.out.println("Subject: Ticket Resolved");
        System.out.println("To: " + event.getRequesterEmail());
        System.out.println("Hello " + event.getRequesterName() + ", your ticket was created successfully.");
        System.out.println("Ticket id: " + event.getTicketId());
        System.out.println("Title: " + event.getTitle());
    }
}