package com.project.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cars.models.Car;

@Repository
public interface  CarRepository extends JpaRepository<Car, Integer>{ 
	


}
