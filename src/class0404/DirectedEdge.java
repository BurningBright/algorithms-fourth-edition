package class0404;

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

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
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

    public String toString() {
        return String.format("%d->%d %.2f", v, w, weight);
    }
    
}
