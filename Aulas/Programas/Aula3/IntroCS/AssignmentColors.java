/*************************************************************************
 * Example: 
 *************************************************************************/

import java.awt.Color;

public class AssignmentColors {

    public static void main(String[] args) {

        Color r, b, g;

	r = new Color(255, 0, 0);
	b = new Color(0, 0, 255);
	g = new Color(0, 255, 0);

        StdDraw.setPenColor(r);
        StdDraw.filledSquare(.25, .5, .2);

	b = r;
	r = g;

        StdDraw.setPenColor(b);
        StdDraw.filledSquare(.75, .5, .2);

    } 
} 

