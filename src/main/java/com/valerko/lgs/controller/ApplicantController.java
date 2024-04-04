package com.valerko.lgs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.dto.ApplicantApplicationDto;
import com.valerko.lgs.dto.FacultyDto;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.FacultyServiceImpl;
import com.valerko.lgs.service.impl.SubjectServiceImpl;
import com.valerko.lgs.service.impl.UserServiceImpl;

@RestController
public class ApplicantController {

	@Autowired
	private UserServiceImpl userServiceImpl;
	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;

	@RequestMapping(value = "/api/application", method = RequestMethod.GET)
	public ApplicantApplicationDto getApplicipantApplication() {		
		String username = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		}
		Optional<User> optionalUser = userServiceImpl.findByEmail(username);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(user);

			if (optionalApplication.isPresent()) {
				return ApplicantApplicationDto.getApplicantApplicationDto(optionalApplication.get());
			}
		}
		return null;
	}

	@RequestMapping(value = "/api/allapplication", method = RequestMethod.GET)
	public List<ApplicantApplicationDto> getAllApplicipantApplication(@RequestParam("facultyName") String faculty) {
		List<ApplicantApplicationDto> allApplications = new ArrayList<ApplicantApplicationDto>();
		userServiceImpl.findAllUsers().forEach(u -> {
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findByUser(u);
			if (optionalApplication.isPresent()) {
				allApplications.add(ApplicantApplicationDto.getApplicantApplicationDto(optionalApplication.get()));
			}
		});
		List<ApplicantApplicationDto> list = allApplications.stream()
				.filter(a -> a.getFaculty() .equalsIgnoreCase(faculty)).toList();
		return list;
	}

	@RequestMapping(value = "/api/faculties", method = RequestMethod.GET)
	public List<Faculty> getFaculties() {
		List<Faculty> faculties = null;
		faculties = facultyServiceImpl.findAllFaculty();
		return faculties;
	}

	@RequestMapping(value = "/api/savefaculty", method = RequestMethod.POST)
	public String saveFaculties(@RequestBody FacultyDto subjectsAndName) {
		String name = subjectsAndName.getFacultyName();
		if (name.equals(""))
			return "error";
		Faculty faculty;
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(name);
		if (optionalFaculty.isEmpty()) {
			faculty = new Faculty();
			faculty.setName(name);
		} else {
			faculty = optionalFaculty.get();
			faculty.getSubjects().clear();
		}
		subjectsAndName.getSubjects().entrySet().stream().forEach(subject -> {
			Optional<Subject> optionalSubjects = subjectServiceImpl.findByName(subject.getValue());
			if (optionalSubjects.isPresent())
				faculty.getSubjects().add(optionalSubjects.get());
		});
		facultyServiceImpl.create(faculty);
		return "success";
	}

	@RequestMapping(value = "/api/deletefaculty", method = RequestMethod.POST)
	public String deleteFaculties(@RequestBody String name) {
		if (name.equals(""))
			return "error";
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(name);
		if (optionalFaculty.isPresent()) {
			facultyServiceImpl.delete(optionalFaculty.get());
			return "success";
		} else
			return "notfound";
	}

	@RequestMapping(value = "/api/allsubjects", method = RequestMethod.GET)
	public List<Subject> getSubjects() {
		List<Subject> subject = null;
		subject = subjectServiceImpl.findAll();
		return subject;
	}

	@RequestMapping(value = "/api/savesubject", method = RequestMethod.POST)
	public String createSubject(@RequestParam("subjectName") String name) {
		if (name.equals(""))
			return "error";
		Optional<Subject> optionalSubject = subjectServiceImpl.findByName(name);
		if (optionalSubject.isEmpty()) {
			Subject subject = new Subject();
			subject.setName(name);
			subjectServiceImpl.create(subject);
			return "success";
		} else
			return "update";
	}

	@RequestMapping(value = "/api/saveapplication", method = RequestMethod.POST)
	public String saveApplicipantApplication(@RequestBody ApplicantApplicationDto application) {

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

		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(application.getFaculty());
		if (optionalFaculty.isEmpty())
			return "faculty not found";
		applicantApplication.setFaculty(optionalFaculty.get());

		Map<Subject, Double> applicationSubjects = applicantApplication.getSubjects();
		application.getSubjects().entrySet().forEach(s -> {
			Optional<Subject> optionalSubjects = subjectServiceImpl.findByName(s.getKey());
			if (optionalSubjects.isPresent())
				applicationSubjects.put(optionalSubjects.get(), s.getValue());
		});
		applicantApplication.setCertificatePoints(application.getCertificatePoints());

		applicationServiceImpl.update(applicantApplication);
		return "success";
	}

	@RequestMapping(value = "/api/facultysubjects", method = RequestMethod.GET)
	public List<Subject> getFacultySubjects(@RequestParam("facultyName") String name) {
		List<Subject> subjectsList = new ArrayList<>();
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(name);
		if (optionalFaculty.isEmpty())
			return null;
		Faculty faculty = optionalFaculty.get();
		faculty.getSubjects().forEach(subjectsList::add);
		return subjectsList;
	}
}
