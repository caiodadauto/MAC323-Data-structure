/*************************************************************************
 *      Exercicio de Programa 1
 *
 *      Nome: Caio Vinicius Dadauto
 *      Nusp: 7994808
 *
 *      Classe para determinar a desenhar as basias de Newton.
/************************************************************************/

import java.awt.Color;
import java.util.Arrays;

public class NewtonBasins{
    private static int
        capacity = 80;                              // Capacidade inicial dos vetores implementados.
    private static double[]
        hues = new double[capacity];                // Vetor para armazenar as cores já definidas para cada raiz encontrada.
    private static RootNewtonBasins[]
        roots = new RootNewtonBasins[capacity];     // Vetor para armazenar as raizes encontradas.

    // Método para realocar um novo vetor com o dobro do tamanho.
    private static void resize(){
        int
            i;
        double[]
            A = new double[2 * capacity];
        RootNewtonBasins[]
            B = new RootNewtonBasins[2 * capacity];

        for(i = 0; i < capacity; i++){
            A[i] =hues[i];
            B[i] = roots[i];
        }
        hues = A;
        roots = B;
        capacity *= 2;
    }

    // Verifica se a raiz complexa a já foi encontrada e está armazena em roots[].
    private static int search(int size, Complex a){
        int
            i;

        for(i = 0; i < size; i++)
            if(roots[i].isEqual(a))
                return i;
        return -1;
    }

    // Desenha a figura.
    public static void draw(HolomorphicFunction f, int MaxI, double x, double y, double xsize, double ysize, int M, int N){
        int
            i,
            j,
            index,      // Armazena o valor de retorno do método search.
            size = 0;   // Armazeno o número de raizes encontradas.
        float
            s = 1,      // Parametro s do padrao HSB
            b;          // Parametro b do padrao HSB
        double
            hue = 0,    // Parametro hue do padrao HSB
            re,
            im;
        Color
            color;
        Complex
            z,
            z0;
        Picture
            pic = new Picture(M, N);
        Counter
            counter = new Counter("iteration");

        for(i = 0; i < M; i++){
            for(j = 0; j < N; j++){
                // Aplica o método de newton tomando cada ponto da figura como z0,
                //além de inicializar o contador como zero.
                counter.set(0);
                re = (x - xsize * 0.5) + (i * xsize/M);
                im = (y - ysize * 0.5) + (j * ysize/N);
                z0 = new Complex(re, im);
                z  = Newton.findRoot(f, z0, counter);
                
                // Verifica se o método de Newton falhou.
                if(z.re() != Double.MIN_NORMAL){
                    index = search(size, z);     // Verifica se a raiz já foi encontrada.
                    if(index < 0){
                        // Verifica se há a necessidade de aplicar o resize().
                        if(size == capacity)
                            resize();

                        // Determina uma nova cor para a nova raiz z.
                        do{
                            hue = StdRandom.uniform();
                        }while(Arrays.binarySearch(hues, 0, size, hue) > 0);

                        hues[size]  = hue;
                        roots[size] = new RootNewtonBasins(z, hue);
                        Arrays.sort(hues, 0, size);     // Ordena o vetor hues, para poder usar o binarry search.
                        size++;
                    }
                    else
                        hue = roots[index].getHue();    // Se a raiz já foi encontrada, usa-se a mesma cor.

                    // Determina a intensidade da cor, baseando-se no número de iterações.
                    if(counter.tally() >= MaxI)
                        b = 1;
                    else
                        b = (float)counter.tally()/MaxI;
                }
                else
                    b = 0;
                color = Color.getHSBColor((float)hue, s, b);
                pic.set(i, j, color);
            }
        }
        pic.show();
    }
}
