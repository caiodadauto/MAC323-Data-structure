/*************************************************************************
 *  Compilation:  javac ColorMandelbrot.java
 *  Execution:    java Mandelbrot xmid ymid size < colors.txt
 *  Dependencies: Picture.java StdIn.java
 *
 *  Plots the Mandelbrot set in color.
 *
 *  % java ColorMandelbrot -.5 0 2
 *
 *  // increase dwell
 *  % java ColorMandelbrot -0.7615134027775 0.0794865972225 0.0032285925920
 *
 *************************************************************************/

import java.awt.Color;

public class ColorMandelbrot {

    // return number of iterations to check if c = a + ib is in Mandelbrot set
    public static int mand(Complex z0, int d) {
        Complex z = z0;
        for (int t = 0; t < d; t++) {
            if (z.abs() > 2.0) return t;
            z = z.times(z).plus(z0);
        }
        return d;
    }

    public static void main(String[] args)  {
        double xc   = Double.parseDouble(args[0]);
        double yc   = Double.parseDouble(args[1]);
        double size = Double.parseDouble(args[2]);

        // int N = 512;
        int N = 1024;

        int ITERS = 256;


        // read in color map
        Color[] colors = new Color[ITERS];
        for (int t = 0; t < ITERS; t++) {
            int r = StdIn.readInt();
            int g = StdIn.readInt();
            int b = StdIn.readInt();
            colors[t] = new Color(r, g, b);
        }


        // compute Mandelbrot set
        Picture pic = new Picture(N, N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                double x = xc - size/2 + size*i/N;
                double y = yc - size/2 + size*j/N;
                Complex z0 = new Complex(x, y);
                int t = mand(z0, ITERS - 1);
                pic.set(i, N-1-j, colors[t]);
            }
        }
        pic.show();
    }

}
