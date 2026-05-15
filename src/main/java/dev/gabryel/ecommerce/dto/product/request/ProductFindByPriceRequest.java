package dev.gabryel.ecommerce.dto.product.request;

import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductFindByPriceRequest(@PositiveOrZero(message = "Enter a valid value") BigDecimal minPrice,
                                        @PositiveOrZero(message = "Enter aa valid value") BigDecimal maxPrice) {
}
