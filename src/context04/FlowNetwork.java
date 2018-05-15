package context04;

import rlgs4.Bag;
import stdlib.In;

/**
 * @Description context04.0
 *              无向流图
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-05-15 11:00:00
 */
public class FlowNetwork {
    
    private final int V;
    private int E;
    private Bag<FlowEdge>[] adj;
    
    @SuppressWarnings("unchecked")
    public FlowNetwork(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<FlowEdge>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<FlowEdge>();
    }
    
    public FlowNetwork(In in) {
        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            validateVertex(v);
            validateVertex(w);
            double capacity = in.readDouble();
            addEdge(new FlowEdge(v, w, capacity));
        }
    }
    
    public int V() {
        return V;
    }
    
    public int E() {
        return E;
    }
    
    public void addEdge(FlowEdge e) {
        int v = e.from();
        int w = e.to();
        validateVertex(v);
        validateVertex(w);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
    
    // edges pointing from v
    public Iterable<FlowEdge> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    
    // all edges in this ﬂow network
    public Iterable<FlowEdge> edges() {
        Bag<FlowEdge> list = new Bag<FlowEdge>();
        for (int v = 0; v < V; v++)
            for (FlowEdge e : adj(v)) {
                if (e.to() != v)
                    list.add(e);
            }
        return list;
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V).append(" ").append(E).append("\r\n");
        for (int v = 0; v < V; v++) {
            s.append(v + ":  ");
            for (FlowEdge e : adj[v]) {
                if (e.to() != v) s.append(e + "  ");
            }
            s.append("\r\n");
        }
        return s.toString();
    }
    
}
