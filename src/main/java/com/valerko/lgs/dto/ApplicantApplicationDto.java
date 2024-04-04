package com.valerko.lgs.dto;

import java.util.Map;
import java.util.stream.Collectors;

import com.valerko.lgs.domain.ApplicantApplication;

import lombok.Data;

@Data
public class ApplicantApplicationDto {
	private String username;
	private Double certificatePoints;
	private String faculty;
	private Map<String, Double> subjects;

	public static ApplicantApplicationDto getApplicantApplicationDto(ApplicantApplication applicaton) {
		ApplicantApplicationDto applicationDto = new ApplicantApplicationDto();
		applicationDto.username = applicaton.getUser().getEmail();
		applicationDto.certificatePoints = applicaton.getCertificatePoints();
		applicationDto.setFaculty(applicaton.getFaculty().getName());
		Map<String, Double> collect = applicaton.getSubjects().entrySet().stream()
				.collect(Collectors.toMap(t -> t.getKey().getName(), Map.Entry::getValue));
		applicationDto.setSubjects(collect);
		return applicationDto;
	}
}
