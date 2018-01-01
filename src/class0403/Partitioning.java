package class0403;

import rlgs4.Queue;
import rlgs4.Quick;
import rlgs4.UF;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.42
 *      快排替代 kruskal中的优先队列
 * @author Leon
 * @date 2017-12-31 12:40:00
 */
public class Partitioning {
    
    private Queue<Edge> mst;
    private double weight;

    public Partitioning(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        Edge[] edges = new Edge[G.E()];
        int count = 0;
        for (Edge e: G.edges())
            edges[count++] = e;
        Quick.sort(edges);
        
        int i = 0;
        UF uf = new UF(G.V());
        while (i != G.E() && mst.size() < G.V() - 1) {
            Edge e = edges[i++];
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

    public double weight() {
        return weight;
    }
    
    public static void main(String[] args) {
        Partitioning p = new Partitioning(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        for (Edge e: p.mst)
            StdOut.println(e);
        StdOut.println(p.weight);
    }

}
