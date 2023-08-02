package com.example.cliqueres.domain.exception;

public class InvalidUserCredentialsException extends RuntimeException {

    public InvalidUserCredentialsException() {
        super("Внесените ставки не се точни!");
    }
}

