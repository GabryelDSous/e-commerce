package dev.gabryel.ecommerce.dto.purchase.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record PurchaseProductRequest(@NotNull(message = "Enter with a valid value")UUID id,
                                     @Positive(message = "Enter with a valid value") Integer quantity) {
}
