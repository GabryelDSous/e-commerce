package dev.gabryel.ecommerce.dto.purchase.response;

import java.time.LocalDateTime;

public record PurchaseProductResponse(String productName, String userName, LocalDateTime purchaseDate, Integer quantity) {
}
