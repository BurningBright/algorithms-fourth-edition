package class0403;

import rlgs4.Stopwatch;
import stdlib.StdOut;

/**
 * @Description 4.3.38
 *          lazy vs eager
 *          当节点数多 - 联通比例小的时候性能差异不大
 *          当节点不变 - 联通比例高的时候性能差异很大
 *          
 *          lazy-prim vs kruscal
 *          kruscal win 构建时间远好于lazy
 *          
 *          eager-prim vs kruscal
 *          prim win 构建时间慢于eager
 *          
 *          v = 5000 p = .1 .2 .4
 *          lazy    0.772   2.527   9.888
 *          eager   0.082   0.266   0.740
 *          kruscal 0.189   0.615   1.928
 *          
 * @author Leon
 * @date 2017-12-30 20:14:00
 */
public class PrimVersusKruskal {
    
    private static int V = 5000;
    
    public static double lazy(EdgeWeightedGraph G) {
        Stopwatch watch = new Stopwatch();
        new LazyPrimMST(G);
        return watch.elapsedTime();
    }
    
    public static double eager(EdgeWeightedGraph G) {
        Stopwatch watch = new Stopwatch();
        new PrimMST(G);
        return watch.elapsedTime();
    }
    
    public static double kraskal(EdgeWeightedGraph G) {
        Stopwatch watch = new Stopwatch();
        new KruskalMST(G);
        return watch.elapsedTime();
    }
    
    public static void main(String[] args) {
//        StdOut.println(lazy(new EdgeWeightedGraph(new In("resource/4.3/mediumEWG.txt"))));
//        StdOut.println(eager(new EdgeWeightedGraph(new In("resource/4.3/mediumEWG.txt"))));
        
        EdgeWeightedGraph eg = CaseGenerator.euclideanByPercent(V, .4);
        StdOut.println("start");
        StdOut.println(lazy(eg));
        StdOut.println(eager(eg));
        StdOut.println(kraskal(eg));
    }
    
}
