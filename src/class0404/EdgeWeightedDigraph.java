package class0404;

import rlgs4.Bag;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.0/2
 *          有向加权图
 * @author Leon
 * @date 2018-01-09 14:22:00
 */
public class EdgeWeightedDigraph {
    
    private static final String NEWLINE = System.getProperty("line.separator");
    
    private final int V;                // number of vertices
    private int E;                      // number of edges
    private Bag<DirectedEdge>[] adj;    // adjacency lists

    @SuppressWarnings("unchecked")
    public EdgeWeightedDigraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<DirectedEdge>();
    }

    // See Exercise 4.4.2.
    public EdgeWeightedDigraph(In in) {
        this(in.readInt());
        
        int e = in.readInt();
        for(int i=0; i<e; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            this.addEdge(new DirectedEdge(v, w, weight));
        }
    }
    
    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(DirectedEdge e) {
        adj[e.from()].add(e);
        E++;
    }

    public Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        Bag<DirectedEdge> bag = new Bag<DirectedEdge>();
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj[v])
                bag.add(e);
        return bag;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(V).append("  ").append(E).append(NEWLINE);
        for (int v = 0; v < V; v++)
            for (DirectedEdge e : adj[v])
                sb.append(e.toString()).append(NEWLINE);
        return sb.toString();
    }
    
    public static void main(String[] args) {
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt"));
        StdOut.println(ewd.toString());
    }
    
}
