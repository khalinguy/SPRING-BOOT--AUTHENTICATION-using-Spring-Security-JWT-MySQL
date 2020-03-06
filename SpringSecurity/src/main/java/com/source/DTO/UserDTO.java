package com.source.DTO;

import java.util.Set;

import com.source.entity.Role;

import lombok.Data;

/**
 * Data Transfer Object
 */
@Data
public class UserDTO {
	private int id;
	private String name;
	private Set<Role> role;
	private String password;
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Role> getRole() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Username: " + this.getName() + "- Password: " + this.getPassword();
		}
}
