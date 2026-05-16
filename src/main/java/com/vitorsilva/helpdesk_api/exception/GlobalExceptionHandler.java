package com.vitorsilva.helpdesk_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception){
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> "Field: " + fieldError.getField() + " - " + fieldError.getDefaultMessage())
                .toList();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPayloadException(HttpMessageNotReadableException exception){
        List<String> errors = List.of("Request body contains invalid or malformed fields");
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage("Validation failed");
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.badRequest().body(errorResponse);
    }

}
