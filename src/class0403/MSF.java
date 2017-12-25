package class0403;

import java.util.Arrays;

import rlgs4.IndexMinPQ;
import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.22
 *      Minimum spanning forest
 * @author Leon
 * @date 2017-12-25 15:46:00
 */
public class MSF {
    
    private boolean[] marked;
    private int[] id;
    private int[] size;
    private int count;
    private Queue<Edge>[] msf;
    
    private Edge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    
    @SuppressWarnings("unchecked")
    public MSF(EdgeWeightedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        size = new int[G.V()];
        msf = (Queue<Edge>[])new Queue[G.V()];
        
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        
        for (int v = 0; v < G.V(); v++) {
            if (msf[count] == null)
                msf[count] = new Queue<Edge>();
            
            if (!marked[v]) {
                prim(G, v);
                count++;
            }
        }
        
        for (int v=0; v<G.V(); v++) 
            size[v] = msf[id[v]].size();
    }
    
    private void prim(EdgeWeightedGraph G, int vv) {
        // init component searching
        for (int i=0; i<G.V(); i++)
            distTo[i] = Double.POSITIVE_INFINITY;
        pq.insert(vv, .0);
        distTo[vv] = 0.0;
        id[vv] = count;
        
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            marked[v] = true;
            
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (marked[w])
                    continue;                   // v-w is ineligible.
                
                if (e.weight() < distTo[w]) {   // Edge e is new best connection from
                                                // tree to w.
                    edgeTo[w] = e;
                    distTo[w] = e.weight();
                    id[w] = count;
                    if (pq.contains(w))
                        pq.changeKey(w, distTo[w]);
                    else
                        pq.insert(w, distTo[w]);
                }
            }
            
            if(!pq.isEmpty()) 
                msf[count].enqueue(edgeTo[pq.minIndex()]);
            
        }
    }
    
    boolean connected(int v, int w) {
        return id[v] == id[w];
    }
    
    public int count() {
        return count;
    }
    
    public int id(int v) {
        return id[v];
    }
    
    public Iterable<Edge> edges(int c) {
        return msf[c];
    }

    public double weight(int c) {
        double w = 0;
        for (Edge e: edges(c)) 
            w += e.weight();
        return w;
    }
    
    public static void main(String[] args) {
        MSF msf = new MSF(new EdgeWeightedGraph(new In("resource/4.3/msf.txt")));
        StdOut.println(msf.count());
        StdOut.println(Arrays.toString(msf.id));
        StdOut.println(Arrays.toString(msf.size));
        StdOut.println(msf.edges(0));
        StdOut.println(msf.edges(1));
        StdOut.println(msf.weight(0));
        StdOut.println(msf.weight(1));
    }
    
}
