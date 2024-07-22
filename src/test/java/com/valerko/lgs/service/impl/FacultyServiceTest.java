package com.valerko.lgs.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.valerko.lgs.domain.Faculty;
import com.valerko.lgs.domain.Subject;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestPropertySource("/application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FacultyServiceTest {

	@Autowired
	private FacultyServiceImpl facultyServiceImpl;
	@Autowired
	private SubjectServiceImpl subjectServiceImpl;
	private static Faculty faculty;
	private static Subject subject;

	@BeforeAll
	public static void init() {
		faculty = new Faculty("Agro");
		subject = new Subject("Physics");
	}

	@Test
	@Order(1)
	public void saveFaculty() {
		Subject savedSubject = subjectServiceImpl.create(subject);
		faculty.getSubjects().add(savedSubject);
		assertNotNull(facultyServiceImpl.create(faculty));
	}

	@Test
	@Order(2)
	public void updateFaculty() {
		Optional<Faculty> optionalFaculty = facultyServiceImpl.findByName(faculty.getName());
		Faculty facultyFromDb = null;
		if (optionalFaculty.isPresent()) {
			facultyFromDb = optionalFaculty.get();
			Subject newSubject = new Subject("History");
			Subject newSavedSubject = subjectServiceImpl.create(newSubject);
			facultyFromDb.setSubjects(new HashSet<Subject>());
			facultyFromDb.getSubjects().add(newSavedSubject);
			Faculty updatedFaculty = facultyServiceImpl.update(facultyFromDb);
			assertTrue(updatedFaculty.getSubjects().stream().anyMatch(s -> s.getName().equals("History")));
		}
	}

	@Test
	@Order(3)
	@Transactional
	public void deleteFaculty() {
		Faculty newfaculty = new Faculty("Art");
		Faculty newSavedfaculty = facultyServiceImpl.create(faculty);
		assertNotNull(newSavedfaculty);
		facultyServiceImpl.delete(newSavedfaculty);
		assertTrue(facultyServiceImpl.findByName(newfaculty.getName()).isEmpty());
	}
}
