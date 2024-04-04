package com.valerko.lgs.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class FacultyDto {
	private String facultyName;
	private Map<Integer, String> subjects = new HashMap<>();

}
