package class0404;

import rlgs4.Queue;
import rlgs4.Stack;
/**
 * @Description 4.4.0
 *          贝尔曼-福特 最短路径
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-01-14 21:00:00
 */
public class BellmanFordSP {
    
    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed
    private int cost;                       // number of calls to relax()
    private Iterable<DirectedEdge> cycle;   // negative cycle in edgeTo[]?

    public BellmanFordSP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !this.hasNegativeCycle()) {
            int v = queue.dequeue();
            onQ[v] = false;
            relax(G, v);
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }
            
            if (cost++ % G.V() == 0)
                findNegativeCycle();
        }
    }
    
    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        // 向根遍历
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }

    private void findNegativeCycle() {
        int V = edgeTo.length;
        EdgeWeightedDigraph spt;
        spt = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++)
            if (edgeTo[v] != null)
                spt.addEdge(edgeTo[v]);
        EdgeWeightedCycleFinder cf;
        cf = new EdgeWeightedCycleFinder(spt);
        cycle = cf.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }
    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

}
