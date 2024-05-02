package com.valerko.lgs.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.valerko.lgs.domain.ApplicantApplication;
import com.valerko.lgs.domain.FacultyReport;

import lombok.Data;

@Data
public class FacultyReportDto {

	private String faculty;
	private List<ApplicantApplication> applications = new ArrayList<ApplicantApplication>();
	private Integer numberOfStudents = 1;

	public static FacultyReportDto mapToFacultyReportDto(FacultyReport report) {
		FacultyReportDto facultyReportDto = new FacultyReportDto();
		facultyReportDto.setFaculty(report.getFaculty().getName());
		facultyReportDto.setNumberOfStudents(report.getNumberOfStudents());
		facultyReportDto.setApplications(report.getApplications());
		return facultyReportDto;
	}
	
	public static List<ApplicantApplicationDto> getEnrolledApplication(FacultyReport report) {		
		List<ApplicantApplicationDto> collect = report.getEnrolledApplication().stream().map(ApplicantApplicationDto::mapToApplicantApplicationDto).collect(Collectors.toList());
		collect.sort(new Comparator<ApplicantApplicationDto>() {
			public int compare(ApplicantApplicationDto o1, ApplicantApplicationDto o2) {
				return o2.compareTo(o1);}
		});
		return collect;
	}

}
