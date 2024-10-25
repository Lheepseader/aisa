package com.example.aisa.controller;

import com.example.aisa.exception.NotEnoughIngredientException;
import com.example.aisa.exception.NotFoundException;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({NotFoundException.class, NotEnoughIngredientException.class})
    public ProblemDetail handleControllerException(RuntimeException e) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fe : ex.getFieldErrors()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(" ");
            }
            stringBuilder.append(fe.getField());
            stringBuilder.append(" - ");
            stringBuilder.append(fe.getDefaultMessage());
            stringBuilder.append(".");
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                stringBuilder.toString());
        problemDetail.setTitle("Validation Error");


        return ResponseEntity.badRequest().body(problemDetail);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(
            @NonNull HandlerMethodValidationException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        for (MessageSourceResolvable fe : ex.getAllErrors()) {
            if (!stringBuilder.isEmpty()) {
                stringBuilder.append(" ");
            }
            String fieldName = "parameter";
            if (fe instanceof FieldError fieldError) {
                fieldName = fieldError.getField();
            }
            stringBuilder.append(fieldName);
            stringBuilder.append(" - ");
            stringBuilder.append(fe.getDefaultMessage());
            stringBuilder.append(".");
        }
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                stringBuilder.toString());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
