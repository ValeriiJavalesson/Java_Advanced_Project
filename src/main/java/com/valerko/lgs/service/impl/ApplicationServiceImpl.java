package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.ApplicationRepository;
import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.User;
import com.valerko.lgs.service.ApplicationService;


@Service
public class ApplicationServiceImpl implements ApplicationService {

	private Logger logger = LoggerFactory.getLogger(ApplicationServiceImpl.class);

	@Autowired
	ApplicationRepository applicationRepository;

	@Override
	public ApplicantApplication create(ApplicantApplication applicantApplication) {
		logger.info("User: " + applicantApplication.getUser() + " created new application");
		return applicationRepository.save(applicantApplication);
	}

	@Override
	public ApplicantApplication update(ApplicantApplication applicantApplication) {
		logger.info("User: " + applicantApplication.getUser() + " updated application");
		return applicationRepository.save(applicantApplication);
	}

	@Override
	public Optional<ApplicantApplication> findById(Long id) {
		logger.info(" Get application by id: " + id);
		return applicationRepository.findById(id);
	}

	@Override
	public Optional<ApplicantApplication> findByUser(User user) {
		logger.info("Get application by user: " + user);
		return applicationRepository.findByUser(user);
	}

	@Override
	public List<ApplicantApplication> findApplicationsByFaculty(Faculty faculty) {
		logger.info("Get applications by faculty: " + faculty.getName());
		return applicationRepository.findApplicationsByFaculty(faculty);
	}

	@Override
	public void delete(ApplicantApplication applicantApplication) {
		logger.info("Delete user`s application {} : " + applicantApplication.getUser());
		applicationRepository.delete(applicantApplication);

	}

	@Override
	public void deleteByUser(User user) {
		logger.info("Delete user`s application {} : " + user);
		applicationRepository.deleteByUser(user);
	}

	@Override
	public List<ApplicantApplication> findAll() {
		logger.info("Get all applications");
		return applicationRepository.findAll();
	}


}
