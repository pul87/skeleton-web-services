package org.example.ws.service.quote.tss;

public interface QuoteService {

    String CATEGORY_INSPIRATIONAL = "inspire";

    Quote getDaily(String category);
}
