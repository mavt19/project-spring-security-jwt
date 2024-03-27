package com.demo.security.exceptionhandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//import com.example.demo.util.customexceptions.NotFoundException;


@RestControllerAdvice
public class GlobalControllerExceptionHandler  {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		Map<String, String> errorsMap = new HashMap<>();
		e.getBindingResult().getFieldErrors().stream().forEach(x -> errorsMap.put(x.getField(), x.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errorsMap);
	}
	
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handlerNotFoundException(UsernameNotFoundException e){
		Map<String, String> errorsMap = new HashMap<>();
		errorsMap.put("mensaje", e.getMessage()) ;
		return ResponseEntity.status(404).body(errorsMap);
    }

}
