package com.valerko.lgs.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.dto.FacultyDto;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.FacultyServiceImpl;
import com.valerko.lgs.service.impl.SubjectServiceImpl;

@RestController
@RequestMapping("/api")
public class FacultyController {

	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;

	@GetMapping("/faculty")
	public ModelAndView faculty() {
		return new ModelAndView("faculty");
	}

	@RequestMapping(value = "/faculties", method = RequestMethod.GET)
	public ModelAndView getFaculties() {
		ModelAndView model = new ModelAndView("faculties");
		List<Faculty> faculties = null;
		faculties = facultyServiceImpl.findAllFaculty();
		model.addObject("faculties", faculties);
		return model;
	}

	@RequestMapping(value = "/savefaculty", method = RequestMethod.POST)
	public String saveFaculties(@RequestBody FacultyDto facultyDto) {
		String name = facultyDto.getName();
		if (name.equals(""))
			return "error";
		Faculty faculty;
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(name);
		if (optionalFaculty.isPresent()) {
			faculty = optionalFaculty.get();
		} else {
			faculty = new Faculty();
			faculty.setName(name);
		}

		Set<Subject> newSubjectsSet = new HashSet<Subject>();
		facultyDto.getSubjects().entrySet().forEach(s -> {
			Optional<Subject> optionalSubjects = subjectServiceImpl.findByName(s.getValue());
			if (optionalSubjects.isPresent())
				newSubjectsSet.add(optionalSubjects.get());
		});

		faculty.setSubjects(newSubjectsSet);
		facultyServiceImpl.create(faculty);

		List<ApplicantApplication> allApplication = applicationServiceImpl.findApplicationsByFaculty(faculty);
		allApplication.stream().forEach(a -> {
			Map<Subject, Double> subjects = new HashMap<Subject, Double>();
			newSubjectsSet.stream().forEach(newSubject -> {
				if(a.getSubjects().containsKey(newSubject)) {
					subjects.put(newSubject, a.getSubjects().get(newSubject));
				}else {
					subjects.put(newSubject, 0.0);
				}
			});
			a.setSubjects(subjects);
			applicationServiceImpl.update(a);
		});
		return "success";
	}

	@RequestMapping(value = "/deletefaculty", method = RequestMethod.POST)
	public String deleteFaculties(@RequestParam String name) {
		if (name.equals(""))
			return "error";
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(name);
		if (optionalFaculty.isPresent()) {
			applicationServiceImpl.findApplicationsByFaculty(optionalFaculty.get())
					.forEach(a -> applicationServiceImpl.delete(a));
			facultyServiceImpl.delete(optionalFaculty.get());
			return "success";
		} else
			return "notfound";
	}

	@RequestMapping(value = "/allfaculty", method = RequestMethod.GET)
	public List<Faculty> getAllFacultySubjects() {
		return facultyServiceImpl.findAllFaculty();

	}
}
