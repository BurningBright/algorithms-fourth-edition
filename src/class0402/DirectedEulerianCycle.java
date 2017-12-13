package class0402;

import java.util.Iterator;

import class0401.BreadthFirstPathsDistTo;
import class0401.Graph;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.2.20
 *          有向欧拉环
 * @author Leon
 * @date 2017-12-11 13:40:00
 */
public class DirectedEulerianCycle {
    
    private Stack<Integer> cycle = null;
    
    public DirectedEulerianCycle(Digraph G) {
        // at least one edge
        if (G.E() == 0) return;
        // graph connection
        if (!connectivity(G)) return;
        // Eulerian Cycle in == out
        for (int v=0; v<G.V(); v++) 
            if(G.indegree(v) != G.outdegree(v))
                return;
        
        // get path
        @SuppressWarnings("unchecked")
        Iterator<Integer>[] adj = (Iterator<Integer>[]) new Iterator[G.V()];
        for (int v=0; v<G.V(); v++) 
            adj[v] = G.adj(v).iterator();
        // init
        Stack<Integer> stack = new Stack<Integer>();
        stack.push(nonIsolatedVertex(G));
        
        // greedily add to putative cycle, depth-first search style
        cycle = new Stack<Integer>();
        while(!stack.isEmpty()) {
            int v = stack.pop();
            while(adj[v].hasNext()) {
                stack.push(v);
                v = adj[v].next();
            }
            // no more edges, path in 'stack' pop into 'cycle'
            cycle.push(v);
        }
        
        // last check
        if (cycle.size() != G.E() + 1)
            cycle = null;
    }
    
    private static boolean connectivity(Digraph G) {
        Graph H = new Graph(G.V());
        for(int v=0; v<H.V(); v++)
            for(int w: G.adj(v))
                H.addEdge(v, w);
        
        int s = nonIsolatedVertex(G);
        BreadthFirstPathsDistTo bfs = new BreadthFirstPathsDistTo(H, s);
        for (int v = 0; v < G.V(); v++)
            if (H.degree(v) > 0 && !bfs.hasPathTo(v))
                return false;
        
        return true;
    }
    
    // returns any non-isolated vertex; -1 if no such vertex
    private static int nonIsolatedVertex(Digraph G) {
        for (int v = 0; v < G.V(); v++)
            if (G.outdegree(v) > 0)
                return v;
        return -1;
    }
    
    public static void main(String[] args) {
        DirectedEulerianCycle D = new DirectedEulerianCycle(
                new Digraph(new In("resource/4.2/eulerianCycle.txt")));
        StdOut.println(D.cycle == null);
        if (D.cycle  != null) {
            for(Integer v: D.cycle)
                StdOut.print(v + " ");
        }
    }

}
