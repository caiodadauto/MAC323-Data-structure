/*************************************************************************
 *  Compilation:  javac Newton.java
 *  Execution:    java Newton x
 *
 *************************************************************************/

public class Newton {

    // find root x such that f(x) = 0 using Newton's method, starting at x0
    public static double findRoot(DifferentiableFunction f, double x0) {
        double eps = 1e-15;
        double x = x0;
        double delta = f.eval(x) / f.diff(x);
        while (Math.abs(delta) > (eps / x)) {
            x = x - delta;
           delta = f.eval(x) / f.diff(x);
	   StdOut.println(x);
        }
        return x;
    }
    
    // sample client program
    public static void main(String[] args) { 
	double c = Double.parseDouble(args[0]);
	DifferentiableFunction f = new Sqrt(c);
	StdOut.println(findRoot(f, c));
    }
    
}
