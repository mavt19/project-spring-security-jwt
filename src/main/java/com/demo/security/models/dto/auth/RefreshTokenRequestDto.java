package com.demo.security.models.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(@NotBlank(message = "El campo no puede ser nulo o vacio")String refreshToken) {
}
