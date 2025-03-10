package mx.edu.beecker.security.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public record RequestPasswordReset(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format") String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}",
                message = "Password must contain at least one number, one uppercase letter, one lowercase letter, and one special character")
        String newPassword,

        @NotBlank(message = "Verification code is required")
        @Size(min = 6, max = 6, message = "Verification code must be exactly 6 characters")
        String verificationCode
) {}
