package class0404;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.8
 *          Dijkstra 有向权重图的直径
 *          所有最短路径中最大的路径为有向图直径
 * @author Leon
 * @date 2018-01-10 15:40:00
 */
public class DiameterByDijkstra {

    public static void main(String[] args) {
        
        double diameter = Double.NEGATIVE_INFINITY;
        
        In in = new In("resource/4.4/tinyEWD.txt");
        int num = in.readInt();
        
        int start=0, dist=0;
        
        for (int s=0; s<num; s++) {
            DijkstraSP dj = new DijkstraSP(
                    new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")), s);
            
            for (int v=0; v<num; v++)
                if (dj.distTo(v) > diameter) {
                    diameter = dj.distTo(v);
                    start = s;
                    dist = v;
                }
            
        }
        
        StdOut.printf("%d -> %d %.3f", start, dist, diameter);
        
    }

}
