package com.source.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.source.DTO.UserDTO;
import com.source.entity.Role;
import com.source.entity.User;
import com.source.repository.RoleRepository;
import com.source.repository.UserRepository;


@Service
public class UserService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public List<UserDTO> findAll() {
		List<UserDTO> allUsers = new ArrayList<UserDTO>();
		
		for (User u: repo.findAll()) {
			allUsers.add(this.convertToDTO(u));
		}
		return allUsers;
		
	}
	
	public boolean existsByName (String name) {
		return repo.existsByName(name);
	}
	
	public Page<User> findAllBySearch (int page, int size, String sortBy){
		return repo.findAll(PageRequest.of(page,size,Sort.by(sortBy)));		
		
	}
	

	public UserDTO findById(int id) {
	
		Optional<User> user = repo.findById(id);
		
        if(user.isPresent()) {
            return this.convertToDTO(user.get());
        } else {
            return null;
        }
	}
	
	public UserDTO findByName(String name) {
		User userByName = new User();
		for(User user : repo.findAll()) {
			if(user.getName().equalsIgnoreCase(name)) {
				userByName = user; 
				
			}
		}
		return this.convertToDTO(userByName);
	
	}
	
	public User updateUser (UserDTO dto){
		
		User user = this.convertToUser(dto);
		Optional<User> users = repo.findById(user.getId());
		
		if (users.isPresent()) {
			User updateUser = users.get();
			updateUser.setName(user.getName());
			updateUser.setRole(user.getRole());
			
			updateUser = repo.save(updateUser);
			System.out.println("has id");
			return updateUser;
		} else {
			
			return null;
		}
		
	}
	
	public User createUser(UserDTO dto) {
		User user = this.convertToUser(dto);
		
		User newUser = repo.save(user);
		return newUser;
	}
	
	public void deleteUserById(int id) {
		Optional<User> users = repo.findById(id);
		
		if(users.isPresent()) {
			repo.deleteById(id);
		}
	}
	
	
	public User convertToUser(UserDTO dto) {
		User user = new User();
		user.setId(dto.getId());
		user.setName(dto.getName());
		Set<Role> roles = new HashSet<>();
		//Add each role into set of roles
		dto.getRole().stream().forEach(role -> roles.add(roleRepository.findOneByRoleName(role.getRoleName())));
		
		
		dto.getRole().stream().forEach(role -> {System.out.println(role.getId() + "---" + role.getRoleName());});
		user.setRole(roles);
		
		System.out.println("Password: " + dto.getPassword());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		return user;
		
	}
	
	public UserDTO convertToDTO(User user) {
		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setRole(user.getRole());
		dto.setPassword(user.getPassword());
		return dto;
	}
	
	
}