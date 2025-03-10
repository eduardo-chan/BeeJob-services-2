package mx.edu.beecker.security.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestCodeVerification(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 6) String code
) {}
