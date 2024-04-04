package com.valerko.lgs.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
	
	Optional<Subject> findByName(String name);

	void deleteByName(String name);
}
