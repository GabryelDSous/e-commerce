package dev.gabryel.ecommerce.dto.user.response;

import java.util.List;
import java.util.UUID;

public record UserListResponse(UUID id, String name, String email, String role,
                               List<String> productNames) {
}
