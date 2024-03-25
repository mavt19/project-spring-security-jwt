package com.demo.security.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {

	ROLE_USER("ROLE_USER"),
	ROLE_ADMIN("ROLE_ADMIN"),
	ROLE_MANAGER("ROLE_MANAGER"),
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN");
	private final String roleName;
}
