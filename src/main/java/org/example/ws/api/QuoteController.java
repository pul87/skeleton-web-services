package org.example.ws.api;

import org.example.ws.service.quote.tss.Quote;
import org.example.ws.service.quote.tss.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController extends BaseController {

    @Autowired
    private QuoteService quoteService;

    @RequestMapping(value = "/api/quotes/daily", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quote> getQuoteOfTheDay() {
        logger.debug("> getQuoteOfTheDay");
        Quote quote = quoteService.getDaily(QuoteService.CATEGORY_INSPIRATIONAL);

        if (quote == null) {
            return new ResponseEntity<Quote>(HttpStatus.NOT_FOUND);
        }
        logger.debug("< getQuoteOfTheDay");

        return new ResponseEntity<Quote>(quote, HttpStatus.OK);
    }
}
