package org.example.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	@Cacheable(
			value="greetings", // definisce la cache da utilizzare ( vedere il cacheManager creato a livello di application )
			key="#id") // definisce quale valore univoco per identificare l'oggetto nella cache
	public Greeting findOne(Long id) {
		return greetingRepository.findOne(id);
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CachePut( // annotazione utilizzata per aggiungere item nella cache o aggiornarli
			value="greetings", // cache da utilizzare
			key="#result.id") // definisce il valore univoco da usare come chiave nella cache, il valore di ritorno della create viene inserito in result quindil'id risulta essere #result.id
	public Greeting create(Greeting greeting) {
		if( greeting.getId() != null ) {

            throw new EntityExistsException("The id attribute must be null to persist a new entity.");
		}
		
		Greeting savedGreeting = greetingRepository.save(greeting);
		return savedGreeting;
	}

	@Override
	@Transactional(
			propagation=Propagation.REQUIRED, 
			readOnly=false)
	@CachePut(
			value="greetings",
			key="#greeting.id") // Attenzione!! Non result come nella create! Se l'item con chiave #greeting.id era già nella cache allora viene aggiornato se no viene inserito
	public Greeting update(Greeting greeting) {
		Greeting greetingPersisted = greetingRepository.findOne(greeting.getId());
		
		if(greetingPersisted == null ) {
			throw new NoResultException("Requested entity not found.");
		}
		Greeting updatedGreeting = greetingRepository.save(greetingPersisted);
		
		return updatedGreeting;
	}

	@Override
	@CacheEvict( // Rimuove l'elemento dalla cache quando il metodo termina senza errori 
			value="greetings",
			key="#id")
	public void delete(Long id) {
		greetingRepository.delete(id);
	}

	@Override
	@CacheEvict(
			value="greetings", 
			allEntries=true)
	public void evictCache() {
		
	}
}
