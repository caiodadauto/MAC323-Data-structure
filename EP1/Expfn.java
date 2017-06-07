/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe que implementa a função f(x) = exp(x) - x
/************************************************************************/

public class Expfn implements HolomorphicFunction{
    public Expfn(){}

    // Retorna f(x) = exp(x) - x
    public Complex eval(Complex x){
        return (x.exp()).minus(x);
    }

    // Retorna f’(x) = exp(x) - 1
    public Complex diff(Complex x) {
        return (x.exp()).minus(new Complex(1, 0));
    }
}
