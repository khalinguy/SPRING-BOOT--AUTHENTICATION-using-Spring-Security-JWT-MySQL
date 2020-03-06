package com.source.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.source.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByName(String name);
	User findByName(String name);
}
