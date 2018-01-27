package class0403;

import java.awt.Color;

import rlgs4.MinPQ;
import rlgs4.Queue;
import rlgs4.UF;
import class0401.StdDraw;

/**
 * @Description 4.3.31
 *          计算权重
 * @author Leon
 * @date 2017-12-20 11:14:00
 */
public class KruskalMST {
    
    private Queue<Edge> mst;
    private double weight;

    public KruskalMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        MinPQ<Edge> pq = new MinPQ<Edge>(G.edges());
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.delMin();                   // Get min weight edge on pq
            int v = e.either(), w = e.other(v);     // and its vertices.
            if (uf.connected(v, w))
                continue;                           // Ignore ineligible edges.
            uf.union(v, w);                         // Merge components.
            mst.enqueue(e);                         // Add edge to mst.
            weight += e.weight();
        }
    }

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
            if (i>mst.size()/4 && !a) {
                StdDraw.save("pic/4.3/mst_kruskal_25.png");
                a = !a;
            } else if (i>mst.size()*2/4 && !b) {
                StdDraw.save("pic/4.3/mst_kruskal_50.png");
                b = !b;
            } else if (i>mst.size()*3/4 && !c) {
                StdDraw.save("pic/4.3/mst_kruskal_75.png");
                c = !c;
            }
            e.show();
            i++;
        }
        StdDraw.save("pic/4.3/mst_kruskal_100.png");
        */
    }
    
}
