package com.demo.security.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint  implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
////		response.getWriter().write("Access Denied");
//		response.getWriter().write(authException.getMessage());
//		response.setStatus(401);
//		response.sendError(401, authException.getMessage());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(401);
		Map<String, Object>  body = new	HashMap<>();
		body.put("status", HttpStatus.UNAUTHORIZED.name());
		body.put("status_code", HttpStatus.UNAUTHORIZED.value());
		body.put("message", authException.getMessage());
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), body);
		
	}

}
