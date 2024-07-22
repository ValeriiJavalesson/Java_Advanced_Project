package com.valerko.lgs.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.FacultyServiceImpl;
import com.valerko.lgs.service.impl.SubjectServiceImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;

	@GetMapping("/subjects")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView subject() {
		ModelAndView model = new ModelAndView("subjects");
		List<Subject> subjects = subjectServiceImpl.findAll();
		model.addObject("subjects", subjects);
		return model;
	}

	@RequestMapping(value = "/allsubjects", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Subject> getSubjects() {
		List<Subject> subject = null;
		subject = subjectServiceImpl.findAll();
		return subject;
	}

	@RequestMapping(value = "/savesubject", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
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

	@Transactional
	@RequestMapping(value = "/deletesubject", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteSubject(@RequestParam String name) {
		if (name.equals(""))
			return "error";
		Optional<Subject> optionalSubject = subjectServiceImpl.findByName(name);
		if (optionalSubject.isPresent()) {
			Subject subject = optionalSubject.get();
			applicationServiceImpl.findAll().stream().forEach(a -> {
				a.getSubjects().remove(subject);
			});
			facultyServiceImpl.findAllFaculty().stream().forEach(f -> f.getSubjects().removeIf(s -> s.equals(subject)));
			subjectServiceImpl.deleteByName(name);
			return "success";
		} else
			return "subject not found";
	}
}
