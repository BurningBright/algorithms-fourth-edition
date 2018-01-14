package class0404;

import rlgs4.Queue;

/**
 * @Description 4.4.18
 *          无环 - 最长路径
 * @author Leon
 * @date 2018-01-14 21:10:00
 */
public class AcyclicLP {
    
    private AcyclicSP sp;
    
    public AcyclicLP(EdgeWeightedDigraph G, int s) {
        EdgeWeightedDigraph GT = new EdgeWeightedDigraph(G.V());
        for (DirectedEdge e: G.edges())
            GT.addEdge(new DirectedEdge(e.from(), e.to(), -1 * e.weight()));
        
        this.sp = new AcyclicSP(GT, s);
        
    }
    
    public double distTo(int v) {
        return -1 * sp.distTo(v);
    }

    public boolean hasPathTo(int v) {
        return sp.hasPathTo(v);
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        
        if (!hasPathTo(v))
            return null;
        
        Queue<DirectedEdge> path = new Queue<DirectedEdge>();
        for (DirectedEdge e: sp.pathTo(v))
            path.enqueue(new DirectedEdge(e.to(), e.from(), -1 * e.weight()));
        return path;
    }
    
}
