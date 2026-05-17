package com.vitorsilva.helpdesk_api.enums;

public enum TicketStatus {
    OPEN(1),
    IN_PROGRESS(2),
    RESOLVED(3),
    CLOSED(4);

    private final int order;

    TicketStatus(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}