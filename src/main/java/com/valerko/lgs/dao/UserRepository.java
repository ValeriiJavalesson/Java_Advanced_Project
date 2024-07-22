package com.valerko.lgs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
	void deleteByEmail(String email);
}
