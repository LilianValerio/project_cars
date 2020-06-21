package com.project.cars.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.project.cars.models.Car;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersDTO {

	private String id;
	
	private String firstName;

	private String lastName;

	private String email;

	private String login;
	
	private LocalDate birthday;

	private String phone;

	private LocalDateTime lastLogin;
	
	private LocalDateTime createdAt;
	
	private List<CarsDisplayDTO> listCars;


}
