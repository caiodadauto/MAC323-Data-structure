public class RootDiophantine{
    public static void main(String[] args){
        int
            k,
            j;
        long
            r,
            max = 100000;
        P
            p;
        G
            g;
        MaxPQ<G>
            pg = new MaxPQ<G>();

        for(k = 0; k < 266; k++){
            for(j = 0; j < 1883; j++){
                if((3*j*j*j + 4*k*k*k*k) < 2000100000)
                    pg.insert(new G(j, k));
            }
        }

        g = pg.delMax();
        while(!pg.isEmpty()){
            for(k = 0; k <= max; k++){
                r = g.getSum() - 2*k*k;
                if(r >= 0 && r <= 100000){
                    p = new P(r, k);
                    StdOut.println(p.toString() + " = " + g.toString());
                }
            }
            g   = pg.delMax();
            max = (long)Math.sqrt(g.getSum()/2);
            if(max > 100000)
                max = 100000;
        }
    }
}
