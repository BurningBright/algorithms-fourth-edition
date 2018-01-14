package class0404;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.19
 *          货币汇率套利
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2018-01-14 21:17:00
 */
public class Arbitrage {
    public static void main(String[] args) {
        
        In in = new In("resource/4.4/rates.txt");
        
        int V = in.readInt();
        String[] name = new String[V];
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        for (int v = 0; v < V; v++) {
            name[v] = in.readString();
            for (int w = 0; w < V; w++) {
                double rate = in.readDouble();
                DirectedEdge e = new DirectedEdge(v, w, -Math.log(rate));
                G.addEdge(e);
            }
        }
        BellmanFordSP spt = new BellmanFordSP(G, 0);
        if (spt.hasNegativeCycle()) {
            double stake = 1000.0;
            for (DirectedEdge e : spt.negativeCycle()) {
                StdOut.printf("%10.5f %s ", stake, name[e.from()]);
                stake *= Math.exp(-e.weight());
                StdOut.printf("= %10.5f %s\n", stake, name[e.to()]);
            }
        } else
            StdOut.println("No arbitrage opportunity");
    }
}
