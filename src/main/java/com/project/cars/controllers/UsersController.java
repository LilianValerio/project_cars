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

import com.project.cars.dto.SigninUserDTO;
import com.project.cars.dto.TokenDTO;
import com.project.cars.dto.UserRegisterDTO;
import com.project.cars.dto.UsersDTO;
import com.project.cars.errors.EmailAlredyExistsException;
import com.project.cars.errors.InvalidFieldsException;
import com.project.cars.errors.InvalidLoginOrPasswordException;
import com.project.cars.errors.LoginAlredyExistsException;
import com.project.cars.errors.MissingFieldsException;
import com.project.cars.models.Users;
import com.project.cars.services.UsersService;

@RestController
@RequestMapping("/api")
public class UsersController {

	@Autowired
	private UsersService userServices;
		
	@PostMapping(value = "/signin", produces = { "application/json" }, consumes = "application/json")
	public TokenDTO signin(@RequestBody final SigninUserDTO user)
			throws MissingFieldsException, InvalidLoginOrPasswordException {
		return userServices.signin(user);
	}
	
	
	@PostMapping(value = "/users", produces = { "application/json" }, consumes = "application/json")
	public UsersDTO registerUsers(@RequestBody final UserRegisterDTO user) throws MissingFieldsException,
			EmailAlredyExistsException, LoginAlredyExistsException, InvalidFieldsException {		
		
		return userServices.registerUsers(user);
	}

	@GetMapping(value = "/users", produces = { "application/json" })
	public List<UsersDTO> listUsers() {
		return userServices.listUsers();
	}
	
	@GetMapping(value = "/users/{id}", produces = { "application/json" })
	public UsersDTO findById(@PathVariable("id") int id) {
		return userServices.findById(id);
	}
	
	
	@DeleteMapping(value = "/users/{id}", produces = { "application/json" })
	public Optional<Users> removeById(@PathVariable("id") int id) {
		return userServices.removeById(id);
	}
	
	@PutMapping(value = "/users/{id}", produces = { "application/json" })
	public UsersDTO updateById(@PathVariable("id") int id , @RequestBody final UserRegisterDTO register) throws MissingFieldsException, EmailAlredyExistsException, LoginAlredyExistsException, InvalidFieldsException {
		return userServices.updateById(id, register);
	}
	
	@GetMapping(value = "/me", produces = { "application/json" })
	public UsersDTO me() {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userServices.me(user.getUsername());
	}
	
}
