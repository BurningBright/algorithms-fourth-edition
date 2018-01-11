package class0404;

import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.12
 *          4.2 环寻找的带权版本
 * @author Leon
 * @date 2018-01-11 14:40:00
 */
public class EdgeWeightedCycleFinder {
    
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // vertices on a cycle (if one exists)
    private boolean[] onStack; // vertices on recursive call stack

    public EdgeWeightedCycleFinder(EdgeWeightedDigraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfs(G, v);
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        onStack[v] = true;
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (this.hasCycle())
                return;
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<Integer>();
                for (int x = v; x != w; x = edgeTo[x])
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
    
    public static void main(String[] args) {
        EdgeWeightedCycleFinder finder = new EdgeWeightedCycleFinder(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")));
        
        StdOut.println(finder.hasCycle());
        StdOut.println(finder.cycle());
    }

}
