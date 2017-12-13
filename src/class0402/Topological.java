package class0402;
/**
 * @Description 4.2.0
 *          拓扑排序顺序
 * @author XXX
 * @date 2017-12-13 10:50:00
 */
public class Topological {
    private Iterable<Integer> order; // topological order
    private boolean unique;         // is unique or not
    
    public Topological(Digraph G) {
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (!cyclefinder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
            
            HamiltonianPath hp = new HamiltonianPath(G);
            unique = hp.havePath();
        }
    }

    public Iterable<Integer> order() {
        return order;
    }

    public boolean isDAG() {
        return order != null;
    }
    
    public boolean isUnique() {
        return unique;
    }
}
