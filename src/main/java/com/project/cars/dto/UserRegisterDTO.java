package com.project.cars.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {

	private String firstName;

	private String lastName;

	private String email;

	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthday;

	private String login;

	private String password;

	private String phone;


}
