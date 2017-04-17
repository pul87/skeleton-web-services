package org.example.ws.service.quote.tss;

import java.util.ArrayList;
import java.util.List;

public class QuoteResponseContents {

    private List<Quote> quotes = new ArrayList<>();

    public QuoteResponseContents() {
        // TODO Auto-generated constructor stub
    }

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        if (quotes == null) {
            this.quotes = new ArrayList<>(0);
        }
        this.quotes = quotes;
    }
}
