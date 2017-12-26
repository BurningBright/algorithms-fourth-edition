package class0403;

import rlgs4.MaxPQ;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.23
 *      Vyssotsky’s algorithm
 *      每次将成环的最大权重边去除。怎么找环，是个麻烦的问题
 * @author Leon
 * @date 2017-12-26 13:46:00
 */
public class VyssotskyMST {
    
    private int V;
    private double weight;
    
    private boolean[] marked;       // 节点是否访问过
    private int[] edgeTo;           // 环路径节点
    private boolean[] onStack;      // 节点是否在副本的探索路径里
    
    private EdgeWeightedGraph mst;
    
    public VyssotskyMST(EdgeWeightedGraph G) {
        
        this.V = G.V();
        mst = new EdgeWeightedGraph(G.V());
        
        for (Edge e: G.edges()) {
//            StdOut.println(e);
            mst.addEdge(e);
            //  当前边所在节点，排除环情形
            edgeCheckOut(mst, e);
        }
        
        /*
        mst.addEdge(G.edges().iterator().next());
        while (set.size() != G.E()) {
            for (Edge e: mst.edges()) {
                for (Edge ev: G.adj(e.either())) {
                    if (!set.contains(ev)) {
                        mst.addEdge(ev);
                        Edge t = edgeCheckOut(mst, ev);
                        
                        while (t != null) {
                            mst.delEdge(t);
                            t = edgeCheckOut(mst, ev);
                        }
                        set.add(ev);
                    }
                }
                
                for (Edge ev: G.adj(e.other(e.either()))) {
                    if (!set.contains(ev)) {
                        mst.addEdge(ev);
                        Edge t = edgeCheckOut(mst, ev);
                        
                        while (t != null) {
                            mst.delEdge(t);
                            t = edgeCheckOut(mst, ev);
                        }
                        set.add(ev);
                    }
                }
            }
        }
        */
        for (Edge e: mst.edges())
            weight += e.weight();
    }
    
    private void edgeCheckOut(EdgeWeightedGraph mst, Edge e) {
        
        // 初始化
        marked = new boolean[V];
        edgeTo = new int[V];
        onStack = new boolean[V];
        
        // 递归找环的最大权
        dfs(mst, e.either(), e.either());
        
    }
    
    private void dfs(EdgeWeightedGraph mst, int v, int u) {
        // 显示记录程序调用栈
        onStack[v] = true;
        // 记录已访问节点
        marked[v] = true;
        
        for (Edge e : mst.adj(v)) {
            
            int w = e.other(v);
            
            // 排除上次调用路径，避免死循环
            if (w == u)
                continue;
            
            if (!marked[w]) {
                // 未被访问，继续探索
                edgeTo[w] = v;
                dfs(mst, w, v);
            } else if(onStack[w]) {
                // 如果节点已被访问过，且在调用栈中，说明有环型结构
                MaxPQ<Edge> pq = new MaxPQ<Edge>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    pq.insert(mst.getEdge(x, edgeTo[x]));
                }
                pq.insert(mst.getEdge(w, v));
                // 删除环中对大权的边
                mst.delEdge(pq.delMax());
            }
            
            /*
            marked[v] = true;
            
            for (Edge e : mst.adj(v)) {
                int w = e.other(v);
                
                if (!marked[w])
                    dfs(mst, w, v);
                else if (w != u)
                    hasCycle = true;
                
            }
            */
            
            /*
            else if (onStack[w]) {
                pq = new MaxPQ<Edge>();
                for (int x = v; x != w; x = edgeTo[x]) {
                    if(edgeTo[x] != w)
                    pq.insert(mst.getEdge(x, edgeTo[x]));
                }
                pq.insert(mst.getEdge(w, v));
                StdOut.println("xxx");
            }
            */
        }
        onStack[v] = false;
        
    }
    
    public Iterable<Edge> edges() {
        return mst.edges();
    }

    public double weight() {
        return weight;
    }
    
    public static void main(String[] args) {
        VyssotskyMST mst = new VyssotskyMST(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        StdOut.println("------");
        for (Edge e: mst.edges())
            StdOut.println(e);
        
        StdOut.println(mst.weight());
    }

}
