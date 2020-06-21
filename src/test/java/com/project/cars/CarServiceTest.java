package com.project.cars;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.cars.config.JwtTokenProvider;
import com.project.cars.dto.CarsDTO;
import com.project.cars.dto.UsersDTO;
import com.project.cars.repositories.UsersRepository;
import com.project.cars.services.CarService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CarServiceTest {
	
	@Mock
	private UsersRepository userRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private JwtTokenProvider jwtTokenProvider;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private ModelMapper modelMapper;
	@InjectMocks
	private CarService casService;
	
	UsersDTO mockUser ;
	List<CarsDTO> listCarDto;
	
	
	@BeforeEach
	void setMockOutput() {
      

	}


}
