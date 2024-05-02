package com.valerko.lgs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;
import com.valerko.lgs.dto.ApplicantApplicationDto;
import com.valerko.lgs.dto.FacultyReportDto;
import com.valerko.lgs.service.impl.ApplicationServiceImpl;
import com.valerko.lgs.service.impl.FacultyReportServiceImpl;
import com.valerko.lgs.service.impl.FacultyServiceImpl;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api")
public class FacultyReportController {

	@Autowired
	private ApplicationServiceImpl applicationServiceImpl;
	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	FacultyReportServiceImpl facultyReportServiceImpl;

	@RequestMapping("/reports")
	public ModelAndView welcome() {
		ModelAndView model = new ModelAndView("reports");
		List<FacultyReport> allReports = facultyReportServiceImpl.findAll();
		List<Faculty> allFaculties = facultyServiceImpl.findAllFaculty();
		List<Faculty> freeFaculties = new ArrayList<Faculty>();
		allFaculties.stream().forEach(faculty -> {
			if (allReports.stream().noneMatch(report -> faculty.equals(report.getFaculty()))) {
				freeFaculties.add(faculty);
			}
		});
		model.addObject("reports", allReports);
		Map<Long, String> faculties = freeFaculties.stream()
				.collect(Collectors.toMap(Faculty::getId, Faculty::getName));
		ObjectMapper mapper = new ObjectMapper();
		String facultiesAsString = null;
		try {
			facultiesAsString = mapper.writeValueAsString(faculties);
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}
		model.addObject("allfaculties", facultiesAsString);
		return model;
	}

	@RequestMapping(value = "/getreport", method = RequestMethod.GET)
	public FacultyReportDto getFacultyReport(@RequestParam String facultyName) {
		if (facultyName.equals(""))
			return null;
		Faculty faculty;
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(facultyName);
		if (optionalFaculty.isEmpty()) {
			return null;
		}
		faculty = optionalFaculty.get();
		Optional<FacultyReport> OptionalFacultyReport = facultyReportServiceImpl.findByFaculty(faculty);
		if (OptionalFacultyReport.isPresent())
			return FacultyReportDto.mapToFacultyReportDto(OptionalFacultyReport.get());
		return null;
	}

	@RequestMapping(value = "/savereport", method = RequestMethod.POST)
	public String saveFacultyReport(@RequestParam("faculty") String facultyName,
			@RequestParam("applicationIdList") List<String> applicationIdList, @RequestParam Integer numberOfStudents) {
		FacultyReport report;
		if (facultyName.equals(""))
			return "error";
		Faculty faculty;
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(facultyName);
		if (optionalFaculty.isEmpty()) {
			return "error";
		}
		faculty = optionalFaculty.get();
		Optional<FacultyReport> OptionalFacultyReport = facultyReportServiceImpl.findByFaculty(faculty);
		if (OptionalFacultyReport.isPresent())
			report = OptionalFacultyReport.get();
		else
			report = new FacultyReport();
		List<ApplicantApplication> applicationList = new ArrayList<ApplicantApplication>();
		applicationIdList.stream().forEach(id -> {
			Optional<ApplicantApplication> optionalApplication = applicationServiceImpl.findById(Long.parseLong(id));
			optionalApplication.ifPresent(application -> applicationList.add(application));
		});
		report.setFaculty(faculty);
		report.setApplications(applicationList);
		report.setNumberOfStudents(numberOfStudents);
		facultyReportServiceImpl.create(report);
		return "success";
	}

	@Transactional
	@RequestMapping(value = "/deletereport", method = RequestMethod.DELETE)
	public String deleteReport(@RequestParam Long id) {
		if (!id.getClass().equals(Long.class))
			return "error";
		Optional<FacultyReport> optionalReport = facultyReportServiceImpl.findById(id);
		if (optionalReport.isEmpty())
			return "notfound";
		else
			facultyReportServiceImpl.delete(optionalReport.get());
		return "success";
	}

	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public List<ApplicantApplicationDto> getFacultyResultReport(@RequestParam Long id) {
		Optional<FacultyReport> OptionalFacultyReport = facultyReportServiceImpl.findById(id);
		if (OptionalFacultyReport.isPresent()) {
			FacultyReport facultyReport = OptionalFacultyReport.get();
			List<ApplicantApplicationDto> enrolledApplicationDto = FacultyReportDto.getEnrolledApplication(facultyReport);
			return enrolledApplicationDto;
		}
		return null;
	}
}
