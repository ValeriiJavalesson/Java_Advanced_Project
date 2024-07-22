package com.valerko.lgs.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationServiceTest {

	@Autowired
	private UserServiceImpl userServiceImpl;
	private static User user;
	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	private static ApplicantApplication application;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	private static Faculty faculty;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;
	private static Subject subject;

	@BeforeAll
	public static void init() {
		user = new User();
		user.setEmail("applicationtest@email.ua");
		user.setFirstname("applicationtestFirstName");
		user.setLastname("applicationtestLastName");
		user.setPassword("8mpas625");
		user.setRole(UserRole.ROLE_USER);
		subject = new Subject("The basics of economics");
		faculty = new Faculty("Economic");
		application = new ApplicantApplication();
	}

	@Test
	@Order(1)
	public void saveApplication() {
		User savedUser = userServiceImpl.saveUser(user);
		Subject savedSubject = subjectServiceImpl.create(subject);
		Faculty savedFaculty = facultyServiceImpl.create(faculty);
		savedFaculty.setSubjects(new HashSet<Subject>());
		savedFaculty.getSubjects().add(savedSubject);
		facultyServiceImpl.update(savedFaculty);
		application.setUser(savedUser);
		application.setCertificatePoints(11.0);
		application.setFaculty(savedFaculty);
		application.getSubjects().entrySet().stream().forEach((e) -> e.setValue(11.0));
		ApplicantApplication applicantApplication = applicationServiceImpl.create(application);
		assertNotNull(applicantApplication);
		assertEquals((double) 22.0, applicantApplication.getSumOfPoints());
	}

	@Test
	@Order(2)
	@Transactional
	public void updateApplication() {
		Optional<User> optionalUser = userServiceImpl.findByEmail(user.getEmail());
		if (optionalUser.isPresent()) {
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(optionalUser.get());
			if (optionalApplication.isPresent()) {
				ApplicantApplication applicationFromDb = optionalApplication.get();
				applicationFromDb.setCertificatePoints(15.0);
				ApplicantApplication updatedApplication = applicationServiceImpl.update(applicationFromDb);
				assertEquals((double) 26.0, updatedApplication.getSumOfPoints());
			}
		}
	}

	@Test
	@Order(3)
	@Transactional
	public void deleteApplication() {
		Optional<User> optionalUser = userServiceImpl.findByEmail(user.getEmail());
		if (optionalUser.isPresent()) {
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(optionalUser.get());
			assertNotNull(optionalApplication.get());
			applicationServiceImpl.deleteByUser(optionalUser.get());
			assertTrue(applicationServiceImpl.findByUser(optionalUser.get()).isEmpty());
		}
	}

}
