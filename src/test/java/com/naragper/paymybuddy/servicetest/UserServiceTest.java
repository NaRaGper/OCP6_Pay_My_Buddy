package com.naragper.paymybuddy.servicetest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.repository.IUserRepository;
import com.naragper.paymybuddy.service.IUserService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class UserServiceTest {
	
	@MockBean
	IUserRepository userRepository;
	
	@Autowired
	IUserService userService;
	
	// Create a new user
	User user = new User();
	// Create a mocked repository
	List<User> mockedRepository = new ArrayList<User>();
	
	@BeforeAll
	void setUp() {
		// Makes sure the database is empty
		userRepository.deleteAll();
		// SetUp the user
		user.setId(1);
		user.setEmail("user@test.com");
		user.setHash(BCrypt.hashpw("password", BCrypt.gensalt()));
		user.setBalance(100);
		user.setBankAccountNumber("ABCD1111EFGH");
		// SetUp the repository
		mockedRepository.add(user);
	}
	
	@AfterAll
	void teardown() {
		userRepository.deleteAll();
		user = null;
	}
	
	@Test
	void getUsers() {
		Mockito.when(userRepository.findAll()).thenReturn(mockedRepository);
		
		List<User> result = userService.getUsers();
		
		assertTrue(result.contains(mockedRepository.get(0)));
	}
	
	@Test
	void getUser() {
		Mockito.when(userRepository.findById(user.getId())).thenReturn(user);
		
		Optional<User> result = userService.getUser(user.getId());
		
		assertNotNull(result);
		assertTrue(result.get().getEmail().contains(user.getEmail()));
	}
	
	// getUser_when_user_doesnt_exist()
	
	@Test
	void postUser() {
		Mockito.when(userRepository.save(user)).thenReturn(user);
		
		User result = userService.postUser(user);
		
		assertNotNull(result);
	}
	
	// postUser_when_user_already_exists()
	
	@Test
	void putUser() {
		
	}
	
	// putUser_when_user_doesntexist()
	
	// deleteUser()
	
	// deleteUser_when_user_doesnt_exist()
}
