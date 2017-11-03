package class0401;

import rlgs4.UF;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.8
 *          联合查找搜索图实现
 *          resource/4.1/tinyG.txt
 * @author Leon
 * @date 2017-11-02 16:15:00
 */
public class UFSearch {
    
    private int s;
    private UF uf;
    
    UFSearch(Graph G, int s) {
        this.s = s;
        uf = new UF(G.V());
        for (int i=0; i<G.V(); i++) {
            Iterable<Integer> adj = G.adj(i);
            for(Integer a: adj)
                uf.union(i, a);
        }
    }
    
    boolean marked(int v) {
        return uf.connected(s, v);
    }
    
    int count() {
        return uf.count();
    }
    
    public static void main(String args[]) {
        In in = new In(args[0]);
        Graph g = new Graph(in);
        UFSearch us = new UFSearch(g, 4);
        StdOut.println(us.count());
        StdOut.println(us.marked(9));
        StdOut.println(us.marked(0));
    }
    
}
