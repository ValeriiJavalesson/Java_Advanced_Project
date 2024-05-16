package com.valerko.lgs.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.dto.ApplicantApplicationDto;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.FacultyReportServiceImpl;
import com.valerko.lgs.service.impl.FacultyServiceImpl;
import com.valerko.lgs.service.impl.SubjectServiceImpl;
import com.valerko.lgs.service.impl.UserServiceImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class ApplicationController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;
	@Autowired
	private FacultyReportServiceImpl facultyReportServiceImpl;

	@RequestMapping(value = "/getapplication", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ApplicantApplicationDto getApplicantApplication() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(user);
			ApplicantApplicationDto applicantApplicationDto = new ApplicantApplicationDto();
			if (optionalApplication.isPresent()) {
				applicantApplicationDto = ApplicantApplicationDto
						.mapToApplicantApplicationDto(optionalApplication.get());
			}
			return applicantApplicationDto;
		}
		return null;
	}	

	@RequestMapping(value = "/applicationsByFaculty", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<ApplicantApplicationDto> getApplicationByFaculty(@RequestParam String facultyName) {
		List<ApplicantApplicationDto> list = new ArrayList<ApplicantApplicationDto>();
		Faculty faculty;
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(facultyName);
		if (optionalFaculty.isEmpty())
			return null;
		faculty = optionalFaculty.get();
		list = applicationServiceImpl.findApplicationsByFaculty(faculty).stream()
				.map(ApplicantApplicationDto::mapToApplicantApplicationDto).collect(Collectors.toList());
		return list;
	}

	@RequestMapping(value = "/saveapplication", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String saveApplicantApplication(@RequestBody ApplicantApplicationDto applicantApplicationDto)
			throws IOException {
		ApplicantApplication applicantApplication;
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isEmpty())
			return "user not found";
		User user = optionalUser.get();

		Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(user);
		if (optionalApplication.isEmpty()) {
			applicantApplication = new ApplicantApplication();
			applicantApplication.setUser(user);
		} else
			applicantApplication = optionalApplication.get();

		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(applicantApplicationDto.getFaculty());
		if (optionalFaculty.isEmpty())
			return "faculty not found";
		applicantApplication.setFaculty(optionalFaculty.get());

		Map<Subject, Double> applicationSubjects = applicantApplication.getSubjects();
		applicantApplicationDto.getSubjects().entrySet().forEach(s -> {
			Optional<Subject> optionalSubjects = subjectServiceImpl.findByName(s.getKey());
			if (optionalSubjects.isPresent())
				applicationSubjects.put(optionalSubjects.get(), s.getValue());
		});
		applicantApplication.setCertificatePoints(applicantApplicationDto.getCertificatePoints());
		applicationServiceImpl.update(applicantApplication);
		return "success";
	}

	@RequestMapping(value = "/numberOfApplication", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Map<String, Long> getNumberOfApplication() {
		Map<String, Long> listOfNumber = applicationServiceImpl.findAll().stream()
				.collect(Collectors.groupingBy(a -> a.getFaculty().getName(), Collectors.counting()));
		return listOfNumber;
	}


	@Transactional
	@RequestMapping(value = "/deleteApplication", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public String deleteApplication() {
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isEmpty())
			return "user not found";
		User user = optionalUser.get();
		List<FacultyReport> allReports = facultyReportServiceImpl.findAll();
		allReports.stream().forEach(report -> {
			report.getApplications().removeIf(appl -> appl.getUser().equals(user));
		});
		applicationServiceImpl.deleteByUser(user);
		return "success";
	}
}
