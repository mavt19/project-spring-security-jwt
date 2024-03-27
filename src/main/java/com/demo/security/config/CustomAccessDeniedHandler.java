package com.demo.security.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler  implements AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
//		response.setContentType("application/json");
//		response.setCharacterEncoding("UTF-8");
////		response.getWriter().write("Access Denied");
////		response.getWriter().write(accessDeniedException.getMessage());
//		response.setStatus(403);
//		response.sendError(403, accessDeniedException.getMessage());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(403);
		Map<String, Object>  body = new	HashMap<>();
		body.put("status", HttpStatus.FORBIDDEN.name());
		body.put("status_code", HttpStatus.FORBIDDEN.value());
		body.put("message", accessDeniedException.getMessage());
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), body);
	}

}
