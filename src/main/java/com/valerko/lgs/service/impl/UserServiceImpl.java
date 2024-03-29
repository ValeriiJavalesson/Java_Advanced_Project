package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.UserRepository;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public Optional<User> findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Optional<User> findUserById(Long id) {
		return repository.findById(id);
	}

	@Override
	public User saveUser(User user) {
		return repository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return repository.save(user);
	}

	@Override
	public void deleteUser(User user) {
		repository.delete(user);
	}

	@Override
	public void deleteUserById(Long id) {
		repository.deleteById(id);
	}

	@Override
	public List<User> findAllUsers() {
		return repository.findAll();
	}

}
