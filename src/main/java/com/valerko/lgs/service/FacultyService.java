package com.valerko.lgs.service;

import java.util.List;
import java.util.Optional;

import com.valerko.lgs.domain.Faculty;

public interface FacultyService {
	
	Optional<Faculty> findByName(String name);

	List<Faculty> findAllFaculty();

	Faculty create(Faculty faculty);

	Faculty update(Faculty faculty);

	Optional<Faculty> findById(Long id);

	void delete(Faculty faculty);
	
	void deleteByName(String name);

}
