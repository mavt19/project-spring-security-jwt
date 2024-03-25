package com.demo.security;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.security.models.domain.Role;
import com.demo.security.models.domain.User;
import com.demo.security.models.enums.RoleEnum;
import com.demo.security.repository.RoleRepository;
import com.demo.security.repository.UserRepository;

@SpringBootApplication
public class ProjectSpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectSpringSecurityJwtApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			var roleUser =roleRepository.save(new Role(null, RoleEnum.ROLE_USER));
			var roleAdmin = roleRepository.save(new Role(null, RoleEnum.ROLE_ADMIN));
			var roleManager = roleRepository.save(new Role(null, RoleEnum.ROLE_MANAGER));
			var roleSuperAdmin = roleRepository.save(new Role(null, RoleEnum.ROLE_SUPER_ADMIN));
			
			userRepository.save(new User(null, "jose Tirado", "jose", "jose@gmail.com", encoder.encode("12345"), List.of(roleUser)));
			userRepository.save(new User(null, "john Travolta", "jhon", "john@gmail.com", encoder.encode("12345"), List.of(roleUser, roleAdmin, roleManager, roleSuperAdmin) ));
			userRepository.save(new User(null, "Will Smith", "will", "will@gmail.com", encoder.encode("12345"), List.of(roleManager, roleUser) ));
			userRepository.save(new User(null, "Jim Carry", "jim", "jim@gmail.com", encoder.encode("12345"), List.of(roleUser, roleSuperAdmin) ));
//		
//			userService.addRoleToUser("jose", "ROLE_USER");
//			userService.addRoleToUser("jose", "ROLE_MANAGER");
//			userService.addRoleToUser("will", "ROLE_MANAGER");
//			userService.addRoleToUser("jim", "ROLE_ADMIN");
//			userService.addRoleToUser("jhon", "ROLE_USER");
//			userService.addRoleToUser("jhon", "ROLE_MANAGER");
//			userService.addRoleToUser("jhon", "ROLE_ADMIN");
//			userService.addRoleToUser("jhon", "ROLE_SUPER_ADMIN");
		};
	}
}
