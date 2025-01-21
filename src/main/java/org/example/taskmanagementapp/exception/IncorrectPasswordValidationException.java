package org.example.taskmanagementapp.exception;

public class IncorrectPasswordValidationException extends RuntimeException {

    public IncorrectPasswordValidationException(String message) {
        super(message);
    }
}
