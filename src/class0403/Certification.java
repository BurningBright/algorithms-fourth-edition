package class0403;

import rlgs4.Bag;
import rlgs4.SET;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.33
 *          检查一组边是一个权重图的最小生成树
 * @author Leon
 * @date 2017-12-28 09:35:00
 */
public class Certification {
    
    public static boolean check(EdgeWeightedGraph G, Iterable<Edge> E) {
        
        MST mst = new MST(G);
        SET<Edge> set = new SET<Edge>();
        
        for (Edge e: mst.edges())
            set.add(e);
        
        for (Edge e: E)
            if (!set.contains(e))
                return false;
        
        return true;
    }
    
    public static void main(String[] args) {
        Bag<Edge> e = new Bag<Edge>();
        e.add(new Edge(0, 7, 0.16));
        e.add(new Edge(1, 7, 0.19));
        e.add(new Edge(0, 2, 0.26));
        e.add(new Edge(2, 3, 0.17));
        e.add(new Edge(5, 7, 0.28));
        e.add(new Edge(4, 5, 0.35));
        e.add(new Edge(6, 2, 0.40));
        StdOut.println(check(new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")), e));
    }

}
