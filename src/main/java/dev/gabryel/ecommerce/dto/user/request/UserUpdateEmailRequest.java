package dev.gabryel.ecommerce.dto.user.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateEmailRequest(@NotBlank(message = "Please enter with your email") String newEmail,
                                     @NotBlank(message = "Please enter with your password") String password) {
}
