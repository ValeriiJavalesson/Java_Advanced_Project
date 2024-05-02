package com.valerko.lgs.dto;

import com.valerko.lgs.domain.User;
import com.valerko.lgs.domain.UserRole;

import lombok.Data;

@Data
public class UserDto{

	private String email;
	private String firstname;
	private String lastname;
	private UserRole role;
	private String encodedImage;

	public static UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setEmail(user.getEmail());
		userDto.setFirstname(user.getFirstname());
		userDto.setLastname(user.getLastname());
		userDto.setRole(user.getRole());
		userDto.setEncodedImage(user.getEncodedImage());
		return userDto;
	}


}
