package dev.gabryel.ecommerce.dto.product.response;

import dev.gabryel.ecommerce.model.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductListByNameResponse(UUID id, String name, BigDecimal price,
                                        ProductStatus status, String sellerName) {
}
