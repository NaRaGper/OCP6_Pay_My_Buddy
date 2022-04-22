package com.naragper.paymybuddy.controllertest;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.naragper.paymybuddy.controller.UserController;
import com.naragper.paymybuddy.service.IUserService;

@SpringBootTest
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Mock
	private IUserService userService;

	@InjectMocks
	private UserController userController;

	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() {
		
	}
	
	@AfterEach
	public void teardown() {
		
	}
	
	@Test
	public void failingTest() {
		fail("nothing is implemented yet");
	}
}
