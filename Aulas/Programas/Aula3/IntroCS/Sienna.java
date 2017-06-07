/*************************************************************************
 * Example: 
 *************************************************************************/

import java.awt.Color;

public class Sienna {

    public static void main(String[] args) {

	Color sienna = new Color(160, 82,  45);

        StdDraw.setPenColor(sienna);
        StdDraw.filledSquare(.17, .5, .13);

	Color c = sienna.darker();

        StdDraw.setPenColor(c);
        StdDraw.filledSquare(.5, .5, .13);

        StdDraw.setPenColor(sienna);
        StdDraw.filledSquare(.83, .5, .13);

    } 
} 

