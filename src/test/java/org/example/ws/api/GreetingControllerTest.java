package org.example.ws.api;

import org.example.ws.AbstractControllerTest;
import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class GreetingControllerTest extends AbstractControllerTest {

	@Autowired
	private GreetingService greetingService;
	
	@Before
	public void setup() {
		// Inizializza il MockMvc della classe astratta
		super.setup();
		// pulisce la cache del servizio
		greetingService.evictCache();
	}
	
	@Test
	public void testGetGreetings() throws Exception {
		String uri = "/api/greetings";
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders.get(uri).accept(
						MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected http response body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGetGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		
		Long id = new Long(1);
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected http response body to have a value", content.trim().length() > 0);
	}
	
	@Test
	public void testGreetingNotFound() throws Exception {
		String uri = "/api/greeings/{id}";
		Long id = Long.MAX_VALUE;
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 404", 404, status);
		Assert.assertTrue("failure - expected http response body to be empty", content.trim().length() == 0);
	}
	
	@Test
	public void testCreateGreeting() throws Exception {
		String uri = "/api/greetings";
		Greeting greeting = new Greeting();
		String testText = "wee!!";
		greeting.setText(testText);
		String inputJson = super.mapToJson(greeting);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders
					.post(uri)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(inputJson)
				).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected response status 201", 201, status);
		Assert.assertTrue("failure - expected response body to have a value", content.trim().length() > 0);
		
		Greeting createdGreeting = super.mapFromJson(content, Greeting.class);
		
		Assert.assertNotNull("failure - expected not null", createdGreeting);
		Assert.assertNotNull("failure - expected not null", createdGreeting.getId());
		Assert.assertEquals("failure - expected match", testText, createdGreeting.getText());
	}
	
	@Test
	public void testUpdateGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		
		Greeting greeting = greetingService.findOne(id);
		String updatedText = greeting.getText() + " test";
		greeting.setText(updatedText);
		
		String inputJson = super.mapToJson(greeting);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders
				.put(uri, id)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(inputJson)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 200", 200, status);
		Assert.assertTrue("failure - expected response body to have a value", content.trim().length() > 0);
		
		Greeting updatedGreeting = super.mapFromJson(inputJson, Greeting.class);
		
		Assert.assertNotNull("failure - expected not null", updatedGreeting);
		Assert.assertNotNull("failure - expected not null", updatedGreeting.getId());
		Assert.assertEquals("failure - expected match", updatedText, updatedGreeting.getText());
	}
	
	@Test
	public void testDeleteGreeting() throws Exception {
		String uri = "/api/greetings/{id}";
		Long id = new Long(1);
		
		Greeting greeting = greetingService.findOne(id);
		
		Assert.assertNotNull(greeting);
		
		MvcResult result = mvc.perform(
				MockMvcRequestBuilders
				.delete(uri, id)
		).andReturn();
		
		String content = result.getResponse().getContentAsString();
		int status = result.getResponse().getStatus();
		
		Assert.assertEquals("failure - expected http status 204", 204, status);
		Assert.assertTrue("failure - epxected http response body to be empty", content.trim().length() == 0);
		
		greeting = greetingService.findOne(id);
		
		Assert.assertNull("failure - expected equal null", greeting);
	}
}
