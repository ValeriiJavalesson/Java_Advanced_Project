package com.valerko.lgs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.FacultyReport;

public interface FacultyReportRepository extends JpaRepository<FacultyReport, Long>{
	Optional<FacultyReport> findByFaculty(Faculty faculty);
}
