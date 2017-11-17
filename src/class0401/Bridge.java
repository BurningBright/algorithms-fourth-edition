package class0401;

import java.util.Arrays;

import stdlib.In;
import stdlib.StdOut;

/******************************************************************************
 *  Compilation:  javac Bridge.java
 *  Execution:    java  Bridge V E
 *  Dependencies: Graph.java GraphGenerator.java
 *
 *  Identifies bridge edges and prints them out. This decomposes
 *  a directed graph into two-edge connected components.
 *  Runs in O(E + V) time.
 *
 *  Key quantity:  low[v] = minimum DFS preorder number of v
 *  and the set of vertices w for which there is a back edge (x, w)
 *  with x a descendant of v and w an ancestor of v.
 *
 *  Note: code assumes no parallel edges, e.g., two parallel edges
 *  would be (incorrectly) identified as bridges.
 *
 ******************************************************************************/

public class Bridge {
    private int bridges;      // number of bridges
    private int cnt;          // counter
    private int[] pre;        // pre[v] = order in which dfs examines v
    private int[] low;        // low[v] = lowest preorder of any vertex connected to v

    public Bridge(Graph G) {
        low = new int[G.V()];
        pre = new int[G.V()];
        for (int v = 0; v < G.V(); v++)
            low[v] = -1;
        for (int v = 0; v < G.V(); v++)
            pre[v] = -1;
        
        for (int v = 0; v < G.V(); v++)
            if (pre[v] == -1)
                dfs(G, v, v);
    }

    public int components() { return bridges + 1; }

    /**
     * @param G - graph data structure
     * @param u - up level searching vertical
     * @param v - current searching vertical
     */
    private void dfs(Graph G, int u, int v) {
        pre[v] = cnt++;
        low[v] = pre[v];
        for (int w : G.adj(v)) {
            // 如果邻接节点未访问，则递归
            if (pre[w] == -1) {
                dfs(G, v, w);
                for(int i=0; i<pre[w]; i++)
                    StdOut.print("\t");
                StdOut.println("dfs(G, "+v+", "+w+")");
                /**
                 * 递归结束后可取到?
                 */
                for(int i=0; i<pre[w]; i++)
                    StdOut.print("\t");
                StdOut.println("-> "+w+ " " + Arrays.toString(low));
                low[v] = Math.min(low[v], low[w]);
                if (low[w] == pre[w]) {
                    for(int i=0; i<pre[w]; i++)
                        StdOut.print("\t");
                    StdOut.println(v + "-" + w + " is a bridge");
                    bridges++;
                }
            }
            /*
             *  如果邻接节点已访问：
             *  当前节点最低值为"当前节点历史最低值"与"邻接探索层数"的较小值
             */
            // update low number - ignore reverse of edge leading to v
            else if (w != u)
                low[v] = Math.min(low[v], pre[w]);
            
        }
    }

    // test client
    public static void main(String[] args) {
//        int V = Integer.parseInt(args[0]);
//        int E = Integer.parseInt(args[1]);
        Graph G = new Graph(new In("resource/4.1/tinyG6.txt"));
        StdOut.println(G);

        Bridge bridge = new Bridge(G);
        StdOut.println("Edge connected components = " + bridge.components());
    }


}

