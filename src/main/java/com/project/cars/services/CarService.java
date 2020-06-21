package com.project.cars.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.cars.dto.CarsDTO;
import com.project.cars.errors.InvalidFieldsException;
import com.project.cars.errors.MissingFieldsException;
import com.project.cars.errors.PlateAlreadyExistsException;
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

	public CarsDTO registerCar(CarsDTO carDTO, String login)
			throws MissingFieldsException, PlateAlreadyExistsException, InvalidFieldsException {

		Users user = userRepository.findByLogin(login);

		if (carDTO.getColor().isEmpty() || carDTO.getLicensePlate().isEmpty() || carDTO.getModel().isEmpty()
				|| carDTO.getYear() == null) {

			throw new MissingFieldsException();
		}

		try {
			Car licensePlateExist = carRepository.findByLicensePlate(carDTO.getLicensePlate());
			if (licensePlateExist != null) {
				throw new PlateAlreadyExistsException();
			}
            	
			Car car = modelMapper.map(carDTO, Car.class);
			car.setUser(user);
			carRepository.save(car);
			user.getListCars().add(car);
			userRepository.save(user);

		} catch (IllegalArgumentException e) {
			throw new InvalidFieldsException();
		}

		return carDTO;

	}
	
	public List<CarsDTO> listCarsUsers (String login) {
		Users user = userRepository.findByLogin(login);
		List<CarsDTO> listCars = user.getListCars()
				  .stream()
				  .map(car -> modelMapper.map(user, CarsDTO.class))
				  .collect(Collectors.toList());
		
		return listCars;
			
	}
	
	public CarsDTO findCarById(Integer id, String login) {
		
		Users user = userRepository.findByLogin(login);
		Car car = carRepository.findByIdByIdUser(id, user.getId());
					
		return modelMapper.map(car, CarsDTO.class);
	}
	
	public String removeCarById(Integer id, String login) {
		
		Users user = userRepository.findByLogin(login);
		
        String message = "";
		
		try{
			Car car = carRepository.findByIdByIdUser(id, user.getId());
									
			if(car != null) {
				user.getListCars().remove(car);
				carRepository.delete(car);	
				userRepository.save(user);
			}
				
			message = "Car deleted" + id ;
		}catch (Exception e) {
			 message = "erro ao deletar cars!";
		}
			
		return message;
	}
	
	
	public CarsDTO updateCarById(String login, CarsDTO carDTO, Integer id) throws MissingFieldsException, PlateAlreadyExistsException, InvalidFieldsException {
		
		Users user = userRepository.findByLogin(login);

		if (carDTO.getColor().isEmpty() || carDTO.getLicensePlate().isEmpty() || carDTO.getModel().isEmpty()
				|| carDTO.getYear() == null) {

			throw new MissingFieldsException();
		}

		try {
			Car licensePlateExist = carRepository.findByLicensePlate(carDTO.getLicensePlate());
			if (licensePlateExist != null) {
				throw new PlateAlreadyExistsException();
			}
			
			Car car = carRepository.findByIdByIdUser(id, user.getId()); 	
			user.getListCars().remove(car);
		    car = modelMapper.map(carDTO, Car.class);
			car.setUser(user);
			car.setId(id);		
			carRepository.save(car);
			user.getListCars().add(car);
			userRepository.save(user);

		} catch (IllegalArgumentException e) {
			throw new InvalidFieldsException();
		}

		return carDTO;

	}
	
	
	
}
