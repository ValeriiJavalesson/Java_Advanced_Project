package com.valerko.lgs.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter

public class FacultyReport implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToOne
	@JoinColumn(name = "faculty_id", referencedColumnName = "id")
	private Faculty faculty;
	@Column
	@ElementCollection
	private List<ApplicantApplication> applications = new ArrayList<ApplicantApplication>();
	@Column
	private Integer numberOfStudents = 1;

	public List<ApplicantApplication> getEnrolledApplication() {

		Collections.sort(applications, new Comparator<ApplicantApplication>() {
			public int compare(ApplicantApplication o1, ApplicantApplication o2) {
				return o2.compareTo(o1);
			}
		});
		if (numberOfStudents < applications.size()) {
			List<ApplicantApplication> toRemove = applications.subList(numberOfStudents, applications.size());
			applications.removeAll(toRemove);
		}
		return applications;
	}

}
