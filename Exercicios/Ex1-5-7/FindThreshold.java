public class FindThreshold{
    public static void main(String[] args){
        int
            dimension = Integer.parseInt(args[0]);

        Percolation3D
            percolate = new Percolation3D(dimension);
        percolate.curve(0.0, 0.0, 1.0, 1.0);
    }
}
