package com.project.cars.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.cars.models.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>{ 
	
	Users findByLogin (String login);
	
	Users findByEmail (String email);
	
	

}
