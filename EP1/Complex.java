/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *  Classe para manipulacao de numeros complexos, baseada em;
 *      http://introcs.cs.princeton.edu/java/32class/Complex.java.html
 *
/************************************************************************/

public class Complex{
    private static final double
        EPSILON = 0.00000001;   //Precisão
    private final double 
        re,                     // Parte real
        im;                     // Parte imaginaria

    public Complex(double re, double im){
        this.re = re;
        this.im = im;
    }

    // Retorna o numero complexo como uma string
    public String toString(){
        if(im == 0) 
            return re + "";
        if(re == 0) 
            return im + "i";
        if(im <  0) 
            return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    // Retorna modulo e fase
    public double abs(){
        return Math.hypot(re, im);
    } 
    public double phase(){ 
        return Math.atan2(im, re);
    }

    // Soma complexos e instancia um novo objeto Complex
    public Complex plus(Complex b){
        Complex 
            a = this;
        double 
            real = a.re + b.re,
            imag = a.im + b.im;
        return new Complex(real, imag);
    }

    // Subtrai numeros complesxos
    public Complex minus(Complex b){
        Complex
            a = this;
        double 
            real = a.re - b.re,
            imag = a.im - b.im;
        return new Complex(real, imag);
    }

    // Produto entre complexos
    public Complex times(Complex b){
        Complex
            a = this;
        double 
            real = a.re * b.re - a.im * b.im,
            imag = a.re * b.im + a.im * b.re;
        return new Complex(real, imag);
    }

    // Produto por um escalar
    public Complex times(double alpha){
        return new Complex(alpha * this.re, alpha * this.im);
    }

    // Retorna o comjugado complexo
    public Complex conjugate(){  
        return new Complex(this.re, -this.im);
    }

    // Retorna o reciproco de a
    public Complex reciprocal(){
        double 
            scale = this.re * this.re + this.im * this.im;
        return new Complex(this.re/scale, -this.im/scale);
    }

    // Retorna parte real
    public double re(){
        return this.re;
    }

    // Retorna parte imaginaria
    public double im(){
        return this.im;
    }

    // Divide numeros complexos
    public Complex divides(Complex b){
        Complex 
            a = this;
        return a.times(b.reciprocal());
    }

    // Retorna o exponencial do comlexo
    public Complex exp(){
        return new Complex(Math.exp(this.re) * Math.cos(this.im), Math.exp(this.re) * Math.sin(this.im));
    }

    // Retorna o log do complexo
    public Complex log(){
        return new Complex(Math.log(this.abs()), this.phase());
    }

    // Retorna o seno do comlexo
    public Complex sin(){
        return new Complex(Math.sin(this.re) * Math.cosh(this.im), Math.cos(this.re) * Math.sinh(this.im));
    }

    // Retorna cosseno do comlexo
    public Complex cos(){
        return new Complex(Math.cos(this.re) * Math.cosh(this.im), -Math.sin(this.re) * Math.sinh(this.im));
    }

    // Retorna a tangente do comlexo
    public Complex tan(){
        return sin().divides(cos());
    }

    // Potencia por um escalar
    public Complex pow(double k){
        double
            mod = this.abs(),
            ph  = this.phase();
        Complex
            aux;

        mod  = Math.pow(mod, k);
        aux  = new Complex(0, k * ph); 
        return (aux.exp()).times(mod);
    }

    // Potencia por um complexo
    public Complex pow(Complex c){
        double
            mod = this.abs(),
            ph  = this.phase();
        Complex
            aux,
            z;

        z    = this.pow(c.re);
        aux  = new Complex(0, c.im * Math.log(mod));
        aux  = (aux.exp()).times(1/Math.exp(c.im * ph));
        return aux.times(z);
    }

    // Escalar k elevedo ao complexo this.
    public Complex escalarPow(double k){
        double
            mod = this.abs(),
            ph  = this.phase();
        Complex
            aux;

        aux  = new Complex(0, this.im * Math.log(k)); 
        return (aux.exp()).times(Math.pow(k, this.re));
    }

    // Versao estatica da soma
    public static Complex plus(Complex a, Complex b){
        double 
            real = a.re + b.re,
            imag = a.im + b.im;
        Complex 
            sum = new Complex(real, imag);
        return sum;
    }
    
    // O número complexo é nulo?
    public boolean isZero(){
        Complex
            zero = new Complex(0, 0);

        return isEqual(zero);
    }

    // O número complexo é igual a b?
    public boolean isEqual(Complex b){
        double
            deviation = this.minus(b).abs();

        if(deviation < EPSILON)
            return true;
        return false;
    }
}
