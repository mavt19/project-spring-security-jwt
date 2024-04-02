package com.demo.security.models.dto.auth;

public record RefreshTokenResponseDto(String token, String refreshToken, String expirationTime) {
}
