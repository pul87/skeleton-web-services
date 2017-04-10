package org.example.ws.service;

import java.util.concurrent.CompletableFuture;
import org.example.ws.model.Greeting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceBean implements EmailService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Boolean send(Greeting greeting) {
		
		logger.info("> send");
		
		Boolean success = Boolean.FALSE;
		long pause = 5000;
		
		try {
			Thread.sleep(pause);
		} catch(Exception e) {
			// 
		}
		
		logger.info("Processing time was {}.", pause / 1000);
		
		success = Boolean.TRUE;
		
		logger.info("< send");
		return success;
	}

	@Override
	@Async
	public void sendAsync(Greeting greeting) {

		logger.info("> sendAsync");
		
		try {
			send(greeting);
		} catch (Exception e) {
			logger.warn("Exception caught ending asynchronous email.", e);
		}
		
		logger.info("< sendAsync");

	}

	@Override
	@Async
	public CompletableFuture<Object> sendAsyncWithResult(Greeting greeting) {
		
		logger.info("> sendAsyncWithResult");
		
		CompletableFuture<Object> result = CompletableFuture.
				supplyAsync(() ->  send(greeting))
				.thenApply(a -> { logger.info("finito risultato {}", a.booleanValue()); return a.booleanValue(); });
	
		logger.info("< sendAsyncWithResult");
		
		return result;
	}

}
