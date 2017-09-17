package com.example.spectrus.stockide.AppObject;

/**
 * Needed for Quotes found by the user query
 */

public class FoundQuote {

    // Variables needed for the Quotes
    public String symbol;
    public String name;
    public String exchange;

    // Constructor
    public FoundQuote(String s, String n, String e){
        super();
        symbol = s;
        name = n;
        exchange = e;
    }
}