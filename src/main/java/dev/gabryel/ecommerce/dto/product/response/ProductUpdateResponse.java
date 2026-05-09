package dev.gabryel.ecommerce.dto.product.response;

import dev.gabryel.ecommerce.model.enums.ProductStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateResponse(UUID id, String name, String description, BigDecimal price, String sellerName, Integer stock, ProductStatus status) {
}
