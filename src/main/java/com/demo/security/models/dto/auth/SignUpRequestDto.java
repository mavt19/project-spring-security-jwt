package com.demo.security.models.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequestDto(
		@NotBlank(message = "El campo no puede ser nulo o vacio")String name,
		@NotBlank(message = "El campo no puede ser nulo o vacio")String lastname,
		@NotBlank(message = "El campo no puede ser nulo o vacio")String username,
		@NotBlank(message = "El campo no puede ser nulo o vacio")String email,
		@NotBlank(message = "El campo no puede ser nulo o vacio")String password) {
}
