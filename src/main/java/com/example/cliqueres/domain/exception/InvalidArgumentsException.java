package com.example.cliqueres.domain.exception;

public class InvalidArgumentsException extends RuntimeException {

    public InvalidArgumentsException() {
        super("Внесените вредности не се точни!");
    }
}
