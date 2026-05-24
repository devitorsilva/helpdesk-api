package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void shouldFindTicketById() {
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("Need new password to access ERP");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Need new password to access ERP", result.getTitle());
    }

    @Test
    void shouldThrowWhenTicketDoesNotExist(){
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.findById(99L);
        });
    }

    @Test
    void shouldDeleteTicketWhenTicketExists(){
        Ticket ticket = new Ticket();
        ticket.setId(1L);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        ticketService.delete(1L);

        verify(ticketRepository).delete(ticket);
    }

    @Test
    void shouldThrowWhenDeletingTicketThatDoesNotExist(){
        when(ticketRepository.findById(1l)).thenReturn((Optional.empty()));

        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.delete(1L);
        });

        verify(ticketRepository, never()).delete(any(Ticket.class));
    }

    @Test
    void shouldCreateTicket(){
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("Need new password to access ERP");
        request.setDescription("I tried to log in 5 times and now I'm blocked.");
        request.setPriority(TicketPriority.HIGH);
        request.setRequesterName("Vitor Silva");
        request.setRequesterEmail("vitor.pro@outlook.com");
        request.setAssignedTo("Ana Souza");

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setTitle(request.getTitle());
        savedTicket.setDescription(request.getDescription());
        savedTicket.setPriority(request.getPriority());
        savedTicket.setPriorityOrder(request.getPriority().getOrder());
        savedTicket.setRequesterName(request.getRequesterName());
        savedTicket.setRequesterEmail(request.getRequesterEmail());
        savedTicket.setAssignedTo(request.getAssignedTo());

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        Ticket result = ticketService.create(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Need new password to access ERP", result.getTitle());
        assertEquals(TicketPriority.HIGH, result.getPriority());

        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void shouldUpdateTicket(){
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setTitle("Updating my title request");
        request.setPriority(TicketPriority.MEDIUM);
        request.setStatus(TicketStatus.IN_PROGRESS);
        request.setAssignedTo("Vitor Silva");

        Ticket existingTicket = new Ticket();
        existingTicket.setId(1L);
        existingTicket.setTitle("Old title");
        existingTicket.setPriority(TicketPriority.HIGH);

        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(1L);
        updatedTicket.setTitle(request.getTitle());
        updatedTicket.setPriority(request.getPriority());
        updatedTicket.setStatus(request.getStatus());
        updatedTicket.setStatusOrder(request.getStatus().getOrder());
        updatedTicket.setAssignedTo(request.getAssignedTo());

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(existingTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(updatedTicket);

        Ticket result = ticketService.update(1L, request);

        assertNotNull(result);
        assertEquals("Updating my title request", result.getTitle());
        assertEquals(TicketPriority.MEDIUM, result.getPriority());
        assertEquals(TicketStatus.IN_PROGRESS, result.getStatus());
        assertEquals("Vitor Silva", result.getAssignedTo());

        verify(ticketRepository).findById(1L);
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    void shouldThrowWhenUpdatingTicketThatDoesNotExist(){
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setTitle("Updating my title request");
        request.setPriority(TicketPriority.MEDIUM);
        request.setStatus(TicketStatus.IN_PROGRESS);
        request.setAssignedTo("Vitor Silva");

        when(ticketRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.update(1L,request);
        });

        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void shouldSetDefaultStatusWhenCreatingTicket(){
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("Need new password to access ERP");
        request.setDescription("I tried to log in 5 times and now I'm blocked.");
        request.setPriority(TicketPriority.HIGH);
        request.setRequesterName("Vitor Silva");
        request.setRequesterEmail("vitor.pro@outlook.com");

        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);

        when(ticketRepository.save(any(Ticket.class))).thenReturn(savedTicket);

        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);

        ticketService.create(request);

        verify(ticketRepository).save(ticketCaptor.capture());

        Ticket capturedTicket = ticketCaptor.getValue();

        assertEquals(TicketStatus.OPEN, capturedTicket.getStatus());
        assertEquals(TicketStatus.OPEN.getOrder(), capturedTicket.getStatusOrder());
    }
}
