/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe que implementa (x - r[0])(x - r[1])...(x - r[n])
/************************************************************************/

public class Poly implements HolomorphicFunction{
    private int
        d;
    private Complex[]
        r;

    // Recebe as raizes do polinômio a ser inicializado
    public Poly(Complex[] r){
        this.d = r.length;
        this.r = r;
    }

    public Complex eval(Complex x){
        Complex 
            p = new Complex(1.0, 0.0);

        for (int i = 0; i < d; i++)
            p = p.times(x.minus(r[i]));
        return p;
    }

    public Complex diff(Complex x){
        Complex
            s,
            p;

        s = new Complex(0.0, 0.0);
        for (int i = 0; i < d; i++){
            p = new Complex(1.0, 0.0);
            for (int j = 0; j < d; j++)
                if (j != i)
                    p = p.times(x.minus(r[j]));
            s = s.plus(p);
        }
        return s;
    }
}
