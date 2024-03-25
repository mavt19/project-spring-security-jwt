package com.demo.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.security.models.domain.User;
import com.demo.security.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService{

	private final UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found in the database"));
		log.info("User found in the database: {}", username);
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities = user.getRoles().stream()
		.map(x -> new SimpleGrantedAuthority(x.getRole().getRoleName()))
		.collect(Collectors.toList());
		System.out.println(authorities);
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}


}
