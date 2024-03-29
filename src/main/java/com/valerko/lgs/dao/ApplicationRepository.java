package com.valerko.lgs.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.User;

public interface ApplicationRepository extends JpaRepository<ApplicantApplication, Long> {
	
	Optional<ApplicantApplication> findByUser(User user);

	List<ApplicantApplication> findApplicationsByFaculty(Faculty faculty);
	
	void deleteByUser(User user);
}
