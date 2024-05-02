package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.ApplicationRepository;
import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.service.ApplicationService;


@Service
public class ApplicationServiceImpl implements ApplicationService {

	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public Optional<ApplicantApplication> findByUser(User user) {
		return applicationRepository.findByUser(user);
	}

	@Override
	public List<ApplicantApplication> findApplicationsByFaculty(Faculty faculty) {
		return applicationRepository.findApplicationsByFaculty(faculty);
	}

	@Override
	public ApplicantApplication create(ApplicantApplication applicantApplication) {
		
		
		return applicationRepository.save(applicantApplication);
	}

	@Override
	public ApplicantApplication update(ApplicantApplication applicantApplication) {
		return applicationRepository.save(applicantApplication);
	}

	@Override
	public Optional<ApplicantApplication> findById(Long id) {
		return applicationRepository.findById(id);
	}

	@Override
	public void delete(ApplicantApplication applicantApplication) {
		applicationRepository.delete(applicantApplication);
		
	}

	@Override
	public void deleteByUser(User user) {
		applicationRepository.deleteByUser(user);
	}

	@Override
	public List<ApplicantApplication> findAll() {
		return applicationRepository.findAll();
	}

}
