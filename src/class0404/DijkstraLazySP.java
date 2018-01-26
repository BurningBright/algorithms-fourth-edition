package class0404;

import rlgs4.IndexMinPQ;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.39
 *          模仿Prim Lazy MST
 *          拆分放松方法，将检查放入构造，先做入队方法（不检查）
 *          优先队列中的边不一定有效
 * @author Leon
 * @date 2018-01-26 09:30:00
 */
public class DijkstraLazySP {
    
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    private boolean[] marked;
    
    public DijkstraLazySP(EdgeWeightedDigraph G, int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) 
            distTo[v] = Double.POSITIVE_INFINITY;
        
        distTo[s] = 0.0;
        pq.insert(s, 0.0);
        
        visit(G, s);
        
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            
            // origin relax part one
            for (DirectedEdge e : G.adj(v)) {
                int w = e.to();
                if (distTo[w] > distTo[v] + e.weight()) {
                    distTo[w] = distTo[v] + e.weight();
                    edgeTo[w] = e;
                }
            }
            
            visit(G, v);
            
        }
        
    }
    
    private void visit(EdgeWeightedDigraph G, int v) {
        marked[v] = true;
        // origin relax part two
        for (DirectedEdge e : G.adj(v)) {
            // 未访问的节点，入队列
            if (!marked[e.to()]) {
                int w = e.to();
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
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
    
    public static void main(String[] args) {
        DijkstraLazySP sp = new DijkstraLazySP(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")), 0);
        StdOut.println(sp.pathTo(1));
        StdOut.println(sp.pathTo(6));
    }

}
