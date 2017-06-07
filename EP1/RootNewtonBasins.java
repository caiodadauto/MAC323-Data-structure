/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe para armazenar as raizes encontradas durante a
 *      geracao das bacias de Newton, juntamente as saus respectivas
 *      cores (coeficiente hue do padrao HSB).
/************************************************************************/

public class RootNewtonBasins extends Complex{
    private final double
        hue;

    public RootNewtonBasins(Complex z, double hue){
        super(z.re(), z.im());
        this.hue = hue;
    }

    public double getHue(){
        return this.hue;
    }
}
