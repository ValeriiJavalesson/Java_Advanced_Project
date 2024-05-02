package com.valerko.lgs.service;

import java.util.List;
import java.util.Optional;

import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;

public interface FacultyReportService {
	
	Optional<FacultyReport> findByFaculty(Faculty faculty);

	List<FacultyReport> findAll();

	FacultyReport create(FacultyReport facultyReport);

	FacultyReport update(FacultyReport facultyReport);

	Optional<FacultyReport> findById(Long id);

	void delete(FacultyReport facultyReport);
	
	void deleteByFaculty(Faculty faculty);

}
