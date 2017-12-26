package class0403;

import java.util.Arrays;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.24
 *      反向删除，权重倒序，持续删除。
 *      可以复用最小生成森林
 * @author Leon
 * @date 2017-12-26 18:46:00
 */
public class ReverseDeleteMST {
    
    private double weight;
    private EdgeWeightedGraph mst;
    private Edge[] src;
    
    public ReverseDeleteMST(EdgeWeightedGraph G) {
        mst = new EdgeWeightedGraph(G.V());
        src = new Edge[G.E()];
        int index = 0;
        for (Edge e: G.edges()) {
            src[index++] = e;
            mst.addEdge(e);
        }
        
        Arrays.sort(src);
        
        for (int i=G.E()-1; i>-1; i--) 
            checkComponent(src[i]);
        
        for (Edge e: mst.edges())
            weight += e.weight();
    }
    
    private void checkComponent(Edge e) {
        mst.delEdge(e);
        MSF msf = new MSF(mst);
        if(msf.count()>1)
            mst.addEdge(e);
    }
    
    public Iterable<Edge> edges() {
        return mst.edges();
    }

    public double weight() {
        return weight;
    }
    
    public static void main(String[] args) {
        ReverseDeleteMST mst = new ReverseDeleteMST(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        for (Edge e: mst.edges())
            StdOut.println(e);
        
        StdOut.println(mst.weight());
    }

}
