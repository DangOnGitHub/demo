package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<InvalidParamsResponse> handleInvalidParams(MethodArgumentNotValidException exception) {
        var parameters = exception.getBindingResult().getAllErrors().stream().map(error -> {
            String name = ((FieldError) error).getField();
            String reason = error.getDefaultMessage();
            return new InvalidParamsResponse.Parameter(name, reason);
        }).collect(Collectors.toList());
        var response = new InvalidParamsResponse("Your request parameters didn't validate.", parameters);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}
