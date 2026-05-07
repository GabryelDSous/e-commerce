package dev.gabryel.ecommerce.dto.user.response;

import java.util.List;

public record UserListResponse(String name, String email, String role,
                               List<String> productNames) {
}
