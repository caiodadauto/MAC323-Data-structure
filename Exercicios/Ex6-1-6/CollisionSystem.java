/*************************************************************************
 *  Compilation:  javac CollisionSystem.java
 *  Execution:    java CollisionSystem N               (N random particles)
 *                java CollisionSystem < input.txt     (from a file) 
 *  
 *  Creates N random particles and simulates their motion according
 *  to the laws of elastic collisions.
 *
 *************************************************************************/

import java.awt.Color;
import java.util.Iterator;
import java.util.Arrays;

public class CollisionSystem {
    private MinPQ<Event> pq;        // the priority queue
    private double t  = 0.0;        // simulation clock time
    private double hz = 0.5;        // number of redraw events per clock tick
    private Particle[] particles;   // the array of particles
    private Bag<Double> velocityBag = new Bag<Double>();

    // create a new collision system with the given set of particles
    public CollisionSystem(Particle[] particles) {
        this.particles = particles.clone();   // defensive copy
    }

    // updates priority queue with all new events for particle a
    private void predict(Particle a, double limit) {
        if (a == null) return;

        // particle-particle collisions
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i]));
        }

        // particle-wall collisions
        double dtX = a.timeToHitVerticalWall();
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtX <= limit) pq.insert(new Event(t + dtX, a, null));
        if (t + dtY <= limit) pq.insert(new Event(t + dtY, null, a));
    }

    // redraw all particles
    private void redraw(double limit) {
        double[]
            v = particles[0].velocity();
        StdDraw.clear();
        velocityBag.add(v[0]);
        velocityBag.add(v[1]);
        for (int i = 0; i < particles.length; i++) {
            particles[i].draw();
        }
        StdDraw.show(20);
        if (t < limit) {
            pq.insert(new Event(t + 1.0 / hz, null, null));
        }
    }

      
   /********************************************************************************
    *  Event based simulation for limit seconds
    ********************************************************************************/
    public void simulate(double limit, int option) {
        
        // initialize PQ with collision events and redraw event
        pq = new MinPQ<Event>();
        for (int i = 0; i < particles.length; i++) {
            predict(particles[i], limit);
        }
        pq.insert(new Event(0, null, null));        // redraw event


        // the main event-driven simulation loop
        while (!pq.isEmpty()) { 

            // get impending event, discard if invalidated
            Event e = pq.delMin();
            if (!e.isValid()) continue;
            Particle a = e.a;
            Particle b = e.b;

            // physical collision, so update positions, and then simulation clock
            for (int i = 0; i < particles.length; i++)
                particles[i].move(e.time - t);
            t = e.time;

            // process event
            if      (a != null && b != null) a.bounceOff(b);              // particle-particle collision
            else if (a != null && b == null) a.bounceOffVerticalWall();   // particle-wall collision
            else if (a == null && b != null) b.bounceOffHorizontalWall(); // particle-wall collision
            else if (a == null && b == null) redraw(limit);               // redraw event

            // update the priority queue with new collisions involving a or b
            predict(a, limit);
            predict(b, limit);
        }
        
        // Cria Histogramas e calcula a media e desvio padrao
        int
            count  = 0,
            qx     = 0,
            qy     = 0,
            qmod   = 0,
            k      = 0,
            i      = 0,
            maxX   = 0,
            maxY   = 0,
            maxMod = 0;
        double[]
            vx  = new double[velocityBag.size()/2],
            vy  = new double[velocityBag.size()/2],
            mod = new double[velocityBag.size()/2],
            histVx = new double[velocityBag.size()/2],
            histVy = new double[velocityBag.size()/2],
            histMod = new double[velocityBag.size()/2];

        // Pega os elementos da Bag e armazena nos vetores vx, vy e mod;
        Iterator<Double> j = velocityBag.iterator();
        while(j.hasNext()){
            Double x = j.next();
            Double y = j.next();
            vx[i]  = x;
            vy[i]  = y;
            mod[i] = Math.sqrt(x * x + y * y);
            i++;
        }

        // Ordena os vetores
        Arrays.sort(vx);
        Arrays.sort(vy);
        Arrays.sort(mod);

        // Imprime as medias e desvios padroes
        StdOut.println("Velocidade em x:");
        StdOut.println("\tMedia\t\t--->\t" + StdStats.mean(vx));
        StdOut.println("\tDesvio Padrao\t--->\t" + StdStats.stddev(vx));
        StdOut.println("Velocidade  em y:");
        StdOut.println("\tMedia\t\t--->\t" + StdStats.mean(vy));
        StdOut.println("\tDesvio Padrao\t--->\t" + StdStats.stddev(vy));
        StdOut.println("Modulo da velocidade:");
        StdOut.println("\tMedia\t\t--->\t" + StdStats.mean(mod));
        StdOut.println("\tDesvio Padrao\t--->\t" + StdStats.stddev(mod));

        // Cria os vetores de frequencia
        double v;
        while(k < i){
            do{
                v = vx[k];
                count++;
                k++;
                if(k == i)
                    break;
            }while(v == vx[k]);
            histVx[qx] = count;
            qx++;
            if(count > maxX)
                maxX = count;
            count = 0;
        }
        k = 0;
        while(k < i){
            do{
                v = vy[k];
                count++;
                k++;
                if(k == i)
                    break;
            }while(v == vy[k]);
            histVy[qy] = count;
            qy++;
            if(count > maxY)
                maxY = count;
            count = 0;
        }
        k = 0;
        while(k < i){
            do{
                v = mod[k];
                count++;
                k++;
                if(k == i)
                    break;
            }while(v == mod[k]);
            histMod[qmod] = count;
            qmod++;
            if(count > maxMod)
                maxMod = count;
            count = 0;
        }

        double[] 
            freqX = new double[qx],
            freqY = new double[qy],
            freqMod = new double[qmod];

        for(i = 0; i < qx; i++)
            freqX[i] = histVx[i];
        for(i = 0; i < qy; i++)
            freqY[i] = histVy[i];
        for(i = 0; i < qmod; i++)
            freqMod[i] = histMod[i];

        StdDraw.setCanvasSize(500, 500);
        if(option == 1){
            StdDraw.setYscale(-1, maxX + 20);
            StdStats.plotBars(freqX);
        } else if(option == 2){
            StdDraw.setYscale(-1, maxY + 1);
            StdStats.plotBars(freqY);
        } else{
            StdDraw.setYscale(-1, maxMod + 1);
            StdStats.plotBars(freqMod);
        }
        StdDraw.show();
    }


   /*************************************************************************
    *  An event during a particle collision simulation. Each event contains
    *  the time at which it will occur (assuming no supervening actions)
    *  and the particles a and b involved.
    *
    *    -  a and b both null:      redraw event
    *    -  a null, b not null:     collision with vertical wall
    *    -  a not null, b null:     collision with horizontal wall
    *    -  a and b both not null:  binary collision between a and b
    *
    *************************************************************************/
    private static class Event implements Comparable<Event> {
        private final double time;         // time that event is scheduled to occur
        private final Particle a, b;       // particles involved in event, possibly null
        private final int countA, countB;  // collision counts at event creation
                
        
        // create a new event to occur at time t involving a and b
        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a    = a;
            this.b    = b;
            if (a != null) countA = a.count();
            else           countA = -1;
            if (b != null) countB = b.count();
            else           countB = -1;
        }

        // compare times when two events will occur
        public int compareTo(Event that) {
            if      (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else                            return  0;
        }
        
        // has any collision occurred between when event was created and now?
        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
   
    }



   /********************************************************************************
    *  Sample client
    ********************************************************************************/
    public static void main(String[] args) {

        int option;

        // remove the border
        // StdDraw.setXscale(1.0/22.0, 21.0/22.0);
        // StdDraw.setYscale(1.0/22.0, 21.0/22.0);


        // the array of particles
        Particle[] particles;

        // create N random particles
        if (args.length == 1) {
            int N = Integer.parseInt(args[0]);
            particles = new Particle[N];
            for (int i = 0; i < N; i++) particles[i] = new Particle();
        }

        // or read from standard input
        else {
            int N = StdIn.readInt();
            particles = new Particle[N];
            for (int i = 0; i < N; i++) {
                double rx     = StdIn.readDouble();
                double ry     = StdIn.readDouble();
                double vx     = StdIn.readDouble();
                double vy     = StdIn.readDouble();
                double radius = StdIn.readDouble();
                double mass   = StdIn.readDouble();
                int r         = StdIn.readInt();
                int g         = StdIn.readInt();
                int b         = StdIn.readInt();
                Color color   = new Color(r, g, b);
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }
        }

        // create collision system and simulate
        StdOut.println("\t1 - Histograma para velocidade x;");
        StdOut.println("\t2 - Histograma para velocidade y;");
        StdOut.println("\t3 - Histograma para o modulo da velocidade;");
        StdOut.print("Entre com a opcao (1, 2, 3) desejada: ");
        option = StdIn.readInt();
        StdDraw.setCanvasSize(800, 800);
        // turn on animation mode
        StdDraw.show(0);
        CollisionSystem system = new CollisionSystem(particles);
        system.simulate(3500, option);
        
    }
      
}
