package com.valerko.lgs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long>{
	

}
