package org.example.ws.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.example.ws.AbstractControllerTest;
import org.example.ws.model.Greeting;
import org.example.ws.service.EmailService;
import org.example.ws.service.GreetingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingsControllerMocksTest extends AbstractControllerTest {

	@Mock // dice a mockito di analizzare l'interfaccia e di creare un mock con gli stessi metodi pubblici
	private EmailService emailService;
	@Mock
	private GreetingService greetingService;
	
	@InjectMocks // dice a mockito di iniettare i mock nel controller
	private GreetingController controller;
	
	private Collection<Greeting> getEntityListStubData() {
		
		Collection<Greeting> list = new ArrayList<>();
		list.add(getEntityStubData());
		return list;
	}
	
	private Greeting getEntityStubData() {
		Greeting entity = new Greeting();
		entity.setId(1L);
		entity.setText("Ciao");
		return entity;
	}
	
	@Before
	public void setup() {
		// inizializzazione di mockito, cerca le proprie annotazioni e inizializza gli oggetti
		MockitoAnnotations.initMocks(this);
		// A questo punto il controller corrente ha le mock e sono inizializzate, eseguo il setup
		// passando il controller con le mock al super.
		super.setup(controller);
	}
	
	@Test
	public void testGetGreetings() throws Exception {
		
		// Create some test data
		Collection<Greeting> list = getEntityListStubData();
		
		// Stub the GreetingService.findAll method return value
		when(greetingService.findAll()).thenReturn(list);
		
		// Perform the behavior being tested
		String uri = "/api/greetings";
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON)
		).andReturn();
		
		// Extract the response status and body
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		// Verify the GreetingService.findAll method was invoked once
		verify(greetingService, times(1)).findAll();
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected response body to have value", content.trim().length() > 0);
	}
	
	@Test
	public void testGreeting() throws Exception {
		
		String uri = "/api/greetings/{id}";
		
		Greeting greeting = getEntityStubData();
		Long id = new Long(1);
		when(greetingService.findOne(id)).thenReturn(greeting);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get(uri, id)
				.accept(MediaType.APPLICATION_JSON)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(greetingService, times(1)).findOne(id);
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected http response to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGreetingNotFound() throws Exception {
		
		String uri = "/api/greetings/{id}";
		
		Long id = Long.MAX_VALUE;
		when(greetingService.findOne(id)).thenReturn(null);
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders
				.get(uri, id)
				.accept(MediaType.APPLICATION_JSON)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(greetingService, times(1)).findOne(id);
		
		Assert.assertEquals("failure - expected http status 404", 404, status);
		Assert.assertTrue("failure - expected http response to have a value", content.trim().length() == 0);
	}
	
	@Test 
	public void testCreateGreeting() throws Exception {
		String uri = "/api/greetings";
		Greeting greeting = getEntityStubData();
		when(greetingService.create(any(Greeting.class))).thenReturn(greeting);
		
		String inputJson = mapToJson(greeting);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders
				.post(uri)
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 201", 201, status);
		Assert.assertTrue("failure - expected response body to have a value", content.trim().length() > 0);
		
		verify(greetingService, times(1)).create(any(Greeting.class));
		
		Greeting createdGreeting = mapFromJson(content, Greeting.class);
		
		Assert.assertNotNull("failure - expected not null", createdGreeting);
		Assert.assertNotNull("failure - expected not null", createdGreeting.getId());
		Assert.assertEquals("failure - expected text attribute match", createdGreeting.getText(), greeting.getText());
	}
	
	@Test 
	public void testUpdateGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Greeting greeting = getEntityStubData();
		greeting.setText(greeting.getText()+ " test");
		when(greetingService.update(any(Greeting.class))).thenReturn(greeting);
		
		String inputJson = mapToJson(greeting);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders
				.put(uri, greeting.getId())
				.content(inputJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(greetingService, times(1)).update(any(Greeting.class));
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected response body to have a value", content.trim().length() > 0);
		
		Greeting updatedGreeting = mapFromJson(content, Greeting.class);
		
		Assert.assertNotNull("failure - expected not null", updatedGreeting);
		Assert.assertNotNull("failure - expected not null", updatedGreeting.getId());
		Assert.assertEquals("failure - expected id not changed", greeting.getId(), updatedGreeting.getId());
		Assert.assertEquals("failure - expected text attribute match", updatedGreeting.getText(), greeting.getText());
	}
	
	@Test
	public void testDeleteGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.delete(uri, id)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		verify(greetingService, times(1)).delete(id);
		
		Assert.assertEquals("failure - expected http status 204", 204, status);
		Assert.assertTrue("failure - expected response body to be empty", content.trim().length() == 0);
	}
}
