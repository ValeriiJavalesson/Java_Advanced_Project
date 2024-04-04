package com.valerko.lgs.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.valerko.lgs.dao.SubjectRepository;
import com.valerko.lgs.domain.Subject;
import com.valerko.lgs.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
	
	@Autowired
	SubjectRepository subjectRepository;

	@Override
	public Subject create(Subject subject) {
		return subjectRepository.save(subject);
	}

	@Override
	public Optional<Subject> findById(Long id) {
		return subjectRepository.findById(id);
	}

	@Override
	public Optional<Subject> findByName(String name) {
		return subjectRepository.findByName(name);
	}

	@Override
	public Subject update(Subject subject) {
		return subjectRepository.save(subject);
	}

	@Override
	public void deleteById(Long id) {
		subjectRepository.deleteById(id);
	}

	@Override
	public void deleteByName(String name) {
		subjectRepository.deleteByName(name);
	}

	@Override
	public List<Subject> findAll() {
		return subjectRepository.findAll();
	}

}
