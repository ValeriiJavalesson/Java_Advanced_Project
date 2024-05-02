package com.valerko.lgs.dto;

import java.util.HashMap;
import java.util.Map;

import com.valerko.lgs.domain.Faculty;

import lombok.Data;

@Data
public class FacultyDto {
	private Long id;
	private String name;
	private Map<Integer, String> subjects = new HashMap<>();
	
	public static FacultyDto mapToFacultyDto(Faculty faculty) {
		FacultyDto facultyDto = new FacultyDto();
		facultyDto.setId(faculty.getId());
		facultyDto.setName(faculty.getName());
		return facultyDto;
	}

}
