package class0401;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * @Description 4.1.26
 *          深度优先探索的分离度
 *          -Xss128M    设置 Java 线程堆栈大小
 * @author Leon
 * @date 2017-11-10 09:15:00
 */
public class DegreesOfSeparationDFS {

    public static void main(String[] args) {
        StdOut.print("Source:");
        String src = StdIn.readLine();
//        String src = "Bacon, Kevin";
        StdOut.print("Query :");
        String qry = StdIn.readLine();
//        String qry = "Kidman, Nicole";
        StdOut.println(src + qry);
        if(src == null || "".equals(src))
            throw new IllegalArgumentException("source error");
        if(qry == null || "".equals(qry))
            throw new IllegalArgumentException("query error");
        
        SymbolGraph sg = new SymbolGraph("resource/4.1/movies.txt", "/");
        DepthFirstPaths df = new DepthFirstPaths(sg.G(), sg.index(src));
        boolean connected = df.hasPathTo(sg.index(qry));
        StdOut.println(connected);
        if(connected) {
            Iterable<Integer> it = df.pathTo(sg.index(qry));
            for(Integer i: it)
                StdOut.println(sg.name(i));
        }
    }

}
