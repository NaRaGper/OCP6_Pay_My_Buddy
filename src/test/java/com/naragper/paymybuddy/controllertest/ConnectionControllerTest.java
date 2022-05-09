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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naragper.paymybuddy.controller.ConnectionController;
import com.naragper.paymybuddy.model.Connection;
import com.naragper.paymybuddy.service.IConnectionService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(ConnectionController.class)
class ConnectionControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	IConnectionService connectionService;

	private Connection connection = new Connection();
	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public void setup() {
		// Setting up the connection
		connection.setId(1);
		connection.setUserId1(1);
		connection.setUserId2(2);
	}

	@Test
	void getConnections() throws Exception {
		String connectionJson = objectMapper.writeValueAsString(connection);
		List<Connection> connections = new ArrayList<>();
		connections.add(connection);
		// Building the request
		RequestBuilder request = MockMvcRequestBuilders.get("/connection/list");
		Mockito.when(connectionService.getConnections()).thenReturn(connections);
		// Getting the result
		MvcResult result = mvc.perform(request).andReturn();
		// Verifying that the result contains the created connection
		assertTrue(result.getResponse().getContentAsString().contains(connectionJson));
	}
	
	@Test
	void getConnection_when_connection_exists() throws Exception {
		Optional<Connection> optionalConnection = Optional.of(connection);
		
		String connectionJson = objectMapper.writeValueAsString(connection);
		
		RequestBuilder request = MockMvcRequestBuilders.get("/connection").queryParam("id", "1");
		Mockito.when(connectionService.getConnection(connection.getId())).thenReturn(optionalConnection);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains(connectionJson));
	}
	
	@Test
	void getConnection_when_connection_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/connection").queryParam("id", "1");
		Mockito.when(connectionService.getConnection(connection.getId())).thenReturn(null);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("This connection cannot be found"));
	}

	@Test
	void postConnection_when_connection_doesnt_exist() throws Exception {
		String connectionJson = objectMapper.writeValueAsString(connection);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/connection").contentType(MediaType.APPLICATION_JSON).content(connectionJson);
		Mockito.when(connectionService.postConnection(connection)).thenReturn(connection);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains(connectionJson));
	}
	
	@Test
	void postConnection_when_connection_exists() throws Exception {
		String connectionJson = objectMapper.writeValueAsString(connection);
		
		RequestBuilder request = MockMvcRequestBuilders.post("/connection").contentType(MediaType.APPLICATION_JSON).content(connectionJson);
		Mockito.when(connectionService.postConnection(connection)).thenReturn(null);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("This connection already exists"));
	}
	
	@Test
	void putConnection_when_connection_exists() throws Exception {
		String connectionJson = objectMapper.writeValueAsString(connection);
		
		RequestBuilder request = MockMvcRequestBuilders.put("/connection").contentType(MediaType.APPLICATION_JSON).content(connectionJson);
		Mockito.when(connectionService.putConnection(connection)).thenReturn(connection);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains(connectionJson));
	}
	
	@Test
	void putConnection_when_connection_doesnt_exist() throws Exception {
		String connectionJson = objectMapper.writeValueAsString(connection);
		
		RequestBuilder request = MockMvcRequestBuilders.put("/connection").contentType(MediaType.APPLICATION_JSON).content(connectionJson);
		Mockito.when(connectionService.putConnection(connection)).thenReturn(null);
		
		MvcResult result = mvc.perform(request).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("This connection cannot be modified"));
	}
	
	@Test
	void deleteConnection_when_connection_exists() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/connection").queryParam("id", "1");
		Mockito.when(connectionService.deleteConnection(connection.getId())).thenReturn(connection);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("The connection has been deleted"));
	}
	
	@Test
	void deleteConnection_when_connection_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/connection").queryParam("id", "1");
		Mockito.when(connectionService.deleteConnection(connection.getId())).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This connection cannot be deleted"));
	}
}
