/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe que implementa a função f(x) = cos(x) + cos(2 x) + x
/************************************************************************/

public class Cos1 implements HolomorphicFunction{
    public Cos1(){}

    // Retorna f(x) = cos(x) + cos(2 x) + x
    public Complex eval(Complex x){
        return ((x.cos()).plus((x.times(2)).cos())).plus(x);
    }

    // returns f’(x) = - sen(x) - 2 sen(2 x) + 1
    public Complex diff(Complex x) {
        return (((x.sin()).times(-1)).minus(((x.times(2)).sin()).times(2))).plus(new Complex(1, 0));
    }
}
