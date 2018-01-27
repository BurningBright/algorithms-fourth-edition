package class0404;

import class0401.StdDraw;

/**
 * @Description 4.4.0
 *          有向加权边
 * @author Leon
 * @date 2018-01-09 14:22:00
 */
public class DirectedEdge {
    
    private final int v;            // edge source
    private final int w;            // edge target
    private final double weight;    // edge weight
    
    private double vx, vy, wx, wy;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }


    public DirectedEdge(int v, int w, double weight, 
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

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }
    
    public void show() {
        StdDraw.line(vx, vy, wx, wy);
    }
    
    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }
    
}
