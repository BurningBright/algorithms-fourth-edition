package class0404;

import rlgs4.Queue;
import rlgs4.Stack;

/**
 * @Description 4.4.0
 *          深度优先顺序
 * @author Leon
 * @date 2018-01-11 18:30:00
 */
public class DepthFirstOrder {
    
    private boolean[] marked;
    private Queue<Integer> pre; // vertices in preorder
    private Queue<Integer> post; // vertices in postorder
    private Stack<Integer> reversePost; // vertices in reverse postorder

    public DepthFirstOrder(EdgeWeightedDigraph G) {
        pre = new Queue<Integer>();
        post = new Queue<Integer>();
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); v++)
            if (!marked[v])
                dfs(G, v);
    }

    private void dfs(EdgeWeightedDigraph G, int v) {
        pre.enqueue(v);
        marked[v] = true;
        for (DirectedEdge e : G.adj(v))
            if (!marked[e.to()])
                dfs(G, e.to());
        post.enqueue(v);
        reversePost.push(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public Iterable<Integer> reversePost() {
        return reversePost;
    }
}
