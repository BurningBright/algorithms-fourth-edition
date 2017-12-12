package class0402;

import java.util.Arrays;

import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.2.23
 *          2次方时间、空间复杂度的强联通元件
 * @author Leon
 * @date 2017-12-12 18:00:00
 */
public class StrongComponent {
    
    private boolean linked[][];
    private boolean marked[];
    private int id[];
    private int V;
    
    public StrongComponent(Digraph G) {
        this.V = G.V();
        linked = new boolean[V][V];
        id = new int[V];
        
        // 每个节点都要探索可达性
        for (int v=0; v<V; v++) {
            marked = new boolean[V];
            dfs(G, v);
        }
        
        int count = 1;
        id[0] = count;
        for (int i=0; i<V; i++) {
            if (id[i] == 0) {
                boolean flag = false;
                for (int j=0; j<V; j++) {
                    // 互通即为元件的一部分
                    if (linked[i][j] && linked[j][i] && id[j] != 0) {
                        id[i] = id[j];
                        flag = true;
                        break;
                    }
                }
                if(!flag)
                    id[i] = ++count;
            }
        }
        
    }
    
    private void dfs(Digraph G, int v) {
        marked[v] = true;
        Queue<Integer> que = new Queue<Integer>();
        que.enqueue(v);
        // 广度优先探索联通性
        while(!que.isEmpty()) {
            int vv = que.dequeue();
            for (int w : G.adj(vv)) {
                if (!marked[w]) {
                    marked[w] = true;
                    linked[v][w] = true;
                    que.enqueue(w);
                }
            }
        }
    }
    public boolean stronglyConnected(int v, int w) {
        validate(v);
        validate(w);
        return linked[v][w] && linked[w][v];
    }
    
    public int getId(int v) {
        validate(v);
        return id[v];
    }
    
    private void validate(int v) {
        if(v < 0 || v >= V)
            throw new IllegalArgumentException();
    }
    
    public static void main(String[] args) {
        StrongComponent sc = new StrongComponent(
                new Digraph(new In("resource/4.2/component.txt")));
        StdOut.println(Arrays.deepToString(sc.linked));
        StdOut.println(Arrays.toString(sc.id));
    }

}
