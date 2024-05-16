package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.FacultyRepository;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.service.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {
	private Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

	@Autowired
	FacultyRepository facultyRepository;

	@Override
	public Faculty create(Faculty faculty) {
		logger.info("Created new faculty: " + faculty.getName());
		return facultyRepository.save(faculty);
	}

	@Override
	public Faculty update(Faculty faculty) {
		logger.info("Updated faculty: " + faculty.getName());
		return facultyRepository.save(faculty);
	}

	@Override
	public Optional<Faculty> findById(Long id) {
		logger.info("Get faculty by id: " + id);
		return facultyRepository.findById(id);
	}

	@Override
	public Optional<Faculty> findByName(String name) {
		logger.info("Get faculty by name: " + name);
		return facultyRepository.findByName(name);
	}

	@Override
	public void delete(Faculty faculty) {
		logger.info("Delete faculty: " + faculty);
		facultyRepository.delete(faculty);
	}

	@Override
	public void deleteByName(String name) {
		logger.info("Delete faculty by name: " + name);
		facultyRepository.deleteByName(name);
	}

	@Override
	public List<Faculty> findAllFaculty() {
		logger.info("Get all faculties");
		return facultyRepository.findAll();
	}
}
