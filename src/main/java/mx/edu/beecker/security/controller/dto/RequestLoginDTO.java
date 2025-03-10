package mx.edu.beecker.security.controller.dto;


import jakarta.validation.constraints.NotBlank;

public record RequestLoginDTO(
        String email,
        String password
) {}
