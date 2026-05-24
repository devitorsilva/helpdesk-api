package com.vitorsilva.helpdesk_api.repository;

import com.vitorsilva.helpdesk_api.entity.TicketComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketCommentRepository extends JpaRepository<TicketComment, Long> {

    List<TicketComment> findByTicketId(Long ticketId);
}
