/*************************************************************************
 *
 *************************************************************************/

public class FilePageSource { 

    public static String sourceFile(String URL) {
        In page = new In(URL); 
	return page.readAll(); 
    } 
    
    public static void main(String[] args) {
        String URL = args[0];
	String input = sourceFile(URL);
	Out out;

	if (args.length == 1) 
	    out = new Out();
	else {
	    String source = args[1];
	    out = new Out(source);
	}

	out.print(input);
    } 
}
