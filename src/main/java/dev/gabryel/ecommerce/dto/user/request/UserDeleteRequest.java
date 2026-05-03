package dev.gabryel.ecommerce.dto.user.request;

import jakarta.validation.constraints.Size;

public record UserDeleteRequest(@Size(min = 8, max = 75, message = "Password most have at least 8 characters") String password) {
}
