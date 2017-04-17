package org.example.ws.service.quote.tss;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteResponseSuccess {

    private int total;

    public QuoteResponseSuccess() {
        // TODO Auto-generated constructor stub
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
