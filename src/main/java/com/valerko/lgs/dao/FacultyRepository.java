package com.valerko.lgs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.Faculty;


public interface FacultyRepository extends JpaRepository<Faculty, Long>{
	
	Optional<Faculty> findByName(String name);
	
	void deleteByName(String name);
}
