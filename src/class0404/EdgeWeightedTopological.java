package class0404;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.12
 *          4.2 拓扑排序顺序
 * @author Leon
 * @date 2018-01-11 17:20:00
 */
public class EdgeWeightedTopological {

    private Iterable<Integer> order; // topological order
    
    public EdgeWeightedTopological(EdgeWeightedDigraph G) {
        EdgeWeightedCycleFinder cyclefinder = new EdgeWeightedCycleFinder(G);
        if (!cyclefinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }
    
    
    public static void main(String[] args) {
        EdgeWeightedTopological tp = new EdgeWeightedTopological(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")));
        StdOut.println(tp.isDAG());
        StdOut.println(tp.order);
        
        StdOut.println("-------------");
        EdgeWeightedTopological tp1 = new EdgeWeightedTopological(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD2.txt")));
        StdOut.println(tp1.isDAG());
        StdOut.println(tp1.order);
        
        StdOut.println("-------------");
        EdgeWeightedTopological tp2 = new EdgeWeightedTopological(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD3.txt")));
        StdOut.println(tp2.isDAG());
        StdOut.println(tp2.order);
    }

}
