package class0404;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.32
 *          启发式父节点检查
 *          
 *            ↗ 1 → 3 ↘ 
 *          0     ↙     5
 *            ↘ 2 → 4 ↗ 
 *          
 *          如果队列中出现了当前队头的父节点，则说明到当前v节点的路径可能出现变动（其它路径插入）
 *          与其检查一遍后还要从父节点再来一遍，不如检查后直接跳过队头，队列里的父节点会将当前v重新加队列
 * @author Leon
 * @date 2018-01-26 13:30:00
 */
public class ParentCheckingHeuristic {

    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed
    private int cost;                       // number of calls to relax()
    private Iterable<DirectedEdge> cycle;   // negative cycle in edgeTo[]?

    public ParentCheckingHeuristic(EdgeWeightedDigraph G, int s) {
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
            
            StdOut.println(v + "  " + queue.toString());
            
            // ? 启发式检查，仅当v的父节点不在队列中时，放松v的邻接边
            DirectedEdge e = edgeTo[v];
            if (e == null || !onQ[e.from()]) {
                onQ[v] = false;
                relax(G, v);
            }
            
            if (e != null && onQ[e.from()])
                StdOut.println(e);
            
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
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
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

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        ParentCheckingHeuristic sp = new ParentCheckingHeuristic(
                new EdgeWeightedDigraph(new In("resource/4.4/heuristic.txt")), 0);
    }

}
