package com.valerko.lgs.dto;

import com.valerko.lgs.domain.Subject;

import lombok.Data;

@Data
public class SubjectDto {
	Long id;
	String name;

	public static SubjectDto mapToSubjectDto(Subject subject) {
		SubjectDto subjectDto = new SubjectDto();
		subjectDto.setId(subject.getId());
		subjectDto.setName(subject.getName());
		return subjectDto;
	}
}
