package com.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.source.entity.Role;
import java.lang.String;

public interface RoleRepository extends JpaRepository<Role, Integer> {
	Role findOneByRoleName(String rolename);
	
	boolean existsByRoleName(String roleName);

}
