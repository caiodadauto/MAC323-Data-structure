/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Interface para funções holomorfas.
/************************************************************************/

public interface HolomorphicFunction {
    public Complex 
        eval(Complex z);    // Retorna o valor de f(z)
    public Complex 
        diff(Complex z);    // Retorna o valor de f'(z)
}
