package com.demo.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.security.models.dto.auth.RefreshTokenRequestDto;
import com.demo.security.models.dto.auth.RefreshTokenResponseDto;
import com.demo.security.models.dto.auth.SignInRequestDto;
import com.demo.security.models.dto.auth.SignInResponseDto;
import com.demo.security.models.dto.auth.SignUpRequestDto;
import com.demo.security.models.dto.auth.SignUpResponseDto;
import com.demo.security.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
