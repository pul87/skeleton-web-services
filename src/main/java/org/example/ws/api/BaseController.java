package org.example.ws.api;

import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.example.ws.web.DefaultExceptionAttributes;
import org.example.ws.web.ExceptionAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController {

	/**
	 * The logger for this class hierarchy
	 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// Exception handler
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception exception, HttpServletRequest request ) {
		
		logger.error("> handleException");
		logger.error("- Exception: ", exception);
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.INTERNAL_SERVER_ERROR);
		
		logger.error("< hanldeException");
		
		return new ResponseEntity<Map<String, Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Map<String, Object>> hendleNoResultFoundException(NoResultException noResultException, HttpServletRequest request) {
		
		logger.error("> handleNoResultFoundException");
		
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, request, HttpStatus.NOT_FOUND);
		
		logger.error("< handleNoResultFoundException");
		
		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.NOT_FOUND);
	}
}
