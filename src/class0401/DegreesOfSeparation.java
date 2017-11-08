package class0401;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 4.1.25
 *          过滤年代？
 *          resource/4.1/movies.txt / "Bacon, Kevin" 20
 * @author Leon
 * @date 2017-11-08 10:36:00
 */
public class DegreesOfSeparation {

    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        Graph G = sg.G();
        String source = args[2];
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }
        int s = sg.index(source);
        BreadthFirstPathsDistTo bfs = new BreadthFirstPathsDistTo(G, s);
        // more than years old movie wille be ignore [movie >= yyyy - years]
        int years = Integer.valueOf(args[3]);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int line = Integer.valueOf(sdf.format(new Date())) - years;
        while (!StdIn.isEmpty()) {
            String sink = StdIn.readLine();
            Pattern p = Pattern.compile("(?<=\\()\\d+?(?=\\))");
            Matcher m = p.matcher(sink);
            if(m.find()) {
                int movie = Integer.valueOf(m.group(0));
                if(movie < line) {
                    StdOut.println("to old this movie:" + sink);
                    continue;
                }
            }
            if (sg.contains(sink)) {
                int t = sg.index(sink);
                if (bfs.hasPathTo(t))
                    for (int v : bfs.pathTo(t))
                        StdOut.println(" " + sg.name(v));
                else
                    StdOut.println("Not connected");
            } else
                StdOut.println("Not in database.");
        }
    }

}
