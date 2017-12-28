package class0403;

import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.29
 *          稠密图的最小生成树，增长级在V2
 *          不使用优先队列，改造Prim
 * @author Leon
 * @date 2017-12-28 09:35:00
 */
public class DenseGraphMST {
    
    private Edge[] edgeTo;          // shortest edge from tree vertex
    private double[] distTo;        // distTo[w] = edgeTo[w].weight()
    private boolean[] marked;
    
    // 直接用数组替代优先队列，在类方法里暴露数组操作
    private Double[] vals;
    
    private double weight;
    private Queue<Edge> mst = new Queue<Edge>();
    
    public DenseGraphMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[0] = 0.0;
        
        vals = new Double[G.V()];
        vals[0] = .0;
        
        while(isNotEmpty())
            visit(G, deleMin());
        
    }
    
    private void visit(EdgeWeightedGraph G, int v) { // Add v to tree; update
        // data structures.
        marked[v] = true;
        
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])
                continue; // v-w is ineligible.
            if (e.weight() < distTo[w]) { // Edge e is new best connection from
                // update weight tree to w.
                edgeTo[w] = e;
                distTo[w] = e.weight();
                vals[w] = e.weight();
            }
        }
        
        if (isNotEmpty()) {
            int next = getMin();
            mst.enqueue(edgeTo[next]);
            weight += distTo[next];
        }
        
    }
    
    private boolean isNotEmpty() {
        for (int i=0; i<vals.length; i++)
            if (vals[i] != null)
                return true;
        return false;
    }
    
    // 删除最小权的值，返回下标
    private int deleMin() {
        Double tmp = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i=0; i<vals.length; i++)
            if (vals[i] != null && vals[i] < tmp) {
                tmp = vals[i];
                index = i;
            }
        
        if (index >= 0) {
            vals[index] = null;
        }
        
        return index;
    }
    
    // 返回最小权值的下标
    private int getMin() {
        Double tmp = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i=0; i<vals.length; i++)
            if (vals[i] != null && vals[i] < tmp) {
                tmp = vals[i];
                index = i;
            }
        
        return index;
    }
    
    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
    
    public static void main(String[] args) {
        DenseGraphMST mst = new DenseGraphMST(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        
        for (Edge e: mst.edges())
            StdOut.println(e);
        
        StdOut.println(mst.weight());
    }

}
