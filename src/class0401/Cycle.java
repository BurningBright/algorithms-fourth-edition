package class0401;

import rlgs4.SET;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.29
 *          排除自连、平行连接的环
 *          和自身相同的邻接节点为自连接
 *          重复邻接历史的节点为平行环
 * @author Leon
 * @date 2017-11-10 09:32:00
 */
public class Cycle {
    
    private boolean[] marked;
    private boolean hasCycle;

    public Cycle(Graph G) {
        marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s])
                dfs(G, s, s, true);
    }

    private void dfs(Graph G, int v, int u, boolean f) {
        marked[v] = true;
        
        SET<Integer> prv = new SET<Integer>();
        for (int w : G.adj(v)) {
            
            if (w == v) {
                StdOut.println("self loop");
                continue;
            }
            
            if(prv.contains(w)) {
                StdOut.println("parallel edges");
                continue;
            } else
                prv.add(w);
            
            if (!marked[w])
                dfs(G, w, v, false);
            else if (w != u)
                hasCycle = true;
            
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public static void main(String[] args) {
        Cycle c = new Cycle(new Graph(new In("resource/4.1/tinyG3.txt")));
        StdOut.println(c.hasCycle);
    }
    
}