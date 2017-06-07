/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe dedicada ao método de Newton.
/************************************************************************/

public class Newton{
    public static Complex findRoot(HolomorphicFunction f, Complex z0, Counter N){
        boolean
            done = false;   // Se a raiz foi encontrada ou não.
        Complex
            fz,
            dfz,
            oldz = z0,
            newz = z0,
            fail = new Complex(Double.MIN_NORMAL, Double.MIN_NORMAL);   // Caso o  método de Newton falhe.

        while(!done && N.tally() < 600){
            oldz = newz;
            fz   = f.eval(oldz);
            dfz  = f.diff(oldz);

            // Para evitar Double.Nan como valor de retorno.
            if(dfz.isZero())
                return fail;
            newz = oldz.minus(fz.divides(dfz));

            // Verifica se a raiz foi encontrada dentro da precisao EPSILON (vide Comlplex.java).
            if(newz.isEqual(oldz))
                done = true;
            N.increment();
        }

        // Caso não foi possível encontrar a raiz dentro de 600 iteracoes.
        if(!done)
            return fail;
        return newz;
    }
}
