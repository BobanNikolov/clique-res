package com.example.cliqueres.endpoint.exception.handler;

import com.example.cliqueres.endpoint.exception.ErrorDetails;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.xml.bind.ValidationException;
import org.apache.coyote.BadRequestException;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({ValidationException.class, BadRequestException.class, EntityNotFoundException.class})
  @Primary
  public ResponseEntity<ErrorDetails> badRequestException(Exception ex) {
    return ResponseEntity.badRequest().body(ErrorDetails.builder().status(BAD_REQUEST.value())
        .message(ex.getMessage()).build());
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @Primary
  public ResponseEntity<ErrorDetails> constraintViolationException(ConstraintViolationException ex) {
    return ResponseEntity.badRequest().body(ErrorDetails.builder().status(INTERNAL_SERVER_ERROR.value())
        .message(ex.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(", "))).build());
  }

  @ExceptionHandler({Exception.class})
  @Primary
  public ResponseEntity<ErrorDetails> allOthers(Exception ex) {
    return ResponseEntity.badRequest().body(ErrorDetails.builder().status(INTERNAL_SERVER_ERROR.value())
        .message(ex.getMessage()).build());
  }
}
