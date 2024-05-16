package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.FacultyReportRepository;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;
import com.valerko.lgs.service.FacultyReportService;

@Service
public class FacultyReportServiceImpl implements FacultyReportService {
	private Logger logger = LoggerFactory.getLogger(FacultyReportServiceImpl.class);

	@Autowired
	FacultyReportRepository repository;

	@Override
	public FacultyReport create(FacultyReport facultyReport) {
		logger.info("Created report for faculty: " + facultyReport.getFaculty().getName());
		return repository.save(facultyReport);
	}

	@Override
	public FacultyReport update(FacultyReport facultyReport) {
		logger.info("Updated report for faculty: " + facultyReport.getFaculty().getName());
		return repository.save(facultyReport);
	}

	@Override
	public Optional<FacultyReport> findById(Long id) {
		logger.info("Get faculty by id: " + id);
		return repository.findById(id);
	}

	@Override
	public Optional<FacultyReport> findByFaculty(Faculty faculty) {
		logger.info("Get report by faculty: " + faculty.getName());
		return repository.findByFaculty(faculty);
	}

	@Override
	public void delete(FacultyReport facultyReport) {
		logger.info("Delete report for faculty: " + facultyReport.getFaculty().getName());
		repository.delete(facultyReport);
	}

	@Override
	public void deleteByFaculty(Faculty faculty) {
		logger.info("Delete report for faculty: " + faculty.getName());
		repository.deleteByFaculty(faculty);
	}

	@Override
	public List<FacultyReport> findAll() {
		logger.info("Get all faculties");
		return repository.findAll();
	}
}
