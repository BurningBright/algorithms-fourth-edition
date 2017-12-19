package class0403;

import rlgs4.MinPQ;
import rlgs4.Queue;

/**
 * @Description 4.3.31 MST weights
 *      未优化Prim's 最小生成树，不合格边会进入优先队列
 *      ps. 28行将过滤不合格边
 * @author Leon
 * @date 2017-12-19 15:47:00
 */
public class LazyPrimMST {
    
    private double weight;
    private boolean[] marked;   // MST vertices
    private Queue<Edge> mst;    // MST edges
    private MinPQ<Edge> pq;     // crossing (and ineligible) edges

    public LazyPrimMST(EdgeWeightedGraph G) {
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<Edge>();
        visit(G, 0);             // assumes G is connected (see Exercise 4.3.22)
        while (!pq.isEmpty()) {
            Edge e = pq.delMin(); // Get lowest-weight
            int v = e.either(), w = e.other(v); // edge from pq.
            if (marked[v] && marked[w])
                continue;       // Skip if ineligible.
            mst.enqueue(e);     // Add edge to tree.
            weight += e.weight();
            if (!marked[v])
                visit(G, v);    // Add vertex to tree
            if (!marked[w])
                visit(G, w);    // (either v or w).
        }
    }

    private void visit(EdgeWeightedGraph G, int v) { // Mark v and add to pq all
                                                     // edges from v to unmarked
                                                     // vertices.
        marked[v] = true;
        for (Edge e : G.adj(v))
            if (!marked[e.other(v)])
                pq.insert(e);
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }

}
