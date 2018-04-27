package context01;

import java.awt.Color;

import class0401.StdDraw;
import stdlib.StdRandom;

/**
 * @Description context01.0
 *              粒子  Momentum/ Impulse/ Elastic collision
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-04-25 14:00:00
 */
public class Particle {
    
    private static final double INFINITY = Double.POSITIVE_INFINITY;
    
    private double rx, ry;
    private double vx, vy;
    private final double radius;
    private final double mass;
    private final Color color;
    
    private int count;
    
    public Particle() {
        // 模拟动量一致
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        vx     = StdRandom.uniform(-0.005, 0.005);
        vy     = StdRandom.uniform(-0.005, 0.005);
        radius = StdRandom.uniform(.01, .05);
        mass   = 0.5;
        color  = Color.BLACK;
    }
    
    public Particle(double momentum) {
        double angle = StdRandom.uniform(0, 1.0) * Math.PI;
        mass = StdRandom.uniform(.01, .05);
        radius = mass;
        double v = momentum / mass;
        vx = v * Math.cos(angle);
        vy = v * Math.sin(angle);
        rx     = StdRandom.uniform(0.0, 1.0);
        ry     = StdRandom.uniform(0.0, 1.0);
        color  = Color.BLACK;
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
    Particle(double rx, double ry, double vx, double vy, double radius, double mass) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.mass = mass;
        color  = Color.BLACK;
    }

    void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(rx, ry, radius);
    }

    void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    int count() {
        return count;
    }

    double timeToHit(Particle that) {
        if (this == that) return INFINITY;
        double dx  = that.rx - this.rx;
        double dy  = that.ry - this.ry;
        double dvx = that.vx - this.vx;
        double dvy = that.vy - this.vy;
        double dvdr = dx*dvx + dy*dvy;      // 如果平方米秒量级是正值，最后的时间会是负值
        
        // 非同向(无交点)
        if (dvdr > 0) return INFINITY;
        double dvdv = dvx*dvx + dvy*dvy;
        double drdr = dx*dx + dy*dy;
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
        if      (vx > 0) return (1.0 - rx - radius) / vx;
        else if (vx < 0) return (radius - rx) / vx;  
        else             return INFINITY;
    }

    double timeToHitVerticalWall() {
        if      (vy > 0) return (1.0 - ry - radius) / vy;
        else if (vy < 0) return (radius - ry) / vy;
        else             return INFINITY;
    }

    void bounceOff(Particle that) {
        double dx  = that.rx - this.rx;             // x轴距离
        double dy  = that.ry - this.ry;             // y轴距离
        double dvx = that.vx - this.vx;             // x轴速度差
        double dvy = that.vy - this.vy;             // y轴速度差
        double dvdr = dx*dvx + dy*dvy;             // dv dot dr    行矢量矩阵 点乘 列矢量矩阵 = 标量 (m^2/s) 平方米秒
        double dist = this.radius + that.radius;   // distance between particle centers at collison

        // magnitude of normal force 焦耳?
        double magnitude = 2 * this.mass * that.mass * dvdr / ((this.mass + that.mass) * dist);

        // normal force, and in x and y directions  能量 -> 力
        double fx = magnitude * dx / dist;
        double fy = magnitude * dy / dist;

        // update velocities according to normal force  力 -> 速度
        this.vx += fx / this.mass;
        this.vy += fy / this.mass;
        that.vx -= fx / that.mass;
        that.vy -= fy / that.mass;

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
    
    void rewind() {
        vx = -vx;
        vy = -vy;
        count = 0;
    }
    
}
