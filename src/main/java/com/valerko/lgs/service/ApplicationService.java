package com.valerko.lgs.service;

import java.util.List;
import java.util.Optional;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.User;

public interface ApplicationService {

	Optional<ApplicantApplication> findByUser(User user);

	List<ApplicantApplication> findApplicationsByFaculty(Faculty faculty);

	ApplicantApplication create(ApplicantApplication applicantApplication);

	ApplicantApplication update(ApplicantApplication applicantApplication);

	Optional<ApplicantApplication> findById(Long id);

	void delete(ApplicantApplication applicantApplication);
	
	void deleteByUser(User user);
}
