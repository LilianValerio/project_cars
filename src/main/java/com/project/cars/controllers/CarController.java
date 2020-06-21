package com.project.cars.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.cars.dto.CarsDTO;
import com.project.cars.dto.UserRegisterDTO;
import com.project.cars.dto.UsersDTO;
import com.project.cars.errors.EmailAlredyExistsException;
import com.project.cars.errors.InvalidFieldsException;
import com.project.cars.errors.LoginAlredyExistsException;
import com.project.cars.errors.MissingFieldsException;
import com.project.cars.errors.PlateAlreadyExistsException;
import com.project.cars.models.Users;
import com.project.cars.repositories.CarRepository;
import com.project.cars.services.CarService;

@RestController
@RequestMapping("/api")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@PostMapping(value = "/cars", produces = { "application/json" }, consumes = "application/json")
	public CarsDTO registerCar(@RequestBody final CarsDTO carDTO) throws MissingFieldsException, PlateAlreadyExistsException, InvalidFieldsException  {		
		UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return  carService.registerCar(carDTO, login.getUsername());
	}
	
	@GetMapping(value = "/cars", produces = { "application/json" })
	public List<CarsDTO> listCarsUsers() {
		UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		return carService.listCarsUsers(login.getUsername());
	}
		
		
		@GetMapping(value = "/cars/{id}", produces = { "application/json" })
		public CarsDTO findCarById(@PathVariable("id") Integer id) {
			UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
			return carService.findCarById(id , login.getUsername());
		}
		
		
		@DeleteMapping(value = "/cars/{id}", produces = { "application/json" })
		public String removeCarById(@PathVariable("id") int id) {	
			UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
			return carService.removeCarById(id, login.getUsername());
		}
		
		@PutMapping(value = "/cars/{id}", produces = { "application/json" })
		public CarsDTO updateCarById(@PathVariable("id") int id , @RequestBody final CarsDTO CarDTO) throws MissingFieldsException, EmailAlredyExistsException, LoginAlredyExistsException, InvalidFieldsException, PlateAlreadyExistsException {
			UserDetails login = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
			return carService.updateCarById(login.getUsername(), CarDTO, id);
		}
		
		
	
	}
	


