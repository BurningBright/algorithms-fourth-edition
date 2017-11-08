package class0401;

import java.util.regex.Pattern;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.22
 *          符号图，寻找奥斯卡候选人
 *          大多为 4 / 6
 * @author Leon
 * @date 2017-11-07 10:55:00
 */
public class OscarNominees {
    
    @SuppressWarnings("unused")
    private static void nameTranslate() {
        /*
        String a = "Gosling, Ryan (I)";
        Pattern p = Pattern.compile("(?i).*\\bryan\\b.*");
        Matcher m = p.matcher(a);
        StdOut.println(m.find());
        StdOut.println(m.matches());
        */
        
        SymbolGraph sg = new SymbolGraph("resource/4.1/movies.txt", "/");
        Graph g = sg.G();
        In in = new In("resource/4.1/2017-89th-oscar-lalaland.txt");
        int row = 0;
        while (in.hasNextLine()) {
            row++;
            String tmp = in.readLine();
            String[] a = tmp.split(" ");
            for(int i=0; i<g.V(); i++) {
                boolean flag = false;
                for (int j=0, k=0; j<a.length; j++) {
                    Pattern p = Pattern.compile("(?i)\\b" + a[j]+"\\b");
                    if(p.matcher(sg.name(i)).find())
                        k++;
                    if(k>=2) {
                        flag = true;
                        StdOut.println(row + " " + tmp);
//                        StdOut.println(sg.name(i));
                        break;
                    }
                }
                if(flag)
                    break;
            }
        }
    }
    
    public static void main(String[] args) {
        In in = new In("resource/4.1/2017-89th-oscar-nominees-copy.txt");
        SymbolGraph sg = new SymbolGraph("resource/4.1/movies.txt", "/");
        Graph G = sg.G();
        String source = "Bacon, Kevin";
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }
        int s = sg.index(source);
        BreadthFirstPathsDistTo bfs = new BreadthFirstPathsDistTo(G, s);
        
        while (in.hasNextLine()) {
            String sink = in.readLine();
            if (sg.contains(sink)) {
                int t = sg.index(sink);
                if(bfs.hasPathTo(t))
                    StdOut.println(sink + "\t\t" + bfs.distTo(t));
                else
                    StdOut.println("Not connected");
                /*
                if (bfs.hasPathTo(t))
                    for (int v : bfs.pathTo(t))
                        StdOut.println(" " + sg.name(v));
                else
                    StdOut.println("Not connected");
                */
            } else
                StdOut.println("Not in database.");
        }
    }

}
