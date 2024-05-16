package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.SubjectRepository;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	private Logger logger = LoggerFactory.getLogger(SubjectServiceImpl.class);

	@Autowired
	SubjectRepository subjectRepository;

	@Override
	public Subject create(Subject subject) {
		logger.info("Created new subject : " + subject.getName());
		return subjectRepository.save(subject);
	}

	@Override
	public Subject update(Subject subject) {
		logger.info("Updated subject : " + subject.getName());
		return subjectRepository.save(subject);
	}

	@Override
	public Optional<Subject> findById(Long id) {
		logger.info("Get subject by id: " + id);
		return subjectRepository.findById(id);
	}

	@Override
	public Optional<Subject> findByName(String name) {
		logger.info("Get subject by name: " + name);
		return subjectRepository.findByName(name);
	}

	@Override
	public void deleteById(Long id) {
		logger.info("Deleted subject by id: " + id);
		subjectRepository.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		logger.info("Deleted subject by name: " + name);
		subjectRepository.deleteByName(name);
	}

	@Override
	public List<Subject> findAll() {
		logger.info("Get all subjects");
		return subjectRepository.findAll();
	}

}
