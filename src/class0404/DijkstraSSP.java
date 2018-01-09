package class0404;

import rlgs4.IndexMinPQ;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.7 
 *          Dijkstra 第二最小路径树
 * @author Leon
 * @date 2018-01-09 16:00:00
 */
public class DijkstraSSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    // 记录第二最小路径树
    private DirectedEdge[] edgeSsTo;
    private double[] distSsTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSSP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        edgeSsTo = new DirectedEdge[G.V()];
        distSsTo = new double[G.V()];
        
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            distSsTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
        
        // 为查找第二最小路径的再次放松
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            sRelax(G, pq.delMin());
        }
    }

    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }
    
    private void sRelax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            
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
    
    public double distSsTo(int v) {
        return distSsTo[v];
    }

    public boolean hasPathSsTo(int v) {
        return distSsTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathSsTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        // 向根遍历
        for (DirectedEdge e = edgeSsTo[v]; e != null; e = edgeSsTo[e.from()])
            path.push(e);
        return path;
    }
    
    public static void main(String[] args) {
        DijkstraSSP dj = new DijkstraSSP(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")), 0);
        StdOut.println(dj.distTo(7));
        StdOut.println(dj.pathTo(7));
        StdOut.println(dj.distTo(3));
        StdOut.println(dj.pathTo(3));
        StdOut.println("-----------");
        StdOut.println(dj.distSsTo(7));
        StdOut.println(dj.pathSsTo(7));
        StdOut.println(dj.distSsTo(3));
        StdOut.println(dj.pathSsTo(3));
    }

}
