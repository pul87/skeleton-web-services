package org.example.ws.service;

import java.util.concurrent.CompletableFuture;

import org.example.ws.model.Greeting;

public interface EmailService {
	
	// send an email synchronously
	Boolean send(Greeting greeting);
	
	// send an email asynchronously
	void sendAsync(Greeting greeting);
	
	CompletableFuture<Object> sendAsyncWithResult(Greeting greeting);
	
}
