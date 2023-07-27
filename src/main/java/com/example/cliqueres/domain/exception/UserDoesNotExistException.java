package com.example.cliqueres.domain.exception;

public class UserDoesNotExistException extends RuntimeException {
  public UserDoesNotExistException(String message) {
    super(message);
  }
}
