package context01;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description context01.2
 *              多粒子碰撞，模拟台球碰撞
 *              实现太复杂，将多粒子作为一个整体，计算所蕴含的能量
 *              将能量转换为力施加到各个粒子，计算粒子各纬度的对应速度
 * @author Leon
 * @date 2018-04-27 13:30:00
 */
public class MultiCollision {
    
    public static Particle3D[] read(In in) {
        int N = in.readInt();
        Particle3D[] particles = new Particle3D[N];
        for (int i = 0; i < N; i++) {
            double rx, ry, rz;
            double vx, vy, vz;
            double mass, radius;
            rx = in.readDouble();
            ry = in.readDouble();
            rz = in.readDouble();
            vx = in.readDouble();
            vy = in.readDouble();
            vz = in.readDouble();
            mass = in.readDouble();
            radius = in.readDouble();
            particles[i] = new Particle3D(rx, ry, rz, vx, vy, vz, mass, radius);
            StdOut.println(particles[i]);
        }
        return particles;
    }
    
    public static void main(String[] args) {
        Particle3D[] data = read(new In("resource/context/multi_collision.txt"));
        CollisionSystem3D system = new CollisionSystem3D(data);
        system.simulate(10000, .7);
    }
}
