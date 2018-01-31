package class0404;

import java.awt.Color;

import class0104.Adjustable2DChart;
import rlgs4.SET;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 4.4.54
 *          懒惰的代价
 *          V-节点  S-连接数  eager  lazy
 *          V 10000 S 128   0.074   0.129
 *          V 10000 S 256   0.199   0.28
 *          V 10000 S 512   0.396   0.620
 *          V 10000 S 1024  0.765   1.18
 * @author Leon
 * @date 2018-01-31 21:10:00
 */
public class CostOfLaziness {
    
    private static int T = 3;
    
    private static EdgeWeightedDigraph generator(int V, int S) {
        EdgeWeightedDigraph ewd = new EdgeWeightedDigraph(V);
        for (int v=0; v<V; v++) {
            SET<Integer> set = new SET<Integer>();
            for (int i=0; i<S; i++) {
                // 防止重复，保证无平行有向边
                int w = StdRandom.uniform(V);
                while(v == w || set.contains(w))
                    w = StdRandom.uniform(V);
                set.add(w);
                ewd.addEdge(new DirectedEdge(v, w, StdRandom.uniform()));
            }
        }
        return ewd;
    }
    
    public static double[] graphDoubling(int V, int S) {
        double[] marks = new double[2];
        
        Stopwatch sw = null;
        for (int i=0; i<T; i++) {
            EdgeWeightedDigraph ewd = generator(V, S);
            sw = new Stopwatch();
            new DijkstraSP(ewd, 0);
            marks[0] += sw.elapsedTime();
        }
        marks[0] /= T;
        
        for (int i=0; i<T; i++) {
            EdgeWeightedDigraph ewd = generator(V, S);
            sw = new Stopwatch();
            new DijkstraLazySP(ewd, 0);
            marks[1] += sw.elapsedTime();
        }
        marks[1] /= T;
        
        return marks;
    }
    
    public static void main(String[] args) {
        Adjustable2DChart a2d = new Adjustable2DChart(0.1, 0.1, 0, 0);
        a2d.setAxisDescDistanceChart(-.3);
        a2d.setAxisDescDistanceY(.07);
        a2d.setChartDesc("Cost of lazy Dijkstra");
        a2d.setAxisXDesc("edges number N");
        a2d.setAxisYDesc("running time T(N)");
        a2d.setColorForChar(Color.RED);
        
        int V = 10000;
        int S = 128;
        for (int s = S; s < 1025; s *= 2) {
            double[] marks = graphDoubling(V, s);
            StdOut.println(s+" \t "+marks[0]+" \t "+marks[1]);
            
            a2d.addChartData((double)s, marks[0]);
            a2d.addAxisDataX((double)s, s+"");
            a2d.addChartData(false, true, (double)s, marks[1]);
            
            a2d.reDraw();
        }
        
    }

}
