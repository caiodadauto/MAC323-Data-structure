/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe que implementa a função f(x) = x^k - q^x
/************************************************************************/

public class NonPoly implements HolomorphicFunction{
    private double
        q,
        k;

    public NonPoly(double k, double q){
        this.k = k;
        this.q = q;
    }

    // Valor da funcao f(x) = x^k - q^x
    public Complex eval(Complex x){
        return (x.pow(k)).minus(x.escalarPow(q));
    }

    // Valor da derivada f'(x) = (k/z) exp(k log(x)) - log(q) exp(x log(q))
    public Complex diff(Complex x){
        Complex
            c,
            w,
            t;

        c = (x.reciprocal()).times(k);
        w = ((x.log()).times(k)).exp();
        t = ((x.times(Math.log(q))).exp()).times(Math.log(q));
        return (c.times(w)).minus(t);
    }
}
