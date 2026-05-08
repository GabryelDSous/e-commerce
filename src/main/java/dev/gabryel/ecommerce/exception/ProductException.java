package dev.gabryel.ecommerce.exception;

import lombok.Getter;

@Getter
public class ProductException extends RuntimeException {
    private final int status;

    public ProductException(String message, int status) {
        super(message);
        this.status = status;
    }

}
