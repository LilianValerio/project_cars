package com.project.cars.services;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cars.dto.CarsDTO;
import com.project.cars.dto.UsersDTO;
import com.project.cars.models.Car;
import com.project.cars.models.Users;
import com.project.cars.repositories.CarRepository;
import com.project.cars.repositories.UsersRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UsersRepository userRepository;

	public CarsDTO registerCar(CarsDTO carDTO, String login) {

		Users user = userRepository.findByLogin(login);

		Car car = modelMapper.map(carDTO, Car.class);
		user.setListCars(new ArrayList<Car>());
		user.getListCars().add(car);

		return null;

	}
}
