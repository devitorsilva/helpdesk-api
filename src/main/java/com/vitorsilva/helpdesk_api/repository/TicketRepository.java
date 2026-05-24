package com.vitorsilva.helpdesk_api.repository;

import com.vitorsilva.helpdesk_api.entity.Ticket;
import com.vitorsilva.helpdesk_api.enums.TicketPriority;
import com.vitorsilva.helpdesk_api.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findAllByStatusAndPriority(TicketStatus status, TicketPriority priority, Pageable pageable);

    Page<Ticket> findAllByStatus(TicketStatus status, Pageable pageable);

    Page<Ticket> findAllByPriority(TicketPriority priority, Pageable pageable);

    @Query("""
                select t from Ticket t
                where lower(t.title) like lower(concat('%', :term, '%'))
                   or lower(t.description) like lower(concat('%', :term, '%'))
            """)
    Page<Ticket> findAllByTerm(@Param("term") String term, Pageable pageable);

}
