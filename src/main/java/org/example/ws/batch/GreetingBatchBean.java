package org.example.ws.batch;

import java.util.Collection;

import org.example.ws.model.Greeting;
import org.example.ws.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class GreetingBatchBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GreetingService greetingService;
	
	// Gira ad ogni "0" e "30" di ogni minuto di ogni ora di ogni giorno di ogni mese di ogni anno.
	//@Scheduled(cron="0,30 * * * * *")
	public void chronJob() {
		logger.info("> chronJob ogni 0 e 30 secondi di ogni minuto.");
		
		// Scheduled logic here
		Collection<Greeting> greetings = greetingService.findAll();
		
		logger.info("There are {} greetings in the data store.", greetings.size());
		
		logger.info("< chronJob");
	}
	
	/*@Scheduled( 
			initialDelay=5000,
			fixedRate=5000 )*/
	public void fixedRateWithInitialDelay() {
		logger.info("> chronJob ogni 5 secondi, dopo 5 secondi dalla partenza.");
	}
	
	// Viene eseguito dopo un fixedDelay dall'esecuzione precedente
	/*@Scheduled( 
			initialDelay=2000,
			fixedDelay=10000 )*/
	public void fixedDelayJob() {
		logger.info("> chronJob ogni 10 secondi dopo il termine dell'esecizione precedente, dopo 2 secondi dalla partenza.");
	}
}
