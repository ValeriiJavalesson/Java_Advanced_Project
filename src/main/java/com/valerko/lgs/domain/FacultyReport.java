package com.valerko.lgs.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
public class FacultyReport {
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

	public Map<User, Double> getEnrolledUsers() {
		Map<User, Double> collect = applications.stream()
				.collect(Collectors.toMap(ApplicantApplication::getUser,
						applicatio -> applicatio.getCertificatePoints() + applicatio.getSubjects().values().stream()
								.collect(Collectors.summingDouble(Double::doubleValue))));

		Map<User, Double> sortedCollect = collect.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));

		while (sortedCollect.size() > numberOfStudents) {
			User key = sortedCollect.keySet().iterator().next();
			sortedCollect.remove(key);
		}
		
		Map<User, Double> sortedCollectCutted = new TreeMap<User, Double>(
				Comparator.comparing(sortedCollect::get).reversed());
		sortedCollectCutted.putAll(sortedCollect);


		return sortedCollectCutted;
	}
}
