package class0403;

import rlgs4.IndexMinPQ;

/**
 * @Description 4.3.0
 *          优化后仅维护最小邻接边的Prim算法
 * @author Leon
 * @date 2017-12-19 18:26:00
 */
public class PrimMST {
    private Edge[] edgeTo; // shortest edge from tree vertex
    private double[] distTo; // distTo[w] = edgeTo[w].weight()
    private boolean[] marked; // true if v on tree
    private IndexMinPQ<Double> pq; // eligible crossing edges

    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(G.V());
        distTo[0] = 0.0;
        pq.insert(0, 0.0); // Initialize pq with 0, weight 0.
        while (!pq.isEmpty())
            visit(G, pq.delMin()); // Add closest vertex to tree.
    }

    private void visit(EdgeWeightedGraph G, int v) { // Add v to tree; update
                                                     // data structures.
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])
                continue; // v-w is ineligible.
            if (e.weight() < distTo[w]) { // Edge e is new best connection from
                                          // tree to w.
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
    }
/*
    public Iterable<Edge> edges() {} // See Exercise 4.3.21.

    public double weight() {} // See Exercise 4.3.31.
*/

}
