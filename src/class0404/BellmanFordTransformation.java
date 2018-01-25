package class0404;

import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.30
 *          将带负权的图，转换为不带负权的图
 *          要假设所有路径的权都不为负数？
 *          如果有路径权是负数，得到的图可能会有负权重边，但不影响引导
 *          使用Bellman-Ford将图中的负权向父路径转移
 * @author Leon
 * @date 2018-01-23 18:18:00
 */
public class BellmanFordTransformation {
    
    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed
    private int cost;                       // number of calls to relax()
    private boolean cycle;                  // negative cycle flag
    private double[] pi;                    // offset of nodes
    
    EdgeWeightedDigraph graph;
    
    public BellmanFordTransformation(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        pi = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        while (!queue.isEmpty() && !cycle) {
            int v = queue.dequeue();
            onQ[v] = false;
            relax(G, v);
        }
        
        if (!cycle) {
            graph = new EdgeWeightedDigraph(G.V());
            for (DirectedEdge e: G.edges()) {
                int from = e.from();
                int to = e.to();
                // 新的权重将引导Dijkstra
                graph.addEdge(new DirectedEdge(from, to, 
                        e.weight() + (pi[to] - pi[from])));
            }
        }
    }
    
    private void relax(EdgeWeightedDigraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                
                // 判断是否是负权边，如果是则追溯pi数组
                if (e.weight() < 0) {
                    for (DirectedEdge now = e; now != null; now = edgeTo[now.from()]) {
                        int from = now.from();
                        int to = now.to();
                        //将负权重摊入父路径
                        if (pi[to] - pi[from] + now.weight() < 0) 
                            pi[from] += pi[to] + now.weight();
                    }
                }
                
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }
            
            if (cost++ % G.V() == 0)
                findNegativeCycle();
        }
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
        cycle = cf.cycle() != null? true: false;
    }
    
    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
    
    public EdgeWeightedDigraph getGraph() {
        if (cycle)
            throw new UnsupportedOperationException("Negative cost cycle exists");
        return graph;
    }
    
    public static void main(String[] args) {
        BellmanFordSP sp = new BellmanFordSP(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD4.txt")), 0);
        if (sp.hasNegativeCycle())
            StdOut.println(sp.negativeCycle());
        StdOut.println(sp.pathTo(1));
        
        BellmanFordTransformation trans = new BellmanFordTransformation(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD4.txt")), 0);
        EdgeWeightedDigraph newGraph = trans.getGraph();
        StdOut.println(newGraph);
        DijkstraSP di = new DijkstraSP(newGraph, 0);
        StdOut.println(di.pathTo(1));
    }

}
