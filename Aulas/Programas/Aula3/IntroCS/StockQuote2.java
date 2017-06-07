/*************************************************************************
 *
 *************************************************************************/

public class StockQuote2 { 

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

    // Given symbol, get source file
    public static String sourceFile(String symbol) {
        In page = new In("http://finance.yahoo.com/q?s=" + symbol); 
	return page.readAll(); 
    } 

    public static void main(String[] args) {
        String symbol = args[0];
        String source = args[1];
	Out out = new Out(source);
	out.print(sourceFile(symbol));
        StdOut.println(name(symbol));
        StdOut.println(price(symbol));
        StdOut.println(date(symbol));
    } 

}
