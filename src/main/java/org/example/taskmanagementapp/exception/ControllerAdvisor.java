package org.example.taskmanagementapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.taskmanagementapp.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ControllerAdvisor {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        log.warn(ex.getMessage());
        return createErrorResponse(HttpStatus.NOT_FOUND, ex, ex.getMessage());
    }

    @ExceptionHandler(IncorrectPasswordValidationException.class)
    private ResponseEntity<ErrorResponse> handleUserNotFoundException(IncorrectPasswordValidationException ex) {
        log.warn(ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
    }

    @ExceptionHandler(UserRegistrationException.class)
    private ResponseEntity<ErrorResponse> handleUserRegistrationException(UserRegistrationException ex) {
        log.warn(ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder message = new StringBuilder();

        result.getFieldErrors().forEach(error ->
            message.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ")
        );

        log.warn(ex.getMessage());
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex, message.toString().trim());
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenException(JwtTokenException ex) {
        log.warn("JWT Token Exception: {}", ex.getMessage());
        log.debug("ErrorResponse generated: {}", ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(ex.getClass().getSimpleName())
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(ex.getMessage())
                .build());

        log.warn(ex.getMessage());
        return createErrorResponse(HttpStatus.UNAUTHORIZED, ex, ex.getMessage());
    }

    private ResponseEntity<ErrorResponse> createErrorResponse(HttpStatus status,
                                                              Exception ex,
                                                              String message) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(ex.getClass().getSimpleName())
                .status(status.value())
                .message(message)
                .build(),
                status);
    }
}
