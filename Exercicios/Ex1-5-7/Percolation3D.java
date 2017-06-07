public class Percolation3D{
    private WeightedQuickUnionPathC
        unions; //Objeto union-find.
    private int
        N,  //Numero de pontos a cada aresta do cubo.
        np; //Numero total de pontos contidos no cubo.

    public Percolation3D(int N){
        np = N * N * N;
        this.N = N;
    }

    private void setCubeRandom(double p){
        int
            i,
            j,
            k;
        boolean[][][]
            M = new boolean[this.N][this.N][this.N];    //Cubo com pontos booleanos randomicos entre 0 e 1 com probabilidade p de serem 1.

        this.unions = new WeightedQuickUnionPathC(np + 2);  //np + 2 posicoes pois duas delas nao pertencem ao cubo, a saber as posicoes 0 e np + 1.

        //Une as faces superiores e inferiores do cubo as posicoes 0 e np + 1, respectivamente.
        for(j = 0; j < this.N; j++){
            for(k = 0; k < this.N; k++){
                int a = (j * this.N + k) + 1;                               // As variaves a e b sao as coordenadas liarizadas do cubo
                int b = (this.np - this.N * this.N + j * this.N + k) + 1;   //soma-se sempre 1 as coordenadas devido as posicoes 0 e np + 1.
                this.unions.union(0, a);
                this.unions.union(np + 1, b);
            }
        }
        //-------------------------------------------------------------------------------------
        //Cria o cubo random.
        for(i = 0; i < this.N; i++){
            for(j = 0; j < this.N; j++){
                for(k = 0; k < this.N; k++)
                    M[i][j][k] = StdRandom.bernoulli(p);
            }
        }
        //------------------
        //Le o cubo e une os pontos "validos" (com valores 1).
        for(i = 0; i < this.N; i++){
            for(j = 0; j < this.N; j++){
                for(k = 0; k < this.N; k++){
                    int c = ((i * N + j) * N + k) + 1;          // Variaveis c, d, e, f linearizam as posicoes do cubo.
                    int d = ((i * N + j + 1) * N + k) + 1;      //Onde: c - posicao atual;      d - c deslocado 1 em j
                    int e = ((i * N + j) * N + k + 1) + 1;      //      e - c deslocado 1 em k; f - c deslocado 1 em i
                    int f = (((i + 1) * N + j) * N + k) + 1;
                    if(M[i][j][k] == false)
                        continue;
                    if(i != this.N - 1 && M[i + 1][j][k] == true)
                        unions.union(c, f);
                    if(j != this.N - 1 && M[i][j + 1][k] == true)
                        unions.union(c, d);
                    if(k != N - 1 && M[i][j][k + 1] == true)
                        unions.union(c, e);

                }
            }
        }
        //----------------------------------------------------
    }

    public void curve(double x0, double y0, double x1, double y1){
        int 
            M = 300;
        double 
            gap = .01,
            err = .0025,
            xm = (x0 + x1) / 2,
            ym = (y0 + y1) / 2,
            fxm = estimate(M, xm);

        if(Math.abs(fxm - 0.593) < err)
            StdOut.println(xm + " " + fxm);
        if (x1 - x0 < gap || Math.abs(ym - fxm) < err) {
            StdDraw.line(x0, y0, x1, y1);
            return;
        }
        curve(x0, y0, xm, fxm);
        StdDraw.filledCircle(xm, fxm, .005);
        curve(xm, fxm, x1, y1);
    }

    public double estimate(int M, double p){
        int
            count = 0;
        for(int i = 0; i < M; i++){
            if(percolates(p))
                count++;
        }
        return (double) count/M;
    }

    public boolean percolates(double p){
        setCubeRandom(p);
        if(unions.connected(0, np + 1))
            return true;
        return false;
    }
}
