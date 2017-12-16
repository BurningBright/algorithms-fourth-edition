package class0402;

import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.2.30
 *          队列实现拓扑排序
 *          源头入队，循环源头队列，遇到入度建达0，入源头队列
 *          每次源头队列循环的弹出顺序即为拓扑顺序，绝不会有入度达0在后的节点被率先弹出
 *          保证所有率先弹出的节点，不会有入度节点在后
 *          节点达0后，加入源队列
 * @author Leon
 * @date 2017-12-16 17:26:00
 */
public class TopologicalInQueue {
    
    private int V;
    private int[] inDegrees;
    private Queue<Integer> source;  //源
    private Queue<Integer> order;   //顺序
    
    public TopologicalInQueue(Digraph G) {
        this.V = G.V();
        DirectedCycle cyclefinder = new DirectedCycle(G);
        if (!cyclefinder.hasCycle()) {
            inDegrees = new int[V];
            source = new Queue<Integer>();
            order = new Queue<Integer>();
            for (int v=0; v<V; v++) {
                inDegrees[v] = G.indegree(v);
                // 初始源
                if (inDegrees[v] == 0)
                    source.enqueue(v);
            }
            
            while (!source.isEmpty()) {
                int v = source.dequeue();
                order.enqueue(v);
                for (int w : G.adj(v)) {
                    inDegrees[w]--;
                    // 再也没有节点指向自身
                    if (inDegrees[w] == 0)
                        // 加入源
                        source.enqueue(w);
                }
            }
            
        }
    }
    
    public boolean isTopological() {
        return order != null && order.size() > 0;
    }
    
    public Iterable<Integer> order() {
        return order;
    }
    
    public static void main(String[] args) {
        Digraph dgh = new Digraph(new In("resource/4.2/tinyDAG.txt"));
        TopologicalInQueue tiq = new TopologicalInQueue(dgh);
        
        for(int v: tiq.order()) 
            StdOut.print(v + " ");
        
        StdOut.println();
        
        StdOut.println(CheckTopologicalOrder.checkOrder(dgh, tiq.order().iterator()));
        
    }

}
