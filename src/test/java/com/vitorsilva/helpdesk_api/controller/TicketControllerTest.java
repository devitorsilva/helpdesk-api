package com.vitorsilva.helpdesk_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitorsilva.helpdesk_api.dto.CreateTicketRequest;
import com.vitorsilva.helpdesk_api.dto.UpdateTicketRequest;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.exception.ErrorResponse;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.service.TicketService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.ErrorResponseException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TicketService ticketService;

    @Test
    @DisplayName("Should return all tickets when no filters are provided")
    void shouldListAllTickets() throws Exception{
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        ticket1.setTitle("First ticket title");

        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        ticket2.setTitle("Second ticket title");

        List<Ticket> ticketList = List.of(ticket1,ticket2);
        Page<Ticket> ticketPage = new PageImpl<>(ticketList);

        when(ticketService.findAllByFilter(isNull(),isNull(),any(Pageable.class))).thenReturn(ticketPage);

        mockMvc.perform(get("/tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].title").value("First ticket title"))
                .andExpect(jsonPath("$.content[1].title").value("Second ticket title"));
    }

    @Test
    @DisplayName("Should return the ticket by id")
    void shouldGetTicketById() throws Exception{
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setTitle("First ticket title");

        when(ticketService.findById(1L)).thenReturn(ticket);

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("First ticket title"));
    }

    @Test
    @DisplayName("Should return not found because ticket not exists")
    void shouldReturnNotFoundWhenTicketDoesNotExistById() throws Exception{
        when(ticketService.findById(1L)).thenThrow(new ResourceNotFoundException("Ticket not found with id: 1"));

        mockMvc.perform(get("/tickets/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.errors[0]").value("Ticket not found with id: 1"));
    }

    @Test
    @DisplayName("Should create new ticket")
    void shouldCreateTicket() throws Exception{
        CreateTicketRequest request = new CreateTicketRequest();
        request.setTitle("Test ticket title");
        request.setDescription("Description about this ticket");
        request.setPriority(TicketPriority.HIGH);
        request.setRequesterName("Vitor Silva");
        request.setRequesterEmail("vitor.pro@outlook.com");

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setPriorityOrder(request.getPriority().getOrder());
        ticket.setRequesterName(request.getRequesterName());
        ticket.setRequesterEmail(request.getRequesterEmail());

        when(ticketService.create(request)).thenReturn(ticket);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test ticket title"));
    }

    @Test
    @DisplayName("Should return error when request to create new ticket is invalid")
    void shouldReturnExceptionWhenRequestIsInvalid() throws Exception{
        CreateTicketRequest request = new CreateTicketRequest();
//        request.setTitle("Test ticket title"); without title
        request.setDescription("Description about this ticket");
        request.setPriority(TicketPriority.HIGH);
        request.setRequesterName("Vitor Silva");
        request.setRequesterEmail("vitor.pro@outlook.com");

        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setPriorityOrder(request.getPriority().getOrder());
        ticket.setRequesterName(request.getRequesterName());
        ticket.setRequesterEmail(request.getRequesterEmail());

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));

        verify(ticketService, never()).create(any(CreateTicketRequest.class));
    }

    @Test
    @DisplayName("Should update ticket")
    void shouldUpdateTicket() throws Exception{
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setDescription("New description about this ticket");
        request.setAssignedTo("Vitor Silva");

        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(1L);
        updatedTicket.setTitle("Need to fix my computer");
        updatedTicket.setDescription("description about my ticket");
        updatedTicket.setPriority(TicketPriority.HIGH);
        updatedTicket.setRequesterName("Alan Turing");
        updatedTicket.setRequesterEmail("alan.turing@mail.com");

        when(ticketService.update(eq(1L), any(UpdateTicketRequest.class))).thenReturn(updatedTicket);

        mockMvc.perform(patch("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("New description about this ticket"))
                .andExpect(jsonPath("$.assignedTo").value("Vitor Silva"));

        verify(ticketService).update(eq(1L),any(UpdateTicketRequest.class));
    }

    @Test
    @DisplayName("Should delete ticket")
    void shouldDeleteTicket() throws Exception{
        mockMvc.perform(delete("/tickets/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(ticketService).delete(eq(1L));
    }
}
