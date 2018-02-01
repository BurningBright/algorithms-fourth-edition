package class0404;

import java.util.Arrays;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.0
 *          yen's 优化贝尔曼-福特，使用两DAG表示原图
 *          分别对A-B图做放松，效率能提升 V -> (V+1)/2 ?
 * @author Leon
 * @date 2018-01-14 21:00:00
 */
public class YensOptimize {
    
    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed
    private Queue<Integer> flip;            // next queue to be relax
    private int cost;                       // number of calls to relax()
    private Iterable<DirectedEdge> cycle;   // negative cycle in edgeTo[]?

    public YensOptimize(EdgeWeightedDigraph G, int s) {
        // 升序DAG图
        EdgeWeightedDigraph A = new EdgeWeightedDigraph(G.V());
        // 降序DAG图
        EdgeWeightedDigraph B = new EdgeWeightedDigraph(G.V());
        for (DirectedEdge e: G.edges())
            if (e.from() < e.to())
                A.addEdge(e);
            else
                B.addEdge(e);
        
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
        flip = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
        
        // 辗转A-B寻找SPT
        boolean flag = true;
        while (!queue.isEmpty() && !this.hasNegativeCycle()) {
            int v = queue.dequeue();
            onQ[v] = false;
            if (flag)
                relax(A, v);
            else
                relax(B, v);
            
            // 交换队列，升降序对调
            if (queue.isEmpty() && !flip.isEmpty()) {
                Queue<Integer> tmp = queue;
                queue = flip;
                flip = tmp;
                flag = !flag;
            }
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
                    // 受当前DAG影响的节点
                    flip.enqueue(w);
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
        YensOptimize opt = new YensOptimize(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")),0);
        StdOut.println(Arrays.toString(opt.edgeTo));
    }

}
