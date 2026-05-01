package dev.gabryel.ecommerce.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessage> handleBadCredentialsException(BadCredentialsException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.valueOf(HttpStatus.CONFLICT.value()).getReasonPhrase(),
                ex.getMessage(),
                request.getPathInfo()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Database error";

        if (ex.getMostSpecificCause() instanceof SQLException sqlEx &&
        "23505".equals(sqlEx.getSQLState())) {
            message = "Entity already registered";
        }
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                message,
                request.getPathInfo()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

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
