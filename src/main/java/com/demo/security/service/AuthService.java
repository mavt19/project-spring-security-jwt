package com.demo.security.service;


import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.security.models.domain.Role;
import com.demo.security.models.domain.User;
import com.demo.security.models.dto.ReqRes;
import com.demo.security.models.dto.auth.RefreshTokenRequest;
import com.demo.security.models.dto.auth.SignInRequest;
import com.demo.security.models.dto.auth.SignUpRequest;
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

    public ReqRes signUp(SignUpRequest registrationRequest){
        ReqRes resp = new ReqRes();
        try {
        	var userFound = userRepository.findByEmailOrUsername(registrationRequest.email(), registrationRequest.username());
        	if(userFound.isPresent()) {
        		throw new Exception("User already exists with this email or username");
        	}
            User ourUsers = new User();
            ourUsers.setName(registrationRequest.name());
            ourUsers.setLastname(registrationRequest.lastname());
            ourUsers.setUsername(registrationRequest.username());
            ourUsers.setEmail(registrationRequest.email());
            ourUsers.setPassword(passwordEncoder.encode(registrationRequest.password()));
            Role role = roleRepository.findByRole(RoleEnum.ROLE_USER);
            ourUsers.getRoles().add(role);
            User ourUserResult = userRepository.save(ourUsers);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setOurUsers(ourUserResult);
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqRes signIn(SignInRequest signinRequest){
        ReqRes response = new ReqRes();

        try {
            var res = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.email(),signinRequest.password()));
           System.out.println(res);	
            var user = userRepository.findByEmail(signinRequest.email()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Signed In");
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    public ReqRes refreshToken(RefreshTokenRequest refreshTokenReqiest){
        ReqRes response = new ReqRes();
        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.refreshToken());
        User users = userRepository.findByEmail(ourEmail).orElseThrow();
        if (jwtUtils.isTokenValid(refreshTokenReqiest.refreshToken(), users.getEmail())) {
            var jwt = jwtUtils.generateToken(users);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRefreshToken(refreshTokenReqiest.refreshToken());
            response.setExpirationTime("24Hr");
            response.setMessage("Successfully Refreshed Token");
        }
        response.setStatusCode(500);
        return response;
    }
}
