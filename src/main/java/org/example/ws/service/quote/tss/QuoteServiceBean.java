package org.example.ws.service.quote.tss;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteServiceBean implements QuoteService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CounterService counterService;

    private final RestTemplate restTemplate;

    public QuoteServiceBean(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Quote getDaily(String category) {
        logger.info("> getDaily");

        counterService.increment("method.invoked.quoteServiceBean.getDaily");

        String quoteCategory = QuoteServiceBean.CATEGORY_INSPIRATIONAL;

        if (category != null && category.trim().length() > 0) {
            quoteCategory = category.trim();
        }

        QuoteResponse quoteResponse = this.restTemplate.getForObject("http://quotes.rest/qod.json?category={cat}",
                QuoteResponse.class, quoteCategory);

        Quote quote = quoteResponse.getQuote();

        logger.info("< getDaily");

        return quote;
    }
}
