package com.valerko.lgs.dto;

import java.util.Map;
import java.util.stream.Collectors;

import com.valerko.lgs.domain.ApplicantApplication;

import lombok.Data;

@Data
public class ApplicantApplicationDto implements Comparable<ApplicantApplicationDto>{
	private Long Id;
	private String username;
	private String firstname;
	private String lastname;
	private Double certificatePoints;
	private String faculty;
	private Map<String, Double> subjects;

	public static ApplicantApplicationDto mapToApplicantApplicationDto(ApplicantApplication application) {
		ApplicantApplicationDto applicationDto = new ApplicantApplicationDto();
		applicationDto.setId(application.getId());
		applicationDto.setUsername(application.getUser().getEmail());
		applicationDto.setFirstname(application.getUser().getFirstname());
		applicationDto.setLastname(application.getUser().getLastname());
		applicationDto.setCertificatePoints(application.getCertificatePoints());
		applicationDto.setFaculty(application.getFaculty().getName());
		Map<String, Double> collect = application.getSubjects().entrySet().stream()
				.collect(Collectors.toMap(t -> t.getKey().getName(), Map.Entry::getValue));
		applicationDto.setSubjects(collect);
		return applicationDto;
	}
	
	@Override
	public int compareTo(ApplicantApplicationDto o) {
		return (int) (this.getCertificatePoints()
				+ this.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)))
				- (int) (o.getCertificatePoints()
						+ o.getSubjects().values().stream().collect(Collectors.summingDouble(Double::doubleValue)));
	}
}
