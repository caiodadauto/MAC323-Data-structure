/****************************************************************************************
 * Caio Vinicius Dadauto   7994808             Estrutura de Dados 2                     *
 *                                             EP3                                      *
 *                                                                                      *
 * Compilacao:  javac-algs4 Maze.java                                                   *
 * Descricao:   O progrma recebe as dimensoes do labirinto N e uma semente opcional por *
 *              linha de comando. Dessa forma, le da entrada padrao um conjunto de      *
 *              dois pares de pontos pertencentes a {1, ... , N} sepearados             *
 *              por quebra de linha. Para cada dois pontos o programa desenha na tela   *
 *              a solucao do labirinto partiindo do primeiro ponto chegando ao segundo. *
 * Execucao:    java-algs4 Maze 63    < st.txt                                          *
 *              java-algs4 Maze 63 10 < st.txt                                          *
 *                                                                                      *
 ****************************************************************************************/

import java.awt.Color;

public class Maze {
    private int         N;                              // Dimensao do labirinto
    private int         xStart;                         // Ponto inicial
    private int         yStart;                         // Ponto inicial
    private int         xGoal;                          // Ponto destino
    private int         yGoal;                          // Ponto destino
    private int[]       edgeTo;                         // Armazena as arestas que ligam posicoes no labirinto
    private boolean[][] north;                          // True se ha´ parede a norte, false se nao ha´, isso para cada posicao (i, j) do labirinto
    private boolean[][] east;                           // Analogamente para o leste
    private boolean[][] south;                          // Analogamente para o sul
    private boolean[][] west;                           // Analogamente para o oeste
    private boolean[][] visited;                        // Verifica se a posicao (i, j) já foi visitada (true) ou nao (false)
    Stack<Integer>      path = new Stack<Integer>();    // Armazena o caminho necessario para chegar ao destino requisitado

    /**
     * Construtores para o objeto Maze
     * fornecendo a semente ou nao
    **/
    public Maze(int N, int xS, int yS, int xG, int yG, long seed) {
        this.N      = N;
        this.xStart = xS;
        this.yStart = yS;
        this.xGoal  = xG;
        this.yGoal  = yG;
        StdRandom.setSeed(seed);
        StdDraw.setCanvasSize(900, 700);
        StdDraw.setXscale(0, N + 2);
        StdDraw.setYscale(0, N + 2);
        init();
        generate();
        pathTo();
    }

    /**
     * Inicializa as variaveis intrincicas a uma instancia de Maze
    **/
    private void init() {
        // Inicializa as bordas das celulas como ja´ visitadas
        visited = new boolean[N + 2][N + 2];
        edgeTo  = new int[(N) * (N)];
        for(int x = 0; x < N + 2; x++)
            visited[x][0] = visited[x][N + 1] = true;

        for(int y = 0; y < N + 2; y++)
            visited[0][y] = visited[N + 1][y] = true;

        // Inicializa todas as paredes como estando presentes no labirinto
        north = new boolean[N + 2][N + 2];
        east  = new boolean[N + 2][N + 2];
        south = new boolean[N + 2][N + 2];
        west  = new boolean[N + 2][N + 2];

        for(int x = 0; x < N + 2; x++)
            for(int y = 0; y < N + 2; y++)
                north[x][y] = east[x][y] = south[x][y] = west[x][y] = true;
    }
    
    /**
     * Gera o labirinto paritindo do canto inferior esquerdo
     * e inicializa o vetor edgeTo de forma a criar uma arvore
     * com raiz em (1, 1) ou 0 em coordena linear
    **/
    private void generate() {
        generate(1, 1);
    }

    // Metodo recursivo que auxilia generate()
    private void generate(int x, int y) {
        visited[x][y] = true;

        // Enquanto hoouver um vizinho nao visitado
        while(!visited[x][y + 1] || !visited[x + 1][y] || !visited[x][y - 1] || !visited[x - 1][y]) {
            // Seleciona um vizinho randomicamente
            while(true) {
                // E´ sempre subtraido 1 de cada coordena x e y ao criar a coordenada linear, pois eles vao de 1 a N, e nao de 0 a N - 1
                double r = StdRandom.uniform();
                if(r < 0.25 && !visited[x][y + 1]) {
                    north[x][y] = south[x][y + 1] = false;
                    edgeTo[(x - 1) * N + y] = (x - 1) * N + (y - 1);
                    generate(x, y + 1);
                    break;
                }
                else if(r >= 0.25 && r < 0.50 && !visited[x + 1][y]) {
                    east[x][y] = west[x + 1][y] = false;
                    edgeTo[x * N + y - 1] = (x - 1) * N + y - 1;
                    generate(x + 1, y);
                    break;
                }
                else if(r >= 0.5 && r < 0.75 && !visited[x][y - 1]) {
                    south[x][y] = north[x][y - 1] = false;
                    edgeTo[(x - 1) * N + y - 2] = (x - 1) * N + (y - 1);
                    generate(x, y - 1);
                    break;
                }
                else if(r >= 0.75 && r < 1.00 && !visited[x - 1][y]) {
                    west[x][y] = east[x - 1][y] = false;
                    edgeTo[(x - 2) * N + y - 1] = (x - 1) * N + y - 1;
                    generate(x - 1, y);
                    break;
                }
            }
        }
    }

    /**
     * Gera o caminho para alcancar o destino desejado
     * para isso cria dois caminhos, um do ponto inicial
     * para o (1, 1) e outro do ponto de destino para o
     * (1, 1), dessa forma cria um novo camilnho do ponto
     * inicial para o de destino (a existencia destes
     * caminhos e´ garantida, pois o labirinto gerado
     * e´ perfeito, ou seja, possui sempre um unico
     * caminho para ligar dois pontos)
    **/
    public void pathTo() {
        int            node      = 0;
        int            stepGoal  = 0;
        int            stepStart = 0;
        int            start     = (xStart - 1) * N + yStart - 1;
        int            goal      = (xGoal - 1) * N + yGoal - 1;
        Stack<Integer> pathGoal  = new Stack<Integer>();
        Stack<Integer> pathStart = new Stack<Integer>();

        // Cria o caminho de (1, 1) ate´ o ponto de destino fornecido
        for(int step = goal; step != 0; step = edgeTo[step])
            pathGoal.push(step);
        pathGoal.push(0);

        // Cria o caminho de (1, 1) ate´ o ponto de inicio fornecido
        for(int step = start; step != 0; step = edgeTo[step])
            pathStart.push(step);
        pathStart.push(0);

        // Exclui a parte do camiinho que e´ igual para ambas as pilhas 
        while((stepGoal == stepStart) && !pathGoal.isEmpty() && !pathStart.isEmpty()) {
            node      = stepStart;
            stepGoal  = pathGoal.pop();
            stepStart = pathStart.pop();
        }

        // Empilha o ultimo passo antes de uma das pilhas (Goal ou Start) ser vazia 
        if(stepGoal == stepStart)
            pathGoal.push(stepStart);
        // Empinha novamente os primeiros pontos diferentes e o no´ que ramifica a arvore
        if(stepGoal != stepStart) {
            pathStart.push(stepStart);
            pathGoal.push(stepGoal);
            pathGoal.push(node);
        }

        while(!pathStart.isEmpty())
            pathGoal.push(pathStart.pop());
        this.path = pathGoal;
    }

    /**
     * Soluciona o labirinto utilizando a pilha path
     * de forma a tornar o tempo de execusao proporcional
     * a distancia entre o ponto de incio e o de destino
    **/
    private void solve() {
        int
            step,
            xStep,
            yStep;
        // O padrao HSB e´ usado para facilitar o usuario a vizualizar o caminho tracado pela solucao
        float hue   = (float)0.1528;
        float b     = (float)0.71;
        float s     = (float)1.0;
        float delta = (float)(0.7639 - 0.1528)/path.size();

        while(!path.isEmpty()) {
            step  = path.pop();
            xStep = (step / N) + 1;
            yStep = (step % N) + 1;
            StdDraw.setPenColor(Color.getHSBColor(hue, s, b));
            StdDraw.filledCircle(xStep + 0.5, yStep + 0.5, 0.25);
            StdDraw.show(30);
            hue += delta;
        }
        StdDraw.show(2000);
    }

    /**
     * Desenha o labirinto
    **/
    public void draw() {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.filledCircle(xGoal + 0.5, yGoal + 0.5, 0.375);
        StdDraw.filledCircle(xStart + 0.5, yStart + 0.5, 0.375);

        StdDraw.setPenColor(StdDraw.BLACK);
        for(int x = 1; x <= N; x++) {
            for(int y = 1; y <= N; y++) {
                if(south[x][y])
                    StdDraw.line(x, y, x + 1, y);
                if(north[x][y])
                    StdDraw.line(x, y + 1, x + 1, y + 1);
                if(west[x][y]) 
                    StdDraw.line(x, y, x, y + 1);
                if(east[x][y])  
                    StdDraw.line(x + 1, y, x + 1, y + 1);
            }
        }
        StdDraw.show(1000);
    }

    public static void main(String[] args) {
        Maze
            maze;
        int
            xS,
            yS,
            xG,
            yG;
        long
            seed;

        int  N    = Integer.parseInt(args[0]);
        
        // Verifica se o usuario forneceu a semente ou nao
        if(args.length > 1)
            seed = Long.parseLong(args[1]);
        else
            seed = StdRandom.getSeed();

        // Le da entrada padrao os pares origem/destino
        while(!StdIn.isEmpty()) {
            xS = StdIn.readInt();
            yS = StdIn.readInt();
            xG = StdIn.readInt();
            yG = StdIn.readInt();
            maze = new Maze(N, xS, yS, xG, yG, seed);

            StdDraw.show(0);
            maze.draw();
            maze.solve();
            StdDraw.clear();
        }
    }
}

