package context04;

import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description context04.0
 *              最小路径步数-最大流量算法
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-05-15 11:00:00
 */
public class FordFulkerson {
    
    private boolean[] marked;   // Is s->v path in residual graph?
    private FlowEdge[] edgeTo;  // last edge on shortest s->v path
    private double value;       // current value of maxflow

    // Find maxflow in flow network G from s to t.
    public FordFulkerson(FlowNetwork G, int s, int t) {
        // While there exists an augmenting path, use it. Compute bottleneck capacity.
        while (hasAugmentingPath(G, s, t)) { 
            double bottle = Double.POSITIVE_INFINITY;
            // 寻找增广路径中的最小剩余容量
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            // Augment flow.
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            value += bottle;
        }
    }

    public double value() {
        return value;
    }

    public boolean inCut(int v) {
        return marked[v];
    }

    // 关键方法：遍历剩余网络、记录增广路径
    private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
        marked = new boolean[G.V()];        // Is path to this vertex known?
        edgeTo = new FlowEdge[G.V()];       // last edge on path
        Queue<Integer> q = new Queue<Integer>();
        marked[s] = true;                   // Mark the source
        q.enqueue(s);                       // and put it on the queue.
        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (FlowEdge e : G.adj(v)) {
                int w = e.other(v);
                // For every edge to an unmarked vertex (in residual)
                if (e.residualCapacityTo(w) > 0 && !marked[w]) { 
                    edgeTo[w] = e;      // Save the last edge on a path.
                    marked[w] = true;   // Mark w because a path is known
                    q.enqueue(w);       // and add it to the queue.
                }
            }
        }
        return marked[t];
    }

    public static void main(String[] args) {
        FlowNetwork G = new FlowNetwork(new In("resource/context/tinyFN.txt"));
        int s = 0, t = G.V() - 1;
        StdOut.println(G);
        
        FordFulkerson maxflow = new FordFulkerson(G, s, t);
        StdOut.println("Max flow from " + s + " to " + t);
        for (int v = 0; v < G.V(); v++)
            for (FlowEdge e : G.adj(v))
                if ((v == e.from()) && e.flow() > 0)
                    StdOut.println(" " + e);
        StdOut.println("Max flow value = " + maxflow.value());
    }
    
}
