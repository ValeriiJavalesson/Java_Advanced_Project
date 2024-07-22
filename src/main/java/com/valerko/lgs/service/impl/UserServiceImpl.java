package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.UserRepository;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;
import com.valerko.lgs.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public User saveUser(User user) {
		logger.info("Register new user : " + user);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(UserRole.ROLE_USER);
		return repository.save(user);
	}

	@Override
	public User updateUser(User user) {
		logger.info("Updated user : " + user);
		return repository.save(user);
	}

	@Override
	public Optional<User> findUserById(Long id) {
		logger.info("Get user by id: " + id);
		return repository.findById(id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		logger.info("Get user by email: " + email);
		return repository.findByEmail(email);
	}

	@Override
	public void deleteUser(User user) {
		logger.info("Deleted user : " + user);
		repository.delete(user);
	}

	@Override
	public void deleteUserById(Long id) {
		logger.info("Deleted user by id : " + id);
		repository.deleteById(id);
	}
	
	@Override
	public void deleteUserByEmail(String email) {
		logger.info("Deleted user by email : " + email);
		repository.deleteByEmail(email);
	}

	@Override
	public List<User> findAllUsers() {
		logger.info("Get all users");
		return repository.findAll();
	}

	

}
