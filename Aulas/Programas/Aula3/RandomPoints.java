// From S&W, Section 2.2

public class RandomPoints {

    public static void main(String[] args)
    {
	int N = Integer.parseInt(args[0]);
	for (int i = 0; i < N; i++) {
	    double x = StdRandom.gaussian(.5, .05);
	    double y = StdRandom.gaussian(.5, .05);
	    StdDraw.point(x, y);
	}
	StdOut.println("I'm done!");
    }

}
