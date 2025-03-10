package mx.edu.beecker.security.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RequestEmailDTO(
        @NotBlank @Email String email
) {}
