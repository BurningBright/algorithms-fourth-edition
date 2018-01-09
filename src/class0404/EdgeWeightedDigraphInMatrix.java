package class0404;

import rlgs4.Bag;
import stdlib.In;

/**
 * @Description 4.4.3
 *          有向加权图 - 矩阵稠密实现
 * @author Leon
 * @date 2018-01-09 15:10:00
 */
public class EdgeWeightedDigraphInMatrix {

    private final int V;
    private int E;
    private boolean[][] adj;
    private double[][] weight;
    
    public EdgeWeightedDigraphInMatrix(int V) {
        this.V = V;
        this.E = 0;
        adj = new boolean[V][V];
        weight = new double[V][V];
    }
    
    public EdgeWeightedDigraphInMatrix(In in) {
        
        this(in.readInt());
        int E = in.readInt();
        
        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new DirectedEdge(v, w, weight));
        }
        
    }
    

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from(), w = e.to();
        adj[v][w] = true;
        weight[v][w] = e.weight();
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int w=0; w<V; w++)
            if (adj[v][w])
                bag.add(new DirectedEdge(v, w, weight[v][w]));
        return bag;
    }
    
    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> b = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj(v))
                    b.add(e);
        return b;
    }
    
}
