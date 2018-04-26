package context01;

import java.awt.Color;

import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;

import stdlib.StdRandom;

/**
 * @Description context01.3
 *              3D粒子
 * @author Leon
 * @date 2018-04-25 16:20:00
 */
public class Particle3D {
    
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    private static final double UNIT = 100;
    
    private double rx, ry, rz;
    private double vx, vy, vz;
    private final double radius;
    private final double mass;
    @SuppressWarnings("unused")
    private final Color color;
    
    private int count;
    
    private Object3D sphere;
    
    public Particle3D() {
        // 模拟动量一致
        rx     = StdRandom.uniform(0.0, UNIT);
        ry     = StdRandom.uniform(0.0, UNIT);
        rz     = StdRandom.uniform(0.0, UNIT);
        vx     = StdRandom.uniform(-1.5, 1.5);
        vy     = StdRandom.uniform(-1.5, 1.5);
        vz     = StdRandom.uniform(-1.5, 1.5);
        radius = StdRandom.uniform(1.0, 5.0);
        mass   = 0.5;
        color  = Color.BLACK;
        sphere = Primitives.getSphere((float)radius*2);
        draw();
    }
    
    /**
     * 
     * @param rx
     *            position x
     * @param ry
     *            position y
     * @param vx
     *            velocity x
     * @param vy
     *            velocity y
     * @param s
     *            radio
     * @param mass
     *            mass
     */
    Particle3D(double rx, double ry, double rz, 
            double vx, double vy, double vz, double radius, double mass) {
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.radius = radius;
        this.mass = mass;
        color  = Color.BLACK;
        sphere = Primitives.getSphere((float)radius*2);
    }
    
    void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
        rz += vz * dt;
    }

    int count() {
        return count;
    }

    public void draw() {
//        sphere.translate(new SimpleVector(rx, -ry, -rz));
        SimpleVector v = sphere.getTranslation();
        sphere.translate(new SimpleVector(rx-v.x, -ry-v.y, -rz-v.z));
    }

    double timeToHit(Particle3D that) {
        if (this == that) return INFINITY;
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dz = that.rz - this.rz;
        
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvz = that.vz - this.vz;
        double dvdr = dx*dvx + dy*dvy + dz*dvz;      // 如果立方米秒量级是正值，最后的时间会是负值
        
        // 非同向(无交点)
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx*dvx + dvy*dvy + dvz*dvz;
        double drdr = dx*dx + dy*dy + dz*dz;
        double sigma = this.radius + that.radius;
        // 二次函数的根需要大于0
        // 平方秒米的平方 >= 整体速度矢量点乘标量 * (当前球心距离 - 碰撞球心距离)
        double d = (dvdr*dvdr) - dvdv * (drdr - sigma*sigma);
        
        // if (drdr < sigma*sigma) StdOut.println("overlapping particles");
        // 非碰撞(交错)
        if (d < 0) return INFINITY;
        return -(dvdr + Math.sqrt(d)) / dvdv;
    }

    double timeToHitHorizontalWall() {
        if      (vx > 0) return (UNIT - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;  
        else             return INFINITY;
    }

    double timeToHitVerticalWall() {
        if      (vy > 0) return (UNIT - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else             return INFINITY;
    }

    double timeToHitFrontBackWall() {
        if      (vz > 0) return (UNIT - rz - radius) / vz;
        else if (vz < 0) return (radius - rz) / vz;
        else             return INFINITY;
    }

    void bounceOff(Particle3D that) {
        double dx  = that.rx - this.rx;             // x轴距离
        double dy  = that.ry - this.ry;             // y轴距离
        double dz  = that.rz - this.rz;             // z轴距离
        
        double dvx = that.vx - this.vx;             // x轴速度差
        double dvy = that.vy - this.vy;             // y轴速度差
        double dvz = that.vz - this.vz;             // z轴速度差
        
        double dvdr = dx*dvx + dy*dvy + dz*dvz;             // dv dot dr    行矢量矩阵 点乘 列矢量矩阵
        double dist = this.radius + that.radius;   // distance between particle centers at collison

        // magnitude of normal force 焦耳?
        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);

        // normal force, and in x and y directions  能量 -> 力
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;
        double fz = magnitude * dz / dist;

        // update velocities according to normal force  力 -> 速度
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        this.vz += fz / this.mass;
        
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;
        that.vz -= fz / that.mass;

        // update collision counts
        this.count++;
        that.count++;
    }

    void bounceOffHorizontalWall() {
        vy = -vy;
        count++;
    }

    void bounceOffVerticalWall() {
        vx = -vx;
        count++;
    }

    void bounceOffFrontBackWall() {
        vz = -vz;
        count++;
    }

    public Object3D getSphere() {
        return sphere;
    }
    
    public String toString() {
        return rx+" " +ry +" "+ rz;
    }
}
