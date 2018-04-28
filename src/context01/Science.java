package context01;

import class0401.StdDraw;
import stdlib.In;

/**
 * @Description context01.6
 *             科学碰撞 - 布朗运动
 * @author Leon
 * @date 2018-04-25 14:00:00
 */
public class Science {

    public static Particle[] read(In in) {
        int N = in.readInt();
        Particle[] particles = new Particle[N];
        for (int i = 0; i < N; i++) {
            double rx, ry;
            double vx, vy;
            double mass, radius;
            int r, g, b;
            rx = in.readDouble();
            ry = in.readDouble();
            vx = in.readDouble();
            vy = in.readDouble();
            mass = in.readDouble();
            radius = in.readDouble();
            r = in.readInt();
            g = in.readInt();
            b = in.readInt();
            particles[i] = new Particle(rx, ry, vx, vy, mass, radius, r, g, b);
        }
        return particles;
    }
    
    public static void main(String[] args) {
        StdDraw.enableDoubleBuffering();
        // one tiny particle sandwiched between two big particles 挤压
//        Particle[] data = read(new In("resource/context/squeeze.txt"));
//        Particle[] data = read(new In("resource/context/squeeze2.txt"));
        
        // pendulum effect 钟摆 - 传递
//        Particle[] data = read(new In("resource/context/pendulum.txt"));
        
        // diffusion of particles from one side of slit 扩散
        Particle[] data = read(new In("resource/context/diffusion.txt"));
//        Particle[] data = read(new In("resource/context/diffusion2.txt"));
//        Particle[] data = read(new In("resource/context/diffusion3.txt"));
        
        // cue ball striking pyramid of 3/ 10/ 15 balls 台球
//        Particle[] data = read(new In("resource/context/billiards2.txt"));
//        Particle[] data = read(new In("resource/context/billiards4.txt"));
//        Particle[] data = read(new In("resource/context/billiards5.txt"));
        
//      Particle[] data = read(new In("resource/context/p10.txt"));
//      Particle[] data = read(new In("resource/context/p2000.txt"));
        
        // 温度
//      Particle[] data = read(new In("resource/context/p100-.125K.txt"));
//      Particle[] data = read(new In("resource/context/p100-.5K.txt"));
//      Particle[] data = read(new In("resource/context/p1000-.5K.txt"));
//      Particle[] data = read(new In("resource/context/p1000-2K.txt"));
//      Particle[] data = read(new In("resource/context/p100-2K.txt"));
        
        // 反弹
//      Particle[] data = read(new In("resource/context/wallbouncing.txt"));
//      Particle[] data = read(new In("resource/context/wallbouncing2.txt"));
//      Particle[] data = read(new In("resource/context/wallbouncing3.txt"));
        
        // 对撞
//      Particle[] data = read(new In("resource/context/diagonal.txt"));
//      Particle[] data = read(new In("resource/context/diagonal1.txt"));
//      Particle[] data = read(new In("resource/context/diagonal2.txt"));
        
        CollisionSystem system = new CollisionSystem(data);
        system.simulate(10000, .7);
    }
    
}
