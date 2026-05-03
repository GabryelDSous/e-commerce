package dev.gabryel.ecommerce.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateEmailRequest(@NotBlank(message = "Please enter with your email")
                                     @Email(message = "Invalid email format")
                                     String newEmail,
                                     @NotBlank(message = "Please enter with your password")
                                     @Size(min = 8, max = 75, message = "Password most have at least 8 characters")
                                     String password) {
}
