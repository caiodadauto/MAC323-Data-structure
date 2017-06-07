/*************************************************************************
 * Examble: 
 * $ java-introcs AssignmentArrays
 * 3
 * 1 2 3
 * 3
 *         1         2         3 
 * 3
 *         2         2         3 
 *************************************************************************/

public class AssignmentArrays {

    public static void main(String[] args) {

        int[] v, w;

	v = StdArrayIO.readInt1D(); 
	StdArrayIO.print(v); 

	w = v;
	StdArrayIO.print(w); 

	v[0] = 2*v[0];

	StdArrayIO.print(w); 

    } 
} 

