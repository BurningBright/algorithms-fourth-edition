package class0403;

import rlgs4.Bag;
import stdlib.In;

/**
 * @Description 4.3.10
 *          权重图 - 稠密图的矩阵实现
 * @author Leon
 * @date 2017-12-20 16:36:00
 */
public class EdgeWeightedGraphInMatrix {
    
    private final int V;
    private int E;
    private boolean[][] adj;
    private double[][] weight;
    
    public EdgeWeightedGraphInMatrix(int V) {
        this.V = V;
        this.E = 0;
        adj = new boolean[V][V];
        weight = new double[V][V];
    }
    
    public EdgeWeightedGraphInMatrix(In in) {
        
        this(in.readInt());
        int E = in.readInt();
        
        for (int i=0; i<E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            addEdge(new Edge(v, w, weight));
        }
        
    }
    

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v][w] = adj[w][v] = true;
        weight[v][w] = weight[w][v] = e.weight();
        E++;
    }

    public Iterable<Edge> adj(int v) {
        Bag<Edge> bag = new Bag<Edge>();
        for (int w=0; w<V; w++)
            if (adj[v][w])
                bag.add(new Edge(v, w, weight[v][w]));
        return bag;
    }
    
    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj(v))
                if (e.other(v) > v)
                    b.add(e);
        return b;
    }
    
}
