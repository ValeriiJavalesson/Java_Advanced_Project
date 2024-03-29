package com.valerko.lgs.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.UserServiceImpl;

@RestController
public class ApplicantController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	
	@RequestMapping(value = "/api/application", method = RequestMethod.GET)
	public ApplicantApplication getApplicipantApplication(Authentication authentication) {
		String name = authentication.getName();
		Optional<User> optionalUser = userServiceImpl.findByEmail(name);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(user);
			if(optionalApplication.isPresent()) {
				ApplicantApplication userApplication = optionalApplication.get();
				System.out.println("return old appl");
				return userApplication;
			}else {
				ApplicantApplication newApplication = new ApplicantApplication();
				newApplication.setUser(user);
				System.out.println("return new appl");		
				return applicationServiceImpl.create(newApplication);
			}
		}		
		System.out.println(name);
        return null;
      
    }

}
