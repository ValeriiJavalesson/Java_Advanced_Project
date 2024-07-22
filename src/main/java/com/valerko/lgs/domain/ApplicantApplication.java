package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

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

	public double getSumOfPoints() {
		double sumOfSubjects = this.getSubjects().entrySet().stream().mapToDouble(e -> e.getValue()).sum();
		return sumOfSubjects + this.getCertificatePoints();
	}

	@Override
	public int compareTo(ApplicantApplication o) {
		return (int) (this.getCertificatePoints()
				+ this.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)))
				- (int) (o.getCertificatePoints()
						+ o.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)));
	}

	@Override
	public String toString() {
		return "ApplicantApplication [user=" + user + ", certificatePoints=" + certificatePoints + ", faculty="
				+ faculty + ", subjects=" + subjects + "]";
	}

}
