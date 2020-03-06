package com.source.controller;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.source.DTO.UserDTO;
import com.source.bean.AuthBody;
import com.source.repository.RoleRepository;
import com.source.repository.UserRepository;
import com.source.security.jwt.JwtProvider;
import com.source.services.UserService;


@RestController
@RequestMapping(value ="/auth")
@CrossOrigin("*")
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserService userService;
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/login/", method = RequestMethod.POST)
	public ResponseEntity authenticateUser (@RequestBody AuthBody auth) {
		
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
		System.out.println(auth.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        Map<Object, Object> model = new HashMap<>();
        model.put("username", auth.getUsername());
        model.put("token", jwt);
        
        return ResponseEntity.ok(model);
	}
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/register/", method = RequestMethod.POST)
	public ResponseEntity register(@RequestBody UserDTO user) {
		//User userExists = userService.convertToUser(userService.findByName(user.getName()));
		System.out.println(user.getName() + user.getPassword());
        if (userService.existsByName(user.getName())) {
            throw new BadCredentialsException("User with username: " + user.getName() + " already exists");
        }
        
        userRepository.save(userService.convertToUser(user));
        Map<Object, Object> model = new HashMap<>();
        model.put("User registered successfully", "userID: " + user.getId());
        return ok(model);
	}
}
