package com.project.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.RepositoryQuery;
import org.springframework.stereotype.Repository;

import com.project.cars.models.Car;
import com.project.cars.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{ 
	
	Users findByLogin (String login);
	
	Users findByEmail (String email);
	
	

}
