package dev.gabryel.ecommerce.exception;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime timestamp,
                           int status,
                           String error,
                           String message,
                           String path) {
}
