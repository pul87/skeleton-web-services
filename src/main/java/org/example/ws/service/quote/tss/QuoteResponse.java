package org.example.ws.service.quote.tss;

/**
 * 
 * @author pul
 *
 */
public class QuoteResponse {

    private QuoteResponseSuccess success;

    private QuoteResponseContents contents;

    public QuoteResponse() {
        // TODO Auto-generated constructor stub
    }

    public QuoteResponseSuccess getSuccess() {
        return success;
    }

    public void setSuccess(QuoteResponseSuccess success) {
        this.success = success;
    }

    public QuoteResponseContents getContents() {
        return contents;
    }

    public void setContents(QuoteResponseContents contents) {
        this.contents = contents;
    }

    public boolean isSuccess() {

        if (success != null && success.getTotal() > 0) {
            return true;
        }

        return false;
    }

    public Quote getQuote() {
        if (isSuccess()) {
            if (contents != null && contents.getQuotes().size() > 0) {
                return contents.getQuotes().get(0);
            }
        }
        return null;
    }

}
