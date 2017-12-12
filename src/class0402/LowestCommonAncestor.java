package class0402;


import rlgs4.SET;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.2.21/22
 *          最小公共祖先 LCA / 路径长度
 * @author Leon
 * @date 2017-12-12 14:00:00
 */
public class LowestCommonAncestor {
    
    private Digraph G;
    private int V;
    private boolean[] marked;
    
    public LowestCommonAncestor(Digraph G) {
        this.G= G;
        this.V = G.V();
    }
    
    public int LCA(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        SET<Integer>[] pv = bfs(v);
        SET<Integer>[] pw = bfs(w);
        for (int i=1; i<V && pv[i] != null; i++) {
            int ret = checkAncestor(pw, pv[i]);
            if(ret >= 0)
                return ret;
        }
        return -1;
    }
    
    public int SAP(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        SET<Integer>[] pv = bfs(v);
        SET<Integer>[] pw = bfs(w);
        for (int i=1; i<V && pv[i] != null; i++) {
            int ret = checkAncestorPath(pw, pv[i]);
            if(ret >= 0)
                return i + ret;
        }
        return -1;
    }
    
    private int checkAncestor(SET<Integer>[] src, Iterable<Integer> sample) {
        for (Integer i: sample) 
            for (int j=1; j<V && src[j] != null ; j++)
                if(src[j].contains(i))
                    return i;
        return -1;
    }
    
    private int checkAncestorPath(SET<Integer>[] src, Iterable<Integer> sample) {
        for (Integer i: sample) 
            for (int j=1; j<V && src[j] != null ; j++)
                if(src[j].contains(i))
                    return j;
        return -1;
    }
    
    private SET<Integer>[] bfs(int s) {
        marked = new boolean[V];
        @SuppressWarnings("unchecked")
        SET<Integer>[] paths = (SET<Integer>[])new SET[V];
        SET<Integer> set = new SET<Integer>();
        set.add(s);
        paths[0] = set;
        int i = 0;
        while (paths[i] != null) {
            SET<Integer> tmp = new SET<Integer>();
            if (paths[i].iterator().hasNext()) {
                Integer cur = paths[i].iterator().next();
                for (int j: G.adj(cur))
                    if (!marked[j])
                        tmp.add(j);
            }
            i++;
            if (tmp.size() > 0)
                paths[i] = tmp;
        }
        return paths;
    }
    
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    
    public static void main(String[] args) {
        LowestCommonAncestor lca = new LowestCommonAncestor(
                new Digraph(new In("resource/4.2/tinyDAG.txt")));
        StdOut.println(lca.LCA(2, 7));
        StdOut.println(lca.LCA(3, 9));
        
        StdOut.println(lca.SAP(2, 7));
        StdOut.println(lca.SAP(3, 9));
    }

}
