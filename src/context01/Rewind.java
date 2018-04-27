package context01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import class0401.StdDraw;
import rlgs4.Stopwatch;
import stdlib.StdOut;

/**
 * @Description context01.10
 *              碰撞的逆向过程
 * @author Leon
 * @date 2018-04-27 16:00:00
 */
public class Rewind {
    
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private boolean hasLocked = false;
    private CollisionSystem system;

    public Rewind(Particle[] particles) {
        system = new CollisionSystem(particles);
    }
    
    public void begin() {
        try {
            lock.lock();
            while (hasLocked == true) 
                condition.await();
            
            Stopwatch watch = new Stopwatch();
            StdOut.println("A");
            
            system.outSimulateInit(10000, false);
            while(watch.elapsedTime() < 5) 
                system.outSimulateStep(10000, .7);
            
            StdOut.println("B");
            hasLocked = true;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void back() {
        try {
            lock.lock();
            while (hasLocked == false) 
                condition.await();
            
            Stopwatch watch = new Stopwatch();
            StdOut.println("C");
            
            system.outSimulateInit(10000, true);
            while(watch.elapsedTime() < 5) 
                system.outSimulateStep(10000, .7);
            
            StdOut.println("D");
            hasLocked = false;
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public static void main(String[] args) {
        int N = 5;
        StdDraw.enableDoubleBuffering();
        Particle[] particles = new Particle[N];
        for (int i = 0; i < N; i++)
            particles[i] = new Particle(.0001);
        Rewind r = new Rewind(particles);
        
        Thread begin = new Thread(new Runnable() {
            @Override
            public void run() {
                r.begin();
            }
        });
        Thread back = new Thread(new Runnable() {
            @Override
            public void run() {
                r.back();
            }
        });
        back.start();
        begin.start();
    }

}
