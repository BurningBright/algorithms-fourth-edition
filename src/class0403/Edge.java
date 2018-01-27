package class0403;

import class0401.StdDraw;

/**
 * @Description 4.3.0
 *          权重边对象
 * @author Leon
 * @date 2017-12-18 16:26:00
 */
public class Edge implements Comparable<Edge> {
    
    private final int v; // one vertex
    private final int w; // the other vertex
    private final double weight; // edge weight
    
    private double vx, vy, wx, wy;
    
    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    public Edge(int v, int w, double weight, 
            double vx, double vy, double wx, double wy) {
        this(v, w, weight);
        this.vx = vx;
        this.vy = vy;
        this.wx = wx;
        this.wy = wy;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        else if (vertex == w)
            return v;
        else
            throw new RuntimeException("Inconsistent edge");
    }

    public int compareTo(Edge that) {
        if (this.weight() < that.weight())
            return -1;
        else if (this.weight() > that.weight())
            return +1;
        else
            return 0;
    }
    
    public void show() {
        StdDraw.line(vx, vy, wx, wy);
    }
    
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}
