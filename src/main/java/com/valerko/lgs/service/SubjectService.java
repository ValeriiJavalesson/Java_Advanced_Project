package com.valerko.lgs.service;

import java.util.List;
import java.util.Optional;

import com.valerko.lgs.domain.Subject;

public interface SubjectService {
	
	List<Subject> findAll();
	
	Subject create(Subject subject);
	
	Optional<Subject> findById(Long id);
	
	Optional<Subject> findByName(String name);
	
	Subject update(Subject subject);
	
	void deleteById(Long id);
	
	void deleteByName(String name);

}
