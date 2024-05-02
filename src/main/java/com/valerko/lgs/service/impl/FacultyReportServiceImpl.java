package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.FacultyReportRepository;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;
import com.valerko.lgs.service.FacultyReportService;

@Service
public class FacultyReportServiceImpl implements FacultyReportService {

	@Autowired
	FacultyReportRepository repository;

	@Override
	public Optional<FacultyReport> findByFaculty(Faculty faculty) {
		return repository.findByFaculty(faculty);
	}

	@Override
	public List<FacultyReport> findAll() {
		return repository.findAll();
	}

	@Override
	public FacultyReport create(FacultyReport facultyReport) {
		return repository.save(facultyReport);
	}

	@Override
	public FacultyReport update(FacultyReport facultyReport) {
		return repository.save(facultyReport);
	}

	@Override
	public Optional<FacultyReport> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void delete(FacultyReport facultyReport) {
		repository.delete(facultyReport);
	}

	@Override
	public void deleteByFaculty(Faculty faculty) {
		repository.deleteByFaculty(faculty);
	}
}
