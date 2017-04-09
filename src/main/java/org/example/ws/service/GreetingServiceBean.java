package org.example.ws.service;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceBean implements GreetingService {
	
	@Autowired
	private GreetingRepository greetingRepository;
	
	@Override
	public Collection<Greeting> findAll() {
		return (Collection<Greeting>) greetingRepository.findAll();
	}

	@Override
	public Greeting findOne(Long id) {
		return greetingRepository.findOne(id);
	}

	@Override
	public Greeting create(Greeting greeting) {
		if( greeting.getId() != null ) {
			return null;
		}
		return greetingRepository.save(greeting);
	
	}

	@Override
	public Greeting update(Greeting greeting) {
		Greeting greetingPersisted = greetingRepository.findOne(greeting.getId());
		
		if(greetingPersisted == null ) {
			return null;
		}
		Greeting updatedGreeting = greetingRepository.save(greetingPersisted);
		return updatedGreeting;
	}

	@Override
	public void delete(Long id) {
		greetingRepository.delete(id);
	}

}
