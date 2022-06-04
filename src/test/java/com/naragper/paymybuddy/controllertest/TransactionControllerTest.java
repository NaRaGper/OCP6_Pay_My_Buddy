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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.naragper.paymybuddy.controller.TransactionController;
import com.naragper.paymybuddy.model.PaymentType;
import com.naragper.paymybuddy.model.Transaction;
import com.naragper.paymybuddy.service.ITransactionService;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	ITransactionService transactionService;

	private Transaction transaction = new Transaction();
	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeAll
	public void setup() {
		// Setting up the transaction
		transaction.setId(1);
		transaction.setType(PaymentType.USER);
		transaction.setSenderId(1);
		transaction.setReceiverId(2);
		transaction.setAmount(11111);
	}

	@Test
	void getTransactions() throws Exception {
		String transactionJson = objectMapper.writeValueAsString(transaction);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		// Building the request
		RequestBuilder request = MockMvcRequestBuilders.get("/transaction/list");
		Mockito.when(transactionService.getTransactions()).thenReturn(transactions);
		// Getting the result
		MvcResult result = mvc.perform(request).andReturn();
		// Verifying that the result contains the created transaction
		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}

	@Test
	void getTransaction_when_transaction_exists() throws Exception {
		Optional<Transaction> optionalTransaction = Optional.of(transaction);
		String transactionJson = objectMapper.writeValueAsString(transaction);

		RequestBuilder request = MockMvcRequestBuilders.get("/transaction").queryParam("id", "1");
		Mockito.when(transactionService.getTransaction(transaction.getId())).thenReturn(optionalTransaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}

	@Test
	void getTransaction_when_transaction_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/transaction").queryParam("id", "1");
		Mockito.when(transactionService.getTransaction(transaction.getId())).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be found"));
	}

	@Test
	void postTransaction_when_transaction_doesnt_exist() throws Exception {
		String transactionJson = objectMapper.writeValueAsString(transaction);

		RequestBuilder request = MockMvcRequestBuilders.post("/transaction").contentType(MediaType.APPLICATION_JSON)
				.content(transactionJson);
		Mockito.when(transactionService.postTransaction(transaction)).thenReturn(transaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}

	@Test
	void postTransaction_when_transaction_exists() throws Exception {
		String transactionJson = objectMapper.writeValueAsString(transaction);

		RequestBuilder request = MockMvcRequestBuilders.post("/transaction").contentType(MediaType.APPLICATION_JSON)
				.content(transactionJson);
		Mockito.when(transactionService.postTransaction(transaction)).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be created"));
	}

	@Test
	void putTransaction_when_transaction_exists() throws Exception {
		String transactionJson = objectMapper.writeValueAsString(transaction);

		RequestBuilder request = MockMvcRequestBuilders.put("/transaction").contentType(MediaType.APPLICATION_JSON)
				.content(transactionJson);
		Mockito.when(transactionService.putTransaction(transaction)).thenReturn(transaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}

	@Test
	void putTransaction_when_transaction_doesnt_exist() throws Exception {
		String transactionJson = objectMapper.writeValueAsString(transaction);

		RequestBuilder request = MockMvcRequestBuilders.put("/transaction").contentType(MediaType.APPLICATION_JSON)
				.content(transactionJson);
		Mockito.when(transactionService.putTransaction(transaction)).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be modified"));
	}

	@Test
	void deleteTransaction_when_transaction_exists() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/transaction").queryParam("id", "1");
		Mockito.when(transactionService.deleteTransaction(transaction.getId())).thenReturn(transaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("The transaction has been deleted"));
	}
	
	@Test
	void deleteTransaction_when_transaction_doesnt_exist() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.delete("/transaction").queryParam("id", "1");
		Mockito.when(transactionService.deleteTransaction(transaction.getId())).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be deleted"));
	}
	
	@Test
	void payUser_without_errors() throws Exception {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		queryParameters.add("senderId", "1");
		queryParameters.add("receiverId", "2");
		queryParameters.add("amount", "11111");
		queryParameters.add("description", "Description test");
		String transactionJson = objectMapper.writeValueAsString(transaction);
		RequestBuilder request = MockMvcRequestBuilders.post("/transaction/user").queryParams(queryParameters);
		Mockito.when(transactionService.payUser(1, 2, 11111, "Description test")).thenReturn(transaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}
	
	@Test
	void payUser_with_errors() throws Exception {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		queryParameters.add("senderId", "1");
		queryParameters.add("receiverId", "2");
		queryParameters.add("amount", "11111");
		queryParameters.add("description", "Description test");
		RequestBuilder request = MockMvcRequestBuilders.post("/transaction/user").queryParams(queryParameters);
		Mockito.when(transactionService.payUser(1, 2, 11111, "Description test")).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be completed"));
	}
	
	@Test
	void nonUserTransaction_without_errors() throws Exception {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		queryParameters.add("userId", "1");
		queryParameters.add("type", "TOCASH");
		queryParameters.add("amount", "11111");
		queryParameters.add("description", "Description test");
		String transactionJson = objectMapper.writeValueAsString(transaction);
		RequestBuilder request = MockMvcRequestBuilders.post("/transaction/other").queryParams(queryParameters);
		Mockito.when(transactionService.nonUserTransaction(1, PaymentType.DEBIT, 11111, "Description test")).thenReturn(transaction);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains(transactionJson));
	}
	
	@Test
	void nonUserTransaction_with_errors() throws Exception {
		MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<String, String>();
		queryParameters.add("userId", "1");
		queryParameters.add("type", "TOCASH");
		queryParameters.add("amount", "11111");
		queryParameters.add("description", "Description test");
		RequestBuilder request = MockMvcRequestBuilders.post("/transaction/other").queryParams(queryParameters);
		Mockito.when(transactionService.nonUserTransaction(1, PaymentType.DEBIT, 11111, "Description test")).thenReturn(null);

		MvcResult result = mvc.perform(request).andReturn();

		assertTrue(result.getResponse().getContentAsString().contains("This transaction cannot be completed"));
	}
}
