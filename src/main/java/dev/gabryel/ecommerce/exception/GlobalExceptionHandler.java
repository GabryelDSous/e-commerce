package dev.gabryel.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorMessage> handleUserException(UserException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                ex.getStatus(),
                HttpStatus.valueOf(ex.getStatus()).getReasonPhrase(),
                ex.getMessage(),
                request.getPathInfo()
        );
        return ResponseEntity.status(ex.getStatus()).body(errorMessage);
    }
}
