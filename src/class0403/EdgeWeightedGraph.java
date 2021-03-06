package class0403;

import java.awt.Color;

import rlgs4.Bag;
import stdlib.In;
import class0401.StdDraw;

/**
 * @Description 4.3.9/17
 *          权重图
 * @author Leon
 * @date 2017-12-18 16:26:00
 */
public class EdgeWeightedGraph {
    
    private final int V; // number of vertices
    private int E; // number of edges
    private Bag<Edge>[] adj; // adjacency lists

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Edge>();
    }

    // See Exercise 4.3.9.
    public EdgeWeightedGraph(In in) {
        
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
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Edge delEdge(Edge e) {
        EdgeWeightedGraph G = new EdgeWeightedGraph(V);
        boolean flag = false;
        for (Edge n: edges())
            if (!n.equals(e))
                G.addEdge(n);
            else
                flag = true;
        this.adj = G.adj;
        this.E = G.E;
        return flag? e: null;
    }
    
    public Edge getEdge(int v, int w) {
        for (Edge e: adj(v))
            if (e.other(v) == w)
                return e;
        return null;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }
    
    // See page 609.
    public Iterable<Edge> edges() {
        Bag<Edge> b = new Bag<Edge>();
        for (int v = 0; v < V; v++)
            for (Edge e : adj[v])
                if (e.other(v) > v)
                    b.add(e);
        return b;
    }
    
    public void show() {
        StdDraw.setPenColor(Color.gray);
        for (Edge e: edges())
            e.show();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Edge e: edges())
            sb.append(e.toString()).append("\n");
        return sb.toString();
    }
}