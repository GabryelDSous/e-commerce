package dev.gabryel.ecommerce.dto.product.response;

import dev.gabryel.ecommerce.model.enums.ProductStatus;

import java.math.BigDecimal;

public record ProductRegisterResponse(String name, String sellerName, BigDecimal price, Integer stock, ProductStatus status) {
}
