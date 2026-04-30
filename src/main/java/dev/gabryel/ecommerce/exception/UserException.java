package dev.gabryel.ecommerce.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {
    private final int status;

    public UserException(String message, int status) {
        super(message);
        this.status = status;
    }
}
