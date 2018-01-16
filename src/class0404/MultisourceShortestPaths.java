package class0404;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.24
 *          Dijkstra 多源最短路径
 *          两种方式，只是封装的级别不一样罢了
 * @author 
 * @date 2018-01-10 15:40:00
 */
public class MultisourceShortestPaths {
    
    DijkstraSP[] dj;
    
    public MultisourceShortestPaths(EdgeWeightedDigraph G) {
        dj = new DijkstraSP[G.V()];
        for (int v=0; v<G.V(); v++) 
            dj[v] = new DijkstraSP(G, v);
    }
    
    public double distTo(int v, int w) {
        return dj[v].distTo(w);
    }

    public boolean hasPathTo(int v, int w) {
        return dj[v].hasPathTo(w);
    }

    public Iterable<DirectedEdge> pathTo(int v, int w) {
        if (!hasPathTo(v, w))
            return null;
        return dj[v].pathTo(w);
    }
    
    public static void main(String[] args) {
        MultisourceShortestPaths msp = new MultisourceShortestPaths(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")));
        StdOut.println(msp.pathTo(0, 3));
        StdOut.println("-------");
        StdOut.println(msp.pathTo(3, 7));
    }
    
}
