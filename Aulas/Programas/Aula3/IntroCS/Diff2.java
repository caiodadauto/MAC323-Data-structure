/*************************************************************************
 *
 *************************************************************************/

public class Diff2 {

    public static void main(String[] args) {
	In in0 = new In(args[0]);
	In in1 = new In(args[1]);
	String s = in0.readAll();
	String t = in1.readAll();
	StdOut.println(args[0] + ":");
	StdOut.println(s);
	StdOut.println(args[1] + ":");
	StdOut.println(t);
	StdOut.println("Equal files?");
	StdOut.println(s.equals(t));
    } 

}
