package com.demo.security.models.dto.auth;

public record SignInResponseDto(String token, String refreshToken, String expirationTime) {
}
