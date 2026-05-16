package com.vitorsilva.helpdesk_api.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private List<String> errors;
    private LocalDateTime timestamp;
}
