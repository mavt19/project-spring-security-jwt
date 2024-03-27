package com.demo.security.models.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(@NotBlank(message = "El campo no puede ser nulo o vacio")String email, @NotBlank(message = "El campo no puede ser nulo o vacio")String password) {
}
