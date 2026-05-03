package dev.gabryel.ecommerce.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(@NotBlank(message = "Please enter with your email")
                               @Email(message = "Invalid email format")
                               String email,
                               @NotBlank(message = "Please enter with your password") String password) {
}
