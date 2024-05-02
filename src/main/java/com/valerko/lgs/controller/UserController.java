package com.valerko.lgs.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.valerko.lgs.domain.User;
import com.valerko.lgs.dto.UserDto;
import com.valerko.lgs.service.impl.UserServiceImpl;

@RestController
public class UserController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@RequestMapping("/")
	public ModelAndView welcome() {
		return new ModelAndView("welcome");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/403")
	public ModelAndView badurl() {
		return new ModelAndView("403");
	}
	
	@RequestMapping("/api/home")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@GetMapping("/api/cabinet")
	public ModelAndView cabinet() {
		ModelAndView model = new ModelAndView("cabinet");
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			model.addObject("current_user", UserDto.userToUserDto(user));
		}
		return model;
	}

	@GetMapping("/registration")
	public ModelAndView registration() {
		return new ModelAndView("registration");
	}

	@PostMapping("/registration")
	public String saveUser(@ModelAttribute("userForm") User userForm, MultipartFile file, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(userForm.getEmail());
		if (optionalUser.isPresent()) {
			return "registration?message=ispresent";
		}
		User user = userServiceImpl.saveUser(userForm);
		try {
			user.setEncodedImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (Exception e) {
			System.err.println("Failed to save photo");
			System.err.println(e.getMessage());
		}
		userServiceImpl.updateUser(user);
		return "login?registered=succesfully";
	}

	@GetMapping("/api/getcurrentuser")
	public String getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			return username;
		}
		return "";
	}

	@PostMapping("/api/saveuserphoto")
	public UserDto saveUserPhoto(@RequestParam MultipartFile image) throws IOException {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setEncodedImage(Base64.getEncoder().encodeToString(image.getBytes()));
			userServiceImpl.updateUser(user);
			return UserDto.userToUserDto(user);
		}
		return null;
	}

	@GetMapping("/isUserPresent")
	public Boolean isUserPresent(@RequestParam String email) {
		Optional<User> optionalUser = userServiceImpl.findByEmail(email);
		return optionalUser.isPresent();
	}

}
