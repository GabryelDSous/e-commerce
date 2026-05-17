package dev.gabryel.ecommerce.dto.purchase.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PurchaseProductResponse(UUID id, String productName, String userName, LocalDateTime purchaseDate, Integer quantity) {
}
