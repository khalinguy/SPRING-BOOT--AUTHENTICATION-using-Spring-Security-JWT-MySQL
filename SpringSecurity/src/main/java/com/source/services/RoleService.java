package com.source.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.source.entity.Role;
import com.source.repository.RoleRepository;

@Service
public class RoleService {
	@Autowired
	RoleRepository repo;
	
	
	public Role findByRole (String name) {
		Role roleByName = new Role();
		for(Role role : repo.findAll()) {
			if(role.getRoleName().equalsIgnoreCase(name)) {
				roleByName = role; 	
			}
		}
		return roleByName;
	}
	public boolean existsByRoleName(String name) {
		return repo.existsByRoleName(name);
	}
	
	
	public Role save (Role role) throws Exception {
		if (this.existsByRoleName(role.getRoleName())) {
			throw new Exception ("Role already exist!");
		}
		
		return repo.save(role);
	}
	

}
