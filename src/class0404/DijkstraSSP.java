package class0404;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.7 
 *          Dijkstra 第二最小路径树
 *          一开始的思路是在寻找最短路径的过程中，寻找对应不合格边，使得整条路径符合第二最小路径。
 *          在画出第二最小路径后发现，所有路径并不组成一棵树，其中可能会有环的存在 ps.到自身不为0。
 *          在探索0->3的SSP过程中发现，探索0->3的SSP = 探索4->3的SP + 0->4
 *          维护起始节点的邻接边和邻接节点DijkstraSP的映射，SP < path < other weight path 即是SSP。
 * @author Leon
 * @date 2018-01-09 16:00:00
 */
public class DijkstraSSP {
    
    // 记录邻接最短路径映射
    private Map<DirectedEdge, DijkstraSP> map;
    // 记录邻接边
    private Stack<DirectedEdge> stack;
    // 原节点最短路径
    private DijkstraSP sp;
    
    public DijkstraSSP(EdgeWeightedDigraph G, int s) {
        map = new HashMap<DirectedEdge, DijkstraSP>();
        stack = new Stack<DirectedEdge>();
        sp = new DijkstraSP(G, s);
        
        // 寻找邻接节点的最短路径图 ps.排除起点为s的边
        EdgeWeightedDigraph NG = new EdgeWeightedDigraph(G.V());
        for (DirectedEdge e: G.edges())
            if (e.from() != s) 
                NG.addEdge(e);
            else
                stack.push(e);
        
        for (DirectedEdge e: stack)
            map.put(e, new DijkstraSP(NG, e.to()));
        
    }
    
    public double distTo(int v) {
        // 比最短路径大，比邻接其他路径小的路径为第二最小路径
        double base = sp.distTo(v);
        double current = Double.POSITIVE_INFINITY;
        for (Entry<DirectedEdge, DijkstraSP> entry: map.entrySet()) {
            // 对比路径的权
            double weight = entry.getKey().weight() + entry.getValue().distTo(v);
            if (weight > base && weight < current)
                current = weight;
        }
        return current;
    }

    public boolean hasPathTo(int v) {
        return distTo(v) < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        // 同distTo();
        double base = sp.distTo(v);
        double current = Double.POSITIVE_INFINITY;
        Entry<DirectedEdge, DijkstraSP> second = null;
        for (Entry<DirectedEdge, DijkstraSP> entry: map.entrySet()) {
            double weight = entry.getKey().weight() + entry.getValue().distTo(v);
            if (weight > base && weight < current) {
                current = weight;
                second = entry;
            }
        }
        
        if (second == null)
            return null;
        
        // 还原路径，队列起点为某一邻接边
        Queue<DirectedEdge> path = new Queue<DirectedEdge>();
        path.enqueue(second.getKey());
        for (DirectedEdge e: second.getValue().pathTo(v))
            path.enqueue(e);
        
        return path;
    }
    
    public static void main(String[] args) {
        DijkstraSP dj = new DijkstraSP(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")), 0);
        for (int i=0; i<8; i++)
            StdOut.printf("%s \t\t\t %.3f\r\n", dj.pathTo(i), dj.distTo(i));
        
        StdOut.println("-------------------");
        
        DijkstraSSP dj1 = new DijkstraSSP(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")), 0);
        for (int i=0; i<8; i++)
            StdOut.printf("%s \t\t\t %.3f\r\n", dj1.pathTo(i), dj1.distTo(i));
        
        StdOut.println("-------------------");
        
        DijkstraSSP dj2 = new DijkstraSSP(
              new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD2.txt")), 0);
        for (int i=0; i<4; i++)
            StdOut.printf("%s \t\t\t %.3f\r\n", dj2.pathTo(i), dj2.distTo(i));
    }
    
}




/*
public class DijkstraSSP {

    private DirectedEdge[] edgeTo;
    private double[] distTo;
    
    // 记录第二最小路径树
    private DirectedEdge[] edgeSsTo;
    private double[] distSsTo;
    private Stack<DirectedEdge> path;
    private double dist;
    private int S;
    private int V;
    private EdgeWeightedDigraph G;
    private IndexMinPQ<Double> pq;
    
    public DijkstraSSP(EdgeWeightedDigraph G, int s) {
        this.S = s;
        this.V = G.V();
        this.G = G;
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        edgeSsTo = new DirectedEdge[G.V()];
        distSsTo = new double[G.V()];
        
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
            distSsTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;
        pq.insert(s, 0.0);
//        while (!pq.isEmpty()) 
//            relax(G, pq.delMin());
        
        
        
        
        // 为查找第二最小路径的再次放松
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
            
            Double minKey = pq.minKey();
            int minIndex = pq.delMin();
            
            // 取第二最小边作为引导节点
            if (!pq.isEmpty()) {
                relax(G, pq.delMin());
                // 这里好像不会发生
                if (distTo[minIndex] > minKey)
                    pq.changeKey(minIndex, minKey);
            } else {
                relax(G, minIndex);
            }
            
        }
        
    }
    
    
    private void sRelax(int v, int t) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            double weight = distSsTo[v] + e.weight();
            if (distSsTo[w] > weight) {
                distSsTo[w] = weight;
                edgeSsTo[w] = e;
                if (pq.contains(w))
                    pq.changeKey(w, distSsTo[w]);
                else
                    pq.insert(w, distSsTo[w]);
            }
            if(w == t && weight > distTo[w] && weight < dist) {
                // 记录路径
                path = new Stack<DirectedEdge>();
                path.push(e);
                for (DirectedEdge ee = edgeTo[v]; ee != null; ee = edgeTo[ee.from()])
                    path.push(ee);
                dist = weight;
            }
        }
    }
    
    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v))
            return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        // 向根遍历
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()])
            path.push(e);
        return path;
    }
    
    public double distSsTo(int v) {
        return dist;
    }

    public boolean hasPathSsTo(int v) {
        return dist < Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathSsTo(int t) {
        edgeSsTo = new DirectedEdge[V];
        distSsTo = new double[V];
        dist = Double.POSITIVE_INFINITY;
        for (int v = 0; v < V; v++) 
            distSsTo[v] = Double.POSITIVE_INFINITY;
        
        distSsTo[S] = .0;
        pq.insert(S, .0);
        while (!pq.isEmpty()) 
            sRelax(pq.delMin(), t);
        
        return path;
    }
    
}
*/