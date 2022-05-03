package com.naragper.paymybuddy.controllertest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naragper.paymybuddy.controller.UserController;
import com.naragper.paymybuddy.model.User;
import com.naragper.paymybuddy.service.IUserService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	IUserService userService;
	
	private User user = new User();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@BeforeAll
	public void setup() {
		// Setting up the user
		user.setEmail("user@mail.test");
		user.setHash(BCrypt.hashpw("password134", BCrypt.gensalt()));
		user.setUsername("User1");
		user.setBalance(10101);
		user.setBankAccountNumber("111ABCDEFGH111");
	}
	
	@Test
	public void getUsers() throws Exception {
		String userJson = objectMapper.writeValueAsString(user);
		List<User> users = new ArrayList<>();
		users.add(user);
		// Building the request
		RequestBuilder request = MockMvcRequestBuilders.get("/user/list");
		Mockito.when(userService.getUsers()).thenReturn(users);
		// Getting the result
		MvcResult result = mvc.perform(request).andReturn();
		// Verifying that the result contains the created user
		assertTrue(result.getResponse().getContentAsString().contains(userJson));
	}
}
