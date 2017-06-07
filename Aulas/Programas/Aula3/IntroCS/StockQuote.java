/*************************************************************************
 *  Compilation:  javac StockQuote.java
 *  Execution:    java StockQuote symbol
 *  Dependencies: In.java
 *  
 *  Print the stock price of the stock with the given symbol. Screen scrapes
 *  finance.yahoo.com to get the current price and associated information.
 *
 *  % java StockQuote goog
 *  Google Inc. (GOOG)
 *  459.19
 *  Friday, March 23, 2007, 3:15PM ET 
 *
 *  % java StockQuote aapl
 *  Apple Computer Inc (AAPL)
 *  23.18
 *  Friday, February 27, 2004, 10:13am ET
 *
 *  % java StockQuote ibm
 *  International Business Machines Corp (IBM)
 *  97.07
 *  Friday, February 27, 2004, 10:13am ET
 *
 *  % java StockQuote msft
 *  Microsoft Corp (MSFT)
 *  26.51
 *  Friday, February 27, 2004, 10:13am ET
 *
 *************************************************************************/

public class StockQuote { 

    // Given symbol, get current stock price.
    public static String price(String symbol) {
        In page = new In("http://finance.yahoo.com/q?s=" + symbol); 
        String input = page.readAll();
        int trade = input.indexOf("time_rtq_ticker", 0);
        int from  = input.indexOf(">", trade);
        from      = input.indexOf(">", from + 1);
        int to    = input.indexOf("</span>", from);
        return input.substring(from + 1, to); 
    } 

    // Given symbol, get current stock price.
    public static String name(String symbol) {
        In page = new In("http://finance.yahoo.com/q?s=" + symbol); 
        String input = page.readAll(); 
        int p    = input.indexOf("<title>", 0);
        int from = input.indexOf("Summary for ", p);
        int to   = input.indexOf("- Yahoo! Finance", from);
        String name = input.substring(from + 12, to);
        return name;
    } 

    // Given symbol, get current stock price.
    public static String date(String symbol) {
        In page = new In("http://finance.yahoo.com/q?s=" + symbol); 
        String input = page.readAll(); 
        int p    = input.indexOf("<span id=\"yfs_market_time\">", 0);
        int from = input.indexOf(">", p);
        int to   = input.indexOf("-", from);        // no closing small tag
        String date = input.substring(from + 1, to);
        return date;
    } 


    public static void main(String[] args) {
        String symbol = args[0];
        StdOut.println(name(symbol));
        StdOut.println(price(symbol));
        StdOut.println(date(symbol));
    } 

}
