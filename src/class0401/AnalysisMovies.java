package class0401;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 4.1.24 
 *      分析电影
 * @author Leon
 * @date 2017-11-06 10:50:00
 */
public class AnalysisMovies {

    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph("resource/3.5/movies-hero.txt", "/");
        CC cc = new CC(sg.G());
        StdOut.println("component : " + cc.count());
        
        int M = cc.count();
        @SuppressWarnings("unchecked")
        Queue<Integer>[] components = (Queue<Integer>[]) new Queue[M];
        for (int i = 0; i < M; i++) {
            components[i] = new Queue<Integer>();
        }
        for (int v = 0; v < sg.G().V(); v++) {
            components[cc.id(v)].enqueue(v);
        }
        
        int max = -1;
        int mi = -1;
        for (int i = 0; i < M; i++) {
            if(components[i].size() > max) {
                mi = i;
                max = components[i].size();
            }
            StdOut.println("["+i+"]"+components[i].size());
        }
        StdOut.println("max " + mi + " : " + max);
        
        StdOut.println("--------------------------");
        
        GraphProperties gp = new GraphProperties(sg.G(), 0);
        StdOut.println("eccentricity : " + gp.eccentricity(0));
        StdOut.println("diameter : " + gp.diameter());
        StdOut.println("radius : " + gp.radius());
        StdOut.println("center : " + gp.center());
        StdOut.println("girth : " + gp.girth());
    }

}
