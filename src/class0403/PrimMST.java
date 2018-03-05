package class0403;

import java.awt.Color;

import rlgs4.IndexMinPQ;
import rlgs4.Queue;
import class0401.StdDraw;

/**
 * @Description 4.3.21
 *          优化后, 仅维护最小邻接边的Prim算法
 *          每次访问邻接后，可知道下次入树的节点、树枝
 * @author Leon
 * @date 2017-12-19 18:26:00
 */
public class PrimMST {
    
    private Edge[] edgeTo;          // shortest edge from tree vertex
    private double[] distTo;        // distTo[w] = edgeTo[w].weight()
    private boolean[] marked;       // true if v on tree
    private IndexMinPQ<Double> pq;  // eligible crossing edges
    
    private Queue<Edge> mst = new Queue<Edge>();
    private double weight;
    
    public PrimMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[G.V()];
        distTo = new double[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(G.V());
        distTo[0] = 0.0;
        pq.insert(0, 0.0);          // Initialize pq with 0, weight 0.
        while (!pq.isEmpty())
            visit(G, pq.delMin());  // Add closest vertex to tree.
        
    }

    private void visit(EdgeWeightedGraph G, int v) { // Add v to tree; update
                                                     // data structures.
        marked[v] = true;
        
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            if (marked[w])
                continue;                   // v-w is ineligible.
            if (e.weight() < distTo[w]) {   // Edge e is new best connection from
                                            // tree to w.
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
        }
        
        if (!pq.isEmpty()) {
            int next = pq.minIndex();
            mst.enqueue(edgeTo[next]);
            weight += distTo[next];
        }
        
    }

    // See Exercise 4.3.21.
    public Iterable<Edge> edges() {
        return mst;
    }

    // See Exercise 4.3.31.
    public double weight() {
        return weight;
    }

    public void show() {
        StdDraw.setPenColor(Color.BLACK);
        for (Edge e: edges()) {
            StdDraw.pause(10);
            e.show();
        }
        /*
        int i = 0;
        boolean a, b, c;
        a = b = c = false;
        for (Edge e: edges()) {
            if (i>edgeTo.length/4 && !a) {
                StdDraw.save("pic/4.3/mst_prim_25.png");
                a = !a;
            } else if (i>edgeTo.length*2/4 && !b) {
                StdDraw.save("pic/4.3/mst_prim_50.png");
                b = !b;
            } else if (i>edgeTo.length*3/4 && !c) {
                StdDraw.save("pic/4.3/mst_prim_75.png");
                c = !c;
            }
            e.show();
            i++;
        }
        StdDraw.save("pic/4.3/mst_prim_100.png");
        */
    }

}
