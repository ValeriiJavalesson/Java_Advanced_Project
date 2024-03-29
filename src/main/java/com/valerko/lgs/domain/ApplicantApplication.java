package com.valerko.lgs.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table
@Data
public class ApplicantApplication implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	@Column
	private Double certificatePoints;
	@Column(name="faculty_id")
	private Faculty faculty;
	@Column
	@ElementCollection
	private Map<Subject, Double> subjects;


	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
		Map<Subject, Double> subjects = new HashMap<Subject, Double>();
		faculty.getSubjects().forEach(s -> subjects.put(s, 0.0));
		setSubjects(subjects);
	}


}
