package context01;

import rlgs4.MinPQ;

/**
 * @Description context01.3
 *              3D粒子 - 碰撞模拟
 * @author Leon
 * @date 2018-04-26 14:48:00
 */
public class CollisionSystem3D {
    
    private class Event implements Comparable<Event> {
        private final double time;
        private final Particle3D a, b, c;
        private final int countA, countB;
        
        // Create a new event to occur at time t involving a and b.
        public Event(double t, Particle3D a, Particle3D b, Particle3D c) { 
            this.time = t;
            this.a = a;
            this.b = b;
            this.c = c;
            
            if (a != null) countA = a.count();
            else countA = -1;
            
            if (b != null) countB = b.count();
            else countB = -1;
        }

        public int compareTo(Event that) {
            if (this.time < that.time) return -1;
            else if (this.time > that.time) return +1;
            else return 0;
        }

        public boolean isValid() {
            if (a != null && a.count() != countA) return false;
            if (b != null && b.count() != countB) return false;
            return true;
        }
    }

    private MinPQ<Event> pq;        // the priority queue
    private double t = 0.0;         // simulation clock time
    private Particle3D[] particles;   // the array of Particle3Ds

    public CollisionSystem3D(Particle3D[] particles) {
        this.particles = particles;
        for (int i = 0; i < particles.length; i++) 
            CollisionDraw.addSphere(particles[i]);
        CollisionDraw.build();
    }

    private void predictCollisions(Particle3D a, double limit) {
        if (a == null)
            return;
        // Put collision with Particle3Ds[i] on pq.
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (t + dt <= limit)
                pq.insert(new Event(t + dt, a, particles[i], null));
        }
        double dtX = a.timeToHitVerticalWall();
        if (t + dtX <= limit)
            pq.insert(new Event(t + dtX, a, null, null));
        double dtY = a.timeToHitHorizontalWall();
        if (t + dtY <= limit)
            pq.insert(new Event(t + dtY, null, a, null));
        double dtZ = a.timeToHitFrontBackWall();
        if (t + dtZ <= limit)
            pq.insert(new Event(t + dtZ, null, null, a));
        
    }

    // Redraw event: redraw all Particle3Ds.
    public void redraw(double limit, double Hz) {
        CollisionDraw.clear();
        for (int i = 0; i < particles.length; i++)
            particles[i].draw();
        CollisionDraw.pause(100);
        CollisionDraw.show();
        if (t < limit)
            pq.insert(new Event(t + 1.0 / Hz, null, null, null));
    }

    public void simulate(double limit, double Hz) {
        pq = new MinPQ<Event>();
        for (int i = 0; i < particles.length; i++) 
            predictCollisions(particles[i], limit);
            
        pq.insert(new Event(0, null, null, null)); // Add redraw event.
        while (!pq.isEmpty()) {             // Process one event to drive simulation.
            Event event = pq.delMin();
            if (!event.isValid())
                continue;
            for (int i = 0; i < particles.length; i++) {
                particles[i].move(event.time - t);  // Update Particle3D positions
//                if (i == 0 )
//                    StdOut.println(particles[i].toString());
            }
            t = event.time;                         // and time.
            Particle3D a = event.a, b = event.b, c = event.c;
            
            if (a != null && b != null)
                a.bounceOff(b);
            else if (a != null && b == null)
                a.bounceOffHorizontalWall();
            else if (a == null && b != null)
                b.bounceOffVerticalWall();
            else if (c != null)
                c.bounceOffFrontBackWall();
            else if (a == null && b == null)
                redraw(limit, Hz);
            
            /*
            if (a != null && b != null)
                a.bounceOff(b);
            else if(event.type != null && 1 == event.type)
                a.bounceOffVerticalWall();
            else if(event.type != null && 2 == event.type)
                a.bounceOffHorizontalWall();
            else if (a == null && b == null)
                redraw(limit, Hz);
            */
//            else if(Type.FrontBack == event.type)
//                a.bounceOffFrontBackWall();
            
            predictCollisions(a, limit);
            predictCollisions(b, limit);
            predictCollisions(c, limit);
        }
    }

    public static void main(String[] args) {
        int N = 15;
        Particle3D[] particles = new Particle3D[N];
        for (int i = 0; i < N; i++)
            particles[i] = new Particle3D();
        CollisionSystem3D system = new CollisionSystem3D(particles);
        system.simulate(10000, .5);
    }

}
