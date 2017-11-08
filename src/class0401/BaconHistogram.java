package class0401;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stdlib.StdOut;

/**
 * @Description 4.1.23
 *          电影的离散分布图
 * @author Leon
 * @date 2017-11-08 10:25:00
 */
public class BaconHistogram {

    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph("resource/4.1/movies.txt", "/");
        Graph G = sg.G();
        String source = "Bacon, Kevin";
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }
        int s = sg.index(source);
        BreadthFirstPathsDistTo bfs = new BreadthFirstPathsDistTo(G, s);
        
        int infinity = 0;
        int dists[] = new int[30];
        for (int i=0; i<G.V(); i++) {
            Pattern p = Pattern.compile("(?<=\\()\\d+?(?=\\))");
            Matcher m = p.matcher(sg.name(i));
            // no movies, but performers
            if(m.find()) 
                continue;
            int dist = bfs.distTo(i);
            if(dist == 0 && i == s)
                dists[0]++;
            else if(dist == 0)
                infinity++;
            else
                dists[dist]++;
        }
        
        StdOut.println("infinity" + infinity);
        for(int i=0; i<dists.length; i++)
            StdOut.println(i + "\t"+dists[i]);
    }

}
