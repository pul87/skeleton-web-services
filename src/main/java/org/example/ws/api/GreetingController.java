package org.example.ws.api;

import java.util.Collection;
import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GreetingController {
	
	@Autowired
	private GreetingService greetingService;
	
	@GetMapping("/greetings")
	public ResponseEntity<Collection<Greeting>> getGreetings() {
		
		Collection<Greeting> greetings = greetingService.findAll();
		return new ResponseEntity<Collection<Greeting>>(greetings, HttpStatus.OK);
	}
	
	@GetMapping("/greetings/{id}")
	public ResponseEntity<Greeting> getGreeting(@PathVariable("id") Long id) {
		Greeting greeting = greetingService.findOne(id);
		
		if ( greeting == null ) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
		}
	}
	
	@PostMapping(value="/greetings", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> createGreeting(@RequestBody Greeting greeting) {
		
		Greeting savedGreeting = greetingService.create(greeting);
		
		return new ResponseEntity<Greeting>(savedGreeting, HttpStatus.CREATED);
	}
	
	@PutMapping(value="/greetings/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Greeting> updateGreeting(@RequestBody Greeting greeting) {
		Greeting updatedGreeting = greetingService.update(greeting);
		
		if ( updatedGreeting == null ) {
			return new ResponseEntity<Greeting>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Greeting>(updatedGreeting, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/greetings/{id}")
	public ResponseEntity<Greeting> deleteGreeting(@PathVariable Long id) {
		
		greetingService.delete(id);
		
		return new ResponseEntity<Greeting>(HttpStatus.NO_CONTENT);
	}
}
