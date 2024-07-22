package com.valerko.lgs.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.valerko.lgs.domain.FacultyReport;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FacultyReportServiceTest {
	@Autowired
	private UserServiceImpl userServiceImpl;
	private static User user1;
	private static User user2;
	@Autowired
	private FacultyReportServiceImpl facultyReportServiceImpl;
	private static FacultyReport facultyReport;
	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	private static ApplicantApplication application1;
	private static ApplicantApplication application2;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	private static Faculty faculty;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;
	private static Subject subject;

	@BeforeAll
	public static void init() {
		user1 = new User();
		user1.setEmail("facultyreporttest1@email.ua");
		user1.setFirstname("facultyreporttestFirstName1");
		user1.setLastname("facultyreporttestLastName1");
		user1.setPassword("8mpas625");
		user1.setRole(UserRole.ROLE_USER);
		user2 = new User();
		user2.setEmail("facultyreporttest2@email.ua");
		user2.setFirstname("facultyreporttestFirstName2");
		user2.setLastname("facultyreporttestLastName2");
		user2.setPassword("8mpas625");
		user2.setRole(UserRole.ROLE_USER);
		subject = new Subject("Measurements");
		faculty = new Faculty("Design");
		application1 = new ApplicantApplication();
		application2 = new ApplicantApplication();
		facultyReport = new FacultyReport();
	}

	@Test
	@Order(1)
	public void saveReport() {
		User savedUser1 = userServiceImpl.saveUser(user1);
		User savedUser2 = userServiceImpl.saveUser(user2);
		Subject savedSubject = subjectServiceImpl.create(subject);
		Faculty savedFaculty = facultyServiceImpl.create(faculty);
		savedFaculty.setSubjects(new HashSet<Subject>());
		savedFaculty.getSubjects().add(savedSubject);
		facultyServiceImpl.update(savedFaculty);

		application1.setUser(savedUser1);
		application1.setCertificatePoints(11.0);
		application1.setFaculty(savedFaculty);
		application1.getSubjects().entrySet().stream().forEach((e) -> e.setValue(11.0));

		application2.setUser(savedUser2);
		application2.setCertificatePoints(13.0);
		application2.setFaculty(savedFaculty);
		application2.getSubjects().entrySet().stream().forEach((e) -> e.setValue(11.0));

		ApplicantApplication applicantApplication1 = applicationServiceImpl.create(application1);
		ApplicantApplication applicantApplication2 = applicationServiceImpl.create(application2);
		facultyReport.setApplications(
				new ArrayList<ApplicantApplication>(List.of(applicantApplication1, applicantApplication2)));
		facultyReport.setFaculty(savedFaculty);
		facultyReport.setNumberOfStudents(2);
		FacultyReport savedReport = facultyReportServiceImpl.create(facultyReport);
		assertNotNull(savedReport);
		List<ApplicantApplication> enrolledApplication = savedReport.getEnrolledApplication();
		enrolledApplication.toString();
		assertEquals(savedUser2, enrolledApplication.getFirst().getUser());
	}

	@Test
	@Order(2)
	@Transactional
	public void deleteReport() {
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(faculty.getName());
		Faculty facultyFromDb = null;
		if (optionalFaculty.isPresent()) {
			facultyFromDb = optionalFaculty.get();
		}
		Optional<FacultyReport> optionalReport = facultyReportServiceImpl.findByFaculty(facultyFromDb);
		assertTrue(optionalReport.isPresent());
		if (optionalReport.isPresent()) {
			facultyReportServiceImpl.deleteByFaculty(facultyFromDb);
		}
		optionalReport = facultyReportServiceImpl.findByFaculty(facultyFromDb);
		assertTrue(optionalReport.isEmpty());
	}
}
