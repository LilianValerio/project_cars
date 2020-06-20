package com.project.cars.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.cars.dto.CarsDTO;
import com.project.cars.services.CarService;

@RestController
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@PostMapping(value = "/cars", produces = { "application/json" }, consumes = "application/json")
	public CarsDTO registerCar(@RequestBody final CarsDTO carDTO)  {		
		UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return  carService.registerCar(carDTO, login.getUsername());
	}

}
