package com.project.cars.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Car {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(updatable = false, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private int year;

	@Column(nullable = false)
	private String licensePlate;

	@Column(nullable = false)
	private String model;

	@Column(nullable = false)
	private String color;

}
