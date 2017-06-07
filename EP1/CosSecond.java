/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe para gerar as bacias de Newton para Cos2
/************************************************************************/

public class CosSecond{
    public static void main(String[] args){
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
        HolomorphicFunction f = new Cos2();
        NewtonBasins.draw(f, maxI, xc, yc, xsize, ysize, M, N);
    }
}
