package com.project.cars;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import com.project.cars.dto.SigninUserDTO;
import com.project.cars.dto.TokenDTO;
import com.project.cars.dto.UserRegisterDTO;
import com.project.cars.dto.UsersDTO;
import com.project.cars.models.Car;
import com.project.cars.models.Users;
import com.project.cars.repositories.UsersRepository;
import com.project.cars.services.UsersService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UsersServiceTest {

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
	private UsersService userService;

	List<Users> users;
	List<Car> cars;
	String MOCKTOKENJWT = "tokenjwt";

	LocalDateTime DATE_CREATE = LocalDateTime.of(2019, 10, 14, 19, 21);
	LocalDateTime LAST_UPDATE = LocalDateTime.of(2020, 10, 14, 19, 23);
	LocalDate BIRTHDAY = LocalDate.of(1995, 02, 12);
	Users mockUsers;
	UserRegisterDTO registerDTO;
	UsersDTO usersDTO;
	SigninUserDTO signinUserDTO;
	TokenDTO tokenDto;

	final UsersDTO userdto = new UsersDTO();

	@BeforeEach
	void setMockOutput() {

		mockUsers = new Users(1, "Teste", "Apps", "teste@teste.com", LocalDate.now(), "testeApps", "123", "987475874",
				null, DATE_CREATE, new ArrayList<Car>());

		registerDTO = UserRegisterDTO.builder().firstName("Teste").lastName("Apps").email("teste@teste.com")
				.birthday(LocalDate.now()).login("testeApps").password("123").phone("987475874").build();

		usersDTO = UsersDTO.builder().firstName("Teste").lastName("Apps").email("teste@teste.com").birthday(BIRTHDAY)
				.login("testeApps").phone("987475874").createdAt(DATE_CREATE).lastLogin(LAST_UPDATE).build();

		signinUserDTO = SigninUserDTO.builder().login("testeApps").password("123").build();

		tokenDto = TokenDTO.builder().token("tokenjwt").build();
		users = new ArrayList<Users>();
		users.add(mockUsers);

	}

	@Test
	public void shouldRegisterSuccess() {

		when(userRepository.findByLogin(mockUsers.getLogin())).thenReturn(null);
		when(userRepository.findByEmail(mockUsers.getEmail())).thenReturn(null);
		when(userRepository.save(mockUsers)).thenReturn(mockUsers);
		when(modelMapper.map(registerDTO, Users.class)).thenReturn(mockUsers);
		when(modelMapper.map(mockUsers, UsersDTO.class)).thenReturn(usersDTO);

		assertDoesNotThrow(() -> {

			UsersDTO userSave = userService.registerUsers(registerDTO);

			assertNotNull(userSave);

		});
	}

	@Test
	public void shouldReturnNotEmptyList() {
		when(userRepository.findAll()).thenReturn(users);
		List<UsersDTO> returnList = userService.listUsers();
		assertTrue(!returnList.isEmpty());

	}

	@Test
	public void shouldReturnMeNotNull() {

		when(userRepository.findByLogin(mockUsers.getLogin())).thenReturn(mockUsers);
		when(modelMapper.map(mockUsers, UsersDTO.class)).thenReturn(userdto);
		UsersDTO returnMe = userService.me(mockUsers.getLogin());
		assertNotNull(returnMe);

	}

	@Test
	public void signUpSuccess() {
		when(userRepository.findByLogin(mockUsers.getLogin())).thenReturn(mockUsers);
		when(userRepository.save(mockUsers)).thenReturn(mockUsers);
		when(jwtTokenProvider.createToken(mockUsers.getLogin())).thenReturn(MOCKTOKENJWT);

		assertDoesNotThrow(() -> {

			TokenDTO token = userService.signin(signinUserDTO);

			assertNotNull(token);
			assertTrue(!token.getToken().isEmpty());
			assertEquals(MOCKTOKENJWT, token.getToken());

		});

	}
	
	// Falta o teste das excecoes
}
