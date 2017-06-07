/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe para gerar as bacias de Newton para um polinimo de
 *      grau 10.
/************************************************************************/

public class PolyTen{
    public static void main(String[] args) {
        int
            maxI = Integer.parseInt(args[0]),
            M = Integer.parseInt(args[5]),
            N = Integer.parseInt(args[6]);
        double 
            xc = Double.parseDouble(args[1]),
            yc = Double.parseDouble(args[2]),
            xsize = Double.parseDouble(args[3]),
            ysize = Double.parseDouble(args[4]);

        StdRandom.setSeed((long) 2);
        Complex[] r = new Complex[10];
        r[0] = new Complex( 1, 0); r[1] = new Complex(-1, 0);
        r[2] = new Complex( 0, 1); r[3] = new Complex( 0, -1);
        r[4] = new Complex( 2, 0); r[5] = new Complex(-2, 0);
        r[6] = new Complex( 0, 2); r[7] = new Complex( 0, -2);
        r[8] = new Complex( 0, 3); r[9] = new Complex( 0, -3);
        HolomorphicFunction f = new Poly(r);
        NewtonBasins.draw(f, maxI, xc, yc, xsize, ysize, M, N);
    }
}
