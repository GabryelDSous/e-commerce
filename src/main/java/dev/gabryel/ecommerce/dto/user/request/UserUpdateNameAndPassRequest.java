package dev.gabryel.ecommerce.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserUpdateNameAndPassRequest(@NotBlank(message = "Please enter with your new name")
                                           String newName,
                                           @NotBlank(message = "Please enter with your current password")
                                           @Size(min = 8, max = 75, message = "Password most have at least 8 characters,")
                                           String currentPassword,
                                           @NotBlank(message = "Please enter with your new password")
                                           @Size(min = 8, max = 75, message = "Password most have at least 8 characters,")
                                           String newPassword
                                        ) {
}
