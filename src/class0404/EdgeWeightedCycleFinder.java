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
    private DirectedEdge[] edgeTo;
    private Stack<DirectedEdge> cycle; // vertices on a cycle (if one exists)
    private boolean[] onStack; // vertices on recursive call stack

    public EdgeWeightedCycleFinder(EdgeWeightedDigraph G) {
        onStack = new boolean[G.V()];
        edgeTo = new DirectedEdge[G.V()];
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
                edgeTo[w] = e;
                dfs(G, w);
            } else if (onStack[w]) {
                cycle = new Stack<DirectedEdge>();
                // 这种循环不对，启示的顺序应该是以w-7开始，这里先入栈v-4
//                for (int x = v; x != w; x = edgeTo[x])
//                    cycle.push(x);
//                cycle.push(w);
//                cycle.push(v);
                
                for (DirectedEdge now = e; ; now = edgeTo[now.from()]) {
                    cycle.push(now);
                    if (now.from() == w) 
                        break;
                }
            }
        }
        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
    
    public static void main(String[] args) {
        EdgeWeightedCycleFinder finder = new EdgeWeightedCycleFinder(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")));
        
        StdOut.println(finder.hasCycle());
        StdOut.println(finder.cycle());
    }

}
