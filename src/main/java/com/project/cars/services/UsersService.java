package com.project.cars.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.project.cars.config.JwtTokenProvider;
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
import com.project.cars.repositories.UsersRepository;

@Service
@Transactional(readOnly = false)
public class UsersService {

	@Autowired
	private UsersRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ModelMapper modelMapper;
	
	  @Autowired
	    private PasswordEncoder passwordEncoder;

	public TokenDTO signin(SigninUserDTO user) throws MissingFieldsException, InvalidLoginOrPasswordException {
		if (StringUtils.isEmpty(user.getLogin()) || StringUtils.isEmpty(user.getPassword())) {
			throw new MissingFieldsException();
		}
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
			final Users userToUpdate = userRepository.findByLogin(user.getLogin());
			if (userToUpdate == null) {
				throw new InvalidLoginOrPasswordException();
			}
			userToUpdate.setLastLogin(LocalDateTime.now());
			userRepository.save(userToUpdate);
			return getTokenDTO(user.getLogin());
		} catch (final AuthenticationException e) {
			throw new InvalidLoginOrPasswordException();
		}
	}

	public TokenDTO getTokenDTO(String login) {
		final String token = jwtTokenProvider.createToken(login);
		return new TokenDTO(token);
	}

	@SuppressWarnings("unused")
	public UsersDTO registerUsers(UserRegisterDTO register) throws MissingFieldsException, EmailAlredyExistsException,
			LoginAlredyExistsException, InvalidFieldsException {

		fieldsValid(register);

		try {
			Users existsEmail = userRepository.findByEmail(register.getEmail());
			if (existsEmail != null) {
				throw new EmailAlredyExistsException();
			}

			Users existsLogin = userRepository.findByLogin(register.getLogin());
			if (existsEmail != null) {
				throw new LoginAlredyExistsException();
			}

			Users user = modelMapper.map(register, Users.class);
			user.setCreatedAt(LocalDateTime.now());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
            
			Users resgisterUser = userRepository.save(user);

			UsersDTO dtoUser = modelMapper.map(resgisterUser, UsersDTO.class);

			return dtoUser;

		} catch (IllegalArgumentException e) {
			throw new InvalidFieldsException();

		}

	}

	public List<UsersDTO> listUsers() {

		List<Users> list = userRepository.findAll();

		List<UsersDTO> listReturn = list.stream().map(user -> {
			return modelMapper.map(user, UsersDTO.class);
		}).collect(Collectors.toList());

		return listReturn;

	}

	public UsersDTO findById(int id) {
		Optional<Users> user = userRepository.findById(id);
		return modelMapper.map(user.get(), UsersDTO.class);
	}

	public String removeById(int id) {
		
		String message = "deleted user!";
		
		try{
			userRepository.deleteById(id);	
		}catch (Exception e) {
			 message = "erro ao deletar!";
		}
			
		return message;
	}

	@SuppressWarnings("unused")
	public UsersDTO updateById(Integer id, UserRegisterDTO register) throws MissingFieldsException,
			EmailAlredyExistsException, LoginAlredyExistsException, InvalidFieldsException {

		Optional<Users> userExists = userRepository.findById(id);

		fieldsValid(register);

		try {
			Users existsEmail = userRepository.findByEmail(register.getEmail());
			if (existsEmail != null) {
				throw new EmailAlredyExistsException();
			}

			Users existsLogin = userRepository.findByLogin(register.getLogin());
			if (existsEmail != null) {
				throw new LoginAlredyExistsException();
			}

			Users User = modelMapper.map(register, Users.class);
			User.setId(userExists.get().getId());

			Users resgisterUser = userRepository.save(User);

			UsersDTO dtoUser = modelMapper.map(resgisterUser, UsersDTO.class);

			return dtoUser;

		} catch (IllegalArgumentException e) {
			throw new InvalidFieldsException();

		}

	}

	private void fieldsValid(UserRegisterDTO register) throws MissingFieldsException {
		if (StringUtils.isEmpty(register.getLogin()) || StringUtils.isEmpty(register.getPassword())
				|| StringUtils.isEmpty(register.getFirstName()) || StringUtils.isEmpty(register.getLastName())
				|| StringUtils.isEmpty(register.getEmail()) || StringUtils.isEmpty(register.getBirthday())
				|| StringUtils.isEmpty(register.getPhone())) {
			throw new MissingFieldsException();
		}
	}

	public UsersDTO me(String login) {
		Users user = userRepository.findByLogin(login);
		return modelMapper.map(user, UsersDTO.class);
	}

}
