package com.naragper.paymybuddy.controllertest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.http.MediaType;
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
		user.setId(1);
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

	@Test
	public void getUser_when_user_exists() throws Exception {
		Optional<User> optionalUser = Optional.of(user);

		String userJson = objectMapper.writeValueAsString(user);
		RequestBuilder request = MockMvcRequestBuilders.get("/user").queryParam("id", "1");
		Mockito.when(userService.getUser(user.getId())).thenReturn(optionalUser);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(userJson));
	}

	@Test
	public void getUser_when_user_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/user").queryParam("id", "1");
		Mockito.when(userService.getUser(user.getId())).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This user cannot be found"));
	}

	@Test
	public void postUser_when_user_doesnt_exist() throws Exception {
		String userJson = objectMapper.writeValueAsString(user);

		RequestBuilder request = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		Mockito.when(userService.postUser(user)).thenReturn(user);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(userJson));
	}

	@Test
	public void postUser_when_user_exists() throws Exception {
		String userJson = objectMapper.writeValueAsString(user);

		RequestBuilder request = MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		Mockito.when(userService.postUser(user)).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This user cannot be created"));
	}
	
	@Test
	public void putUser_when_user_exists() throws Exception {
		String userJson = objectMapper.writeValueAsString(user);

		RequestBuilder request = MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		Mockito.when(userService.putUser(user)).thenReturn(user);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(userJson));
	}
	
	@Test
	public void putUser_when_user_doesnt_exist() throws Exception {
		String userJson = objectMapper.writeValueAsString(user);

		RequestBuilder request = MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
				.content(userJson);
		Mockito.when(userService.putUser(user)).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This user cannot be modified"));
	}
	
	@Test
	public void deleteUser_when_user_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/user").queryParam("id", "1");
		Mockito.when(userService.deleteUser(user.getId())).thenReturn(user);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("The user has been deleted"));
	}
	
	@Test
	public void deleteUser_when_user_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/user").queryParam("id", "1");
		Mockito.when(userService.deleteUser(user.getId())).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This user cannot be deleted"));
	}
}
