package com.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.security.models.domain.Role;
import com.demo.security.models.enums.RoleEnum;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRole(RoleEnum rol);
}
