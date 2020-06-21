package com.project.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.cars.models.Car;

@Repository
public interface  CarRepository extends JpaRepository<Car, Integer>{ 
	
 Car findByLicensePlate (String licensePlate);
 
 @Query("SELECT c FROM Car c WHERE c.id = ?1 and c.user.id = ?2")
 Car findByIdByIdUser(Integer id , Integer idUser);

}
