package dev.gabryel.ecommerce.dto.product.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRegisterRequest(@NotBlank(message = "Please enter with product name") String name,
                                     @NotBlank(message = "Please enter with product description") String description,
                                     @Digits(integer = 8, fraction = 2, message = "Please enter a valid price value") BigDecimal price,
                                     @Min(value = 0, message = "Please enter a positive stock value") Integer stock) {
}
