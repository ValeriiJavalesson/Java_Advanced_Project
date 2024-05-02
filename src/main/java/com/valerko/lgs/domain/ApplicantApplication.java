package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data

public class ApplicantApplication implements Serializable, Comparable<ApplicantApplication> {

	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	@Column
	private Double certificatePoints;
	@ManyToOne
	private Faculty faculty;
	@Column(name = "subject_points")
	@ElementCollection
	private Map<Subject, Double> subjects;

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
		Map<Subject, Double> subjects = new HashMap<Subject, Double>();
		faculty.getSubjects().forEach(s -> subjects.put(s, 0.0));
		setSubjects(subjects);
	}

	@Override
	public int compareTo(ApplicantApplication o) {
		return (int) (this.getCertificatePoints()
				+ this.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)))
				- (int) (o.getCertificatePoints()
						+ o.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)));
	}

}
