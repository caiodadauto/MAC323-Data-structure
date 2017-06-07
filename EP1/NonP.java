/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe para gerar as bacias de Newton para NonPoly
/************************************************************************/

public class NonP{
    public static void main(String[] args) {
        int
            maxI = Integer.parseInt(args[0]),
            M = Integer.parseInt(args[5]),
            N = Integer.parseInt(args[6]);
        double 
            xc = Double.parseDouble(args[1]),
            yc = Double.parseDouble(args[2]),
            xsize = Double.parseDouble(args[3]),
            ysize = Double.parseDouble(args[4]),
            k = Double.parseDouble(args[7]),
            q = Double.parseDouble(args[8]);

        StdRandom.setSeed((long) 2);
        HolomorphicFunction f = new NonPoly(k, q);
        NewtonBasins.draw(f, maxI, xc, yc, xsize, ysize, M, N);
    }
}
