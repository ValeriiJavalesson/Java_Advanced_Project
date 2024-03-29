package com.valerko.lgs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;
import com.valerko.lgs.service.impl.UserServiceImpl;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private PasswordEncoder encoder;
	
	@RequestMapping("/")
	public ModelAndView welcome() {
		return new ModelAndView("welcome");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	@RequestMapping("/api/home")
	public ModelAndView home() {
		return new ModelAndView("home");
	}

	@GetMapping("/registration")
	public ModelAndView registration() {
		return new ModelAndView("registration");
	}

	@PostMapping("/registration")
	public ModelAndView saveUser(@RequestParam("firstname") String firstName, 
						   @RequestParam("lastname") String lastName,
						   @RequestParam("email") String email, 
						   @RequestParam("password") String password) {		
		User user = User.builder()
				.email(email)
				.firstname(firstName)
				.lastname(lastName)
				.password(encoder.encode(password))
				.role(UserRole.ROLE_USER)
				.build();
		userServiceImpl.saveUser(user);		
		return new ModelAndView("redirect:/login?registered=successfully");
	}
}
