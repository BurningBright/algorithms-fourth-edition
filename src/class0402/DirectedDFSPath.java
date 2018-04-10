package class0402;

import rlgs4.Stack;

/**
 * @Description 4.4.0
 * @author Leon
 * @date 2018-04-10 11:28:00
 */
public class DirectedDFSPath {
    private boolean[] marked;  // marked[v] = true if v is reachable
                               // from source (or sources)
    private int count;         // number of vertices reachable from s
    private int[][] edgeTo;

    public DirectedDFSPath(Digraph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()][G.V()];
        dfs(G, s, s);
    }

    public DirectedDFSPath(Digraph G, Iterable<Integer> sources) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()][G.V()];
        for (int v : sources) 
            if (!marked[v]) dfs(G, v, v);
    }

    private void dfs(Digraph G, int v, int s) { 
        count++;
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[s][w] = v;
                dfs(G, w, s);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int s, int v) {
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[s][x])
            path.push(x);
        path.push(s);
        return path;
    }
    
    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }

}
