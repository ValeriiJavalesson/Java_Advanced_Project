package com.valerko.lgs.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.valerko.lgs.domain.Subject;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubjectServiceTest {

	@Autowired
	private SubjectServiceImpl subjectServiceImpl;
	private static Subject subject;

	@BeforeAll
	public static void init() {
		subject = new Subject();
		subject.setName("Math");
	}

	@Test
	@Order(1)
	public void saveSubject() {
		assertNotNull(subjectServiceImpl.create(subject));
	}

	@Test
	@Order(2)
	public void updateSubject() {
		Optional<Subject> optionalSubject = subjectServiceImpl.findByName(subject.getName());
		Subject subjectFromDb = null;
		if (optionalSubject.isPresent()) {
			subjectFromDb = optionalSubject.get();
			subjectFromDb.setName("Biology");
			Subject updatedSubject = subjectServiceImpl.update(subjectFromDb);
			assertEquals("Biology", updatedSubject.getName());
		}
	}

	@Test
	@Order(3)
	@Transactional
	public void deleteSubject() {
		Subject newSubject = new Subject();
		newSubject.setName("Geography");
		Subject savedSubject = subjectServiceImpl.create(newSubject);
		assertNotNull(savedSubject);
		subjectServiceImpl.deleteByName(newSubject.getName());
		assertTrue(subjectServiceImpl.findByName(newSubject.getName()).isEmpty());
	}
}
