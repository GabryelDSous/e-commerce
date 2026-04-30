package dev.gabryel.ecommerce.config;

import lombok.Builder;

@Builder
public record JWTUserData(String userId, String email, String role) {
}
