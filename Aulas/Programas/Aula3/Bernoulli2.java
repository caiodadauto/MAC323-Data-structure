/*************************************************************************
 *  Compilation:  javac Bernoulli.java
 *  Execution:    java Bernoulli N T
 *  Dependencies: StdDraw.java
 *  
 *  Each experiment consists of flipping N probability p coins T times.
 *  Plots a histogram of the number of times i of the N coins are heads.
 *
 *  % java Bernoulli2 32 1000 .5
 *
 *  % java Bernoulli2 64 1000 .3
 *
 *  % java Bernoulli2 128 10000 .2
 *
 *************************************************************************/

public class Bernoulli2 { 

    // number of heads when flipping N p-biased coins
    public static int binomial(int N, double p) {
        int heads = 0;
        for (int i = 0; i < N; i++) 
            if (StdRandom.bernoulli(p)) 
                heads++;
	return heads;
    } 

    // number of heads when flipping N fair coins
    // or call binomial(N, 0.5)
    public static int binomial(int N) {
        int heads = 0;
        for (int i = 0; i < N; i++) {
            if (StdRandom.bernoulli(0.5)) {
                heads++;
            }
        }
        return heads;
    } 

    public static void main(String[] args) { 
        int N = Integer.parseInt(args[0]);   // number of coins to flip per trial
        int T = Integer.parseInt(args[1]);   // number of trials
        double p = Double.parseDouble(args[2]);   // head probability

        StdDraw.setYscale(0, 1.0/Math.sqrt(2*Math.PI*N*p*(1-p)));

        // flip N fair coins, T times
        int[] freq = new int[N+1];
        for (int t = 0; t < T; t++) {
            freq[binomial(N, p)]++;
        }

        // plot normalized values
        double[] normalized = new double[N+1];
        for (int i = 0; i <= N; i++) {
            normalized[i] = (double) freq[i] / T;
        }
        StdStats.plotBars(normalized);

        // plot Gaussian approximation
        double mean = p*N;
        double stddev = Math.sqrt(N*p*(1-p));
        double[] phi  = new double[N+1];
        for (int i = 0; i <= N; i++) {
            phi[i] = Gaussian.phi(i, mean, stddev);
        }
        StdStats.plotLines(phi);
    } 
} 
