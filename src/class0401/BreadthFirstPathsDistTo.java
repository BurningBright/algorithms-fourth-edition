package class0401;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.1.13 
 *      对广度优先路径加入路径长度计数
 * @author Leon
 * @date 2017-11-06 10:50:00
 */
public class BreadthFirstPathsDistTo {
    private boolean[] marked; // Is a shortest path to this vertex known?
    private int[] edgeTo; // last vertex on known path to this vertex
    private int[] distTo; // the number of edges
    private final int s; // source

    public BreadthFirstPathsDistTo(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Queue<Integer> queueA = new Queue<Integer>();
        Queue<Integer> queueB = new Queue<Integer>();
        marked[s] = true; // Mark the source
        queueA.enqueue(s); // and put it on the queue.
        int count = 1;
        while (!queueA.isEmpty() || !queueB.isEmpty()) {
            
            if(queueB.isEmpty()) {
                while (!queueA.isEmpty()) {
                    int v = queueA.dequeue(); // Remove next vertex from the queue.
                    for (int w : G.adj(v)) {
                        if (!marked[w]) // For every unmarked adjacent vertex,
                        {
                            edgeTo[w] = v; // save last edge on a shortest path,
                            marked[w] = true; // mark it because path is known,
                            distTo[w] = count;
                            queueB.enqueue(w); // and add it to the queue.
                        }
                    }
                }
                count++;
            }
            
            if(queueA.isEmpty()) {
                while (!queueB.isEmpty()) {
                    int v = queueB.dequeue(); // Remove next vertex from the queue.
                    for (int w : G.adj(v)) {
                        if (!marked[w]) // For every unmarked adjacent vertex,
                        {
                            edgeTo[w] = v; // save last edge on a shortest path,
                            marked[w] = true; // mark it because path is known,
                            distTo[w] = count;
                            queueA.enqueue(w); // and add it to the queue.
                        }
                    }
                }
                count++;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }
    
    public int distTo(int v) {
        return distTo[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }

    public static void main(String[] args) {
        In in = new In("resource/4.1/tinyG.txt");
        BreadthFirstPathsDistTo bfs = new BreadthFirstPathsDistTo(new Graph(in), 0);
        StdOut.println(bfs.hasPathTo(4));
        StdOut.println(bfs.distTo(4));
        for(Integer i: bfs.pathTo(4)) {
            StdOut.print(i + " -> ");
        }
    }

}
