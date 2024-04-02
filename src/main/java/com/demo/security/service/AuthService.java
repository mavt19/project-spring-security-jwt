package com.demo.security.service;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.security.models.domain.Role;
import com.demo.security.models.domain.User;
import com.demo.security.models.dto.auth.RefreshTokenRequestDto;
import com.demo.security.models.dto.auth.RefreshTokenResponseDto;
import com.demo.security.models.dto.auth.SignInRequestDto;
import com.demo.security.models.dto.auth.SignInResponseDto;
import com.demo.security.models.dto.auth.SignUpRequestDto;
import com.demo.security.models.dto.auth.SignUpResponseDto;
import com.demo.security.models.enums.RoleEnum;
import com.demo.security.repository.RoleRepository;
import com.demo.security.repository.UserRepository;
import com.demo.security.utils.JWTUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final JWTUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public SignUpResponseDto signUp(SignUpRequestDto registrationRequest) {
		SignUpResponseDto resp = null;
		userRepository.findByEmailOrUsername(registrationRequest.email(), registrationRequest.username())
				.ifPresent(u -> {
					throw new RuntimeException("User already exists with this email or username");
				});
		User userDb = new User();
		userDb.setName(registrationRequest.name());
		userDb.setLastname(registrationRequest.lastname());
		userDb.setUsername(registrationRequest.username());
		userDb.setEmail(registrationRequest.email());
		userDb.setPassword(passwordEncoder.encode(registrationRequest.password()));
		Role role = roleRepository.findByRole(RoleEnum.ROLE_USER);
		userDb.getRoles().add(role);
		User ourUserResult = userRepository.save(userDb);
		if (ourUserResult != null && ourUserResult.getId() > 0) {
			resp = new SignUpResponseDto(ourUserResult.getName(), ourUserResult.getLastname(),
					ourUserResult.getUsername(), ourUserResult.getEmail());
		}

		return resp;
	}

	public SignInResponseDto signIn(SignInRequestDto signinRequest) {
		SignInResponseDto response = null;

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.email(), signinRequest.password()));
		var user = userRepository.findByEmail(signinRequest.email())
				.orElseThrow(() -> new RuntimeException("User not found with email: " + signinRequest.email()));
		System.out.println("USER IS: " + user);
		var jwt = jwtUtils.generateToken(user);
		var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
		response = new SignInResponseDto(jwt, refreshToken, "24Hr");

		return response;
	}

	public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenReqiest) {
		RefreshTokenResponseDto response = null;
		String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.refreshToken());
		User users = userRepository.findByEmail(ourEmail).orElseThrow();
		if (jwtUtils.isTokenValid(refreshTokenReqiest.refreshToken(), users.getEmail())) {
			var jwt = jwtUtils.generateToken(users);
			var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), users);
			response = new RefreshTokenResponseDto(jwt, refreshToken, "24Hr");
		}
		return response;
	}
}
