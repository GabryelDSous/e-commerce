package dev.gabryel.ecommerce.dto.product.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductUpdateRequest(@NotBlank(message = "Please enter a product name") String name,
                                   @NotBlank(message = "Please enter a description") String description,
                                   @Digits(integer = 8, fraction = 2, message = "Please enter a valid value") BigDecimal price,
                                   @NotBlank(message = "Please enter a seller name") String sellerName,
                                   @PositiveOrZero(message = "Please enter a valid value") Integer stock) {
}