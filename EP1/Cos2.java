/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe que implementa a função f(x) = 1/cos(x) + x
/************************************************************************/

public class Cos2 implements HolomorphicFunction{
    public Cos2(){}

    // Retorna f(x) = 1/cos(x) + x
    public Complex eval(Complex x){
        return ((x.cos()).reciprocal()).plus(x);
    }

    // Retorna f’(x) = sen(x)/cos^2(x) + 1
    public Complex diff(Complex x) {
        return ((x.sin()).times((((x.cos()).pow(2)).reciprocal()))).plus(new Complex(1, 0));
    }
}
