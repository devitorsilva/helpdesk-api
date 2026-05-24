package com.vitorsilva.helpdesk_api.service;

import com.vitorsilva.helpdesk_api.dto.TicketCommentRequest;
import com.vitorsilva.helpdesk_api.dto.TicketCommentResponse;
import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.entity.TicketComment;
import com.vitorsilva.helpdesk_api.exception.ResourceNotFoundException;
import com.vitorsilva.helpdesk_api.repository.TicketCommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TicketCommentService {

    private final TicketCommentRepository ticketCommentRepository;
    private final TicketService ticketService;

    public TicketCommentService(TicketCommentRepository ticketCommentRepository, TicketService ticketService) {
        this.ticketCommentRepository = ticketCommentRepository;
        this.ticketService = ticketService;
    }

    public TicketCommentResponse create(Long ticketId, TicketCommentRequest request) {
        Ticket ticket = ticketService.findById(ticketId);
        TicketComment newComment = new TicketComment();

        newComment.setTicket(ticket);
        newComment.setComment(request.getComment());
        newComment.setAuthorName(request.getAuthorName());
        newComment.setCreatedAt(LocalDateTime.now());

        return toResponse(ticketCommentRepository.save(newComment));
    }

    public List<TicketCommentResponse> findByTicketId(Long id){
        return toResponseList(ticketCommentRepository.findByTicketId(id).stream().toList());
    }

    public TicketComment findById(Long id){
        return ticketCommentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket comment not found with id:" + id));
    }

    public TicketCommentResponse update(Long id, TicketCommentRequest request){
        TicketComment ticketComment = this.findById(id);
        if(request.getComment() != null){
            ticketComment.setComment(request.getComment());
        }
        if(request.getAuthorName() != null){
            ticketComment.setAuthorName(request.getAuthorName());
        }

        ticketComment.setUpdatedAt(LocalDateTime.now());

        return toResponse(ticketCommentRepository.save(ticketComment));
    }

    public void delete(Long id){
        TicketComment ticketComment = this.findById(id);
        ticketCommentRepository.delete(ticketComment);
    }

    private TicketCommentResponse toResponse(TicketComment ticketComment){
        TicketCommentResponse response = new TicketCommentResponse();
        response.setId(ticketComment.getId());
        response.setTicketId(ticketComment.getTicket().getId());
        response.setComment(ticketComment.getComment());
        response.setAuthorName(ticketComment.getAuthorName());
        response.setCreatedAt(ticketComment.getCreatedAt());

        return response;
    }

    private List<TicketCommentResponse> toResponseList(List<TicketComment> ticketCommentList){
        List<TicketCommentResponse> responseList = new ArrayList<>();
        ticketCommentList.forEach(ticketComment ->{
            responseList.add(toResponse(ticketComment));
        });

        return responseList;
    }
}
