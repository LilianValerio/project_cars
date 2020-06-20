package com.project.cars.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarsDTO {

	private Integer id;

	private int year;

	private String licensePlate;

	private String model;

	private String color;
}
