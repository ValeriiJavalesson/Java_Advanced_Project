package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.FacultyRepository;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.service.FacultyService;

@Service
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	FacultyRepository facultyRepository;

	@Override
	public Optional<Faculty> findByName(String name) {
		return facultyRepository.findByName(name);
	}

	@Override
	public List<Faculty> findAllFaculty() {
		return facultyRepository.findAll();
	}

	@Override
	public Faculty create(Faculty faculty) {
		return facultyRepository.save(faculty);
	}

	@Override
	public Faculty update(Faculty faculty) {
		return facultyRepository.save(faculty);
	}

	@Override
	public Optional<Faculty> findById(Long id) {
		return facultyRepository.findById(id);
	}

	@Override
	public void delete(Faculty faculty) {
		facultyRepository.delete(faculty);
	}

	@Override
	public void deleteByName(String name) {
		facultyRepository.deleteByName(name);
	}

}
