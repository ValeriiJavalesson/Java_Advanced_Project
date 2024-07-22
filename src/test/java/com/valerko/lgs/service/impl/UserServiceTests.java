package com.valerko.lgs.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	private static User user;

	@BeforeAll
	public static void init() {
		user = new User();
		user.setEmail("test@email.ua");
		user.setFirstname("testFirstName");
		user.setLastname("testLastName");
		user.setPassword("8mpas625");
		user.setRole(UserRole.ROLE_USER);
	}

	@Test
	@Order(1)
	public void saveUser() {
		User userCreated = userServiceImpl.saveUser(user);
		assertNotNull(userCreated);
	}

	@Test
	@Order(2)
	public void updateUser() {
		Optional<User> optionalUser = userServiceImpl.findByEmail(user.getEmail());
		User userFromDb = null;
		if (optionalUser.isPresent()) {
			userFromDb = optionalUser.get();
			userFromDb.setFirstname("anotherFirstName");
			User updatedUser = userServiceImpl.updateUser(userFromDb);
			assertEquals("anotherFirstName", updatedUser.getFirstname());
		}
	}

	@Test
	@Order(3)
	@Transactional
	public void deleteUser() {
		userServiceImpl.deleteUserByEmail(user.getEmail());
		Optional<User> optionalUser = userServiceImpl.findByEmail(user.getEmail());
		assertTrue(optionalUser.isEmpty());
	}
}
