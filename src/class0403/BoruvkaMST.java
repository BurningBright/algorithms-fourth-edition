package class0403;

import rlgs4.Queue;
import rlgs4.UF;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.43
 *      There are a most log V phases since number of trees 
 *      decreases by at least a factor of 2 in each phase. 
 *      Attractive because it is efficient and can be run in parallel.
 *      
 *      和kruskal区别在于筛取权的位置不同
 *      boruvka分布在各个阶段，更新最小邻接
 *      kruskal在每次的优先队列取顶，或统一的排序取头
 *      
 *      风格有点像lazy-prim 和 eager-prim的关系
 *      kruskal - lazy      重点是当前的树，直接找下一个最小权边
 *      boruvka - eager     重点是各阶段的树，更新树的临近最小权边
 *      
 *      kruskal 和 prim的区别
 *      kruskal     是“离散”的，下一个加入mst的边可能会出现在图的各个地方
 *      prim        是“扩散”的，下一个加入mst的边一定是出现在当前mst周边
 *      
 * @author Leon
 * @date 2017-12-31 12:40:00
 */
public class BoruvkaMST {
    
    private Queue<Edge> mst;
    private double weight;
    
    public BoruvkaMST(EdgeWeightedGraph G) {
        mst = new Queue<Edge>();
        UF uf = new UF(G.V());
        
        while (mst.size() < G.V()-1) {
            // 存储连接两树直接最小权边ps.如果存在
            Edge[] edges = new Edge[G.V()];
            for (Edge e: G.edges()) {
                int v = e.either(), w = e.other(v);
                int i = uf.find(v);
                int j = uf.find(w);
                if(i == j) continue;
                // 优先队列部分的变体
                if(edges[i] == null || less(e, edges[i])) edges[i] = e;
                if(edges[j] == null || less(e, edges[j])) edges[j] = e;
            }
            
            // 产生某颗树指向另一棵树的最小边
            for (Edge e: edges)
                if (e != null) {
                    int v = e.either(), w = e.other(v);
                    if (uf.connected(v, w)) continue;
                    uf.union(v, w);
                    weight += e.weight();
                    mst.enqueue(e);
                }
            
        }
        
    }
    
    public boolean less(Edge a, Edge b) {
        return a.weight() - b.weight() < 0;
    }
    
    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
    
    public static void main(String[] args) {
        BoruvkaMST p = new BoruvkaMST(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        for (Edge e: p.mst)
            StdOut.println(e);
        StdOut.println(p.weight);
    }

}
