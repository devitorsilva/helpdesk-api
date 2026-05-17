package com.vitorsilva.helpdesk_api.enums;

public enum TicketPriority {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    private final int order;

    TicketPriority(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }
}
