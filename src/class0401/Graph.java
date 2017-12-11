package class0401;

import rlgs4.Bag;
import rlgs4.SET;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.4/5/32
 *          图 - 禁止平行连接、自连接，增加连接判断方法
 *          统计平行连线数
 * @author Leon
 * @date 2017-11-03 11:15:00
 */
public class Graph {
    private final int V; // number of vertices
    private int E; // number of edges
    private Bag<Integer>[] adj; // adjacency lists

    @SuppressWarnings("unchecked")
    public Graph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V]; // Create array of lists.
        for (int v = 0; v < V; v++) // Initialize all lists
            adj[v] = new Bag<Integer>(); // to empty.
    }

    public Graph(In in) {
        this(in.readInt()); // Read V and construct this graph.
        int E = in.readInt(); // Read E.
        for (int i = 0; i < E; i++) { // Add an edge.
            int v = in.readInt(); // Read a vertex,
            int w = in.readInt(); // read another vertex,
            addEdge(v, w); // and add edge connecting them.
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }
    
    public boolean hasEdge(int v, int w) {
        if (v < 0 || v >= V || w < 0 || w >= V) return false;
        if (adj[v] != null) {
            for(Integer i: adj[v])
                if(i == w)
                    return true;
        }
        return false;
    }
    
    public void addEdge(int v, int w) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
        if (w < 0 || w >= V) throw new IndexOutOfBoundsException();
        /*
        if (v == w) throw new IllegalArgumentException("self-loops");
        if (adj[v] != null) {
            for(Integer i: adj[v])
                if(i == w)
                    throw new IllegalArgumentException("parallel edges");
        }
        
        if (v == w)
            return;
        if (adj[v] != null) {
            for(Integer i: adj[v])
                if(i == w)
                    return;
        }*/
        adj[v].add(w); // Add w to v’s list.
        adj[w].add(v); // Add v to w’s list.
        E++;
    }
    
    private void validate(int v) {
        if (v < 0 || v >= V) throw new IndexOutOfBoundsException();
    }
    
    public Iterable<Integer> adj(int v) {
        validate(v);
        return adj[v];
    }
    
    public int degree(int v) {
        validate(v);
        return adj[v].size();
    }
    
    public int parallel() {
        int[] c = new int[V];
        for (int i=0; i<V; i++) {
            SET<Integer> set = new SET<Integer>();
            int pSize = adj[i].size();
            // 剔除自连接
            for (Integer j: adj[i])
                if(j != i)
                    set.add(j);
                else
                    pSize--;
            c[i] = pSize - set.size();
        }
        int count = 0;
        for (int i=0; i<V; i++) {
            count += c[i];
        }
        return count/2;
    }
    
    public static void main(String args[]) {
        Graph g = new Graph(new In("resource/4.1/tinyG5.txt"));
        StdOut.println(g.parallel());
    }
}
