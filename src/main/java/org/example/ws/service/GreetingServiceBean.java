package org.example.ws.service;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
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
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	public Greeting create(Greeting greeting) {
		if( greeting.getId() != null ) {
			return null;
		}
		
		Greeting savedGreeting = greetingRepository.save(greeting);
		
		// Illustrate a tx rollback
		if( savedGreeting.getId() == 4L ) {
			throw new RuntimeException("Roll me back");
		}
		return savedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
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
