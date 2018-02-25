package class0404;

import rlgs4.Queue;
import rlgs4.SET;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.0
 *          yen's n短路径
 *          
 *          C -> D -> F
 *            ↘    ↑    ➚    ↓    ↘
 *               E -> G -> H
 *          
 *          0 -> 1 -> 3
 *            ↘    ↑    ➚    ↓    ↘
 *               2 -> 4 -> 5
 * @author Leon
 * @date 2018-02-24 10:40:00
 */
public class YansSSP {
    
    private DijkstraSP[]                  paths;        // A
    private Queue<DijkstraSP>             candidate;    // B
    
    private final int sink;
    
    public YansSSP(EdgeWeightedDigraph G, int source, int sink, int K) {
        this.sink = sink;
        candidate = new Queue<DijkstraSP>();
        paths = new DijkstraSP[K];
        
        paths[0] = new DijkstraSP(G, source);
        
        for (int k=1; k<K; k++) {
            // 寻找候选路径
            findKthPath(G, source, k);
            
            // 候选路径中最小权值即 kth最短
            double pri = Double.POSITIVE_INFINITY;
            DijkstraSP kth = null;
            int index = 0;
            int cnt=0;
            
            // 定位最小路径
            for (DijkstraSP sp: candidate) {
                if (sp.distTo(sink) < pri) {
                    pri = sp.distTo(sink);
                    kth = sp;
                    index = cnt;
                }
                cnt++;
            }
            cnt++;
            
            // 出列
            for (int i=0; i<cnt; i++) {
                if(index == i)
                    candidate.dequeue();
                else
                    candidate.enqueue(candidate.dequeue());
            }
            paths[k] = kth;         // 胜选
        }
    }
    
    private void findKthPath(EdgeWeightedDigraph G, int source, int k) {
        for (int i=0; i<paths[k-1].stepTo(sink); i++) {
            
            // 新建临时图
            SET<DirectedEdge> set = new SET<DirectedEdge>();
            for (DijkstraSP dij: paths) 
                if (dij != null) 
                    set.add(findKthEdge(dij.pathTo(sink), i));
            EdgeWeightedDigraph tmpG = new EdgeWeightedDigraph(G.V());
            for (DirectedEdge e: G.edges())
                if (!set.contains(e))
                    tmpG.addEdge(e);
            
            // 候选最短路径
            candidate.enqueue(new DijkstraSP(tmpG, source));
        }
    }
    
    private DirectedEdge findKthEdge(Iterable<DirectedEdge> path, int k) {
        int i = 0;
        for (DirectedEdge e: path) {
            if (i++ == k){
                return e;
            }
        }
        return null;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DijkstraSP sp: paths)
            if (sp != null)
                sb
                .append(sp.distTo(sink))
                .append("\t")
                .append(sp.stepTo(sink))
                .append("\t")
                .append(sp.pathTo(sink))
                .append("\r\n");
        return sb.toString();
    }
    
    public static void main(String[] args) {
        YansSSP ssp = new YansSSP(
                new EdgeWeightedDigraph(new In("resource/4.4/yens.txt")), 0, 5, 3);
        StdOut.println(ssp);
    }

}

/**
function YenKSP(Graph, source, sink, K):
   // Determine the shortest path from the source to the sink.
   A[0] = Dijkstra(Graph, source, sink);
   // Initialize the heap to store the potential kth shortest path.
   B = [];
   
   for k from 1 to K:
       // The spur node ranges from the first node to the next to last node in the previous k-shortest path.
       for i from 0 to size(A[k − 1]) − 1:
           
           // Spur node is retrieved from the previous k-shortest path, k − 1.
           spurNode = A[k-1].node(i);
           // The sequence of nodes from the source to the spur node of the previous k-shortest path.
           rootPath = A[k-1].nodes(0, i);
           
           for each path p in A:
               if rootPath == p.nodes(0, i):
                   // Remove the links that are part of the previous shortest paths which share the same root path.
                   remove p.edge(i,i + 1) from Graph;
           
           for each node rootPathNode in rootPath except spurNode:
               remove rootPathNode from Graph;
           
           // Calculate the spur path from the spur node to the sink.
           spurPath = Dijkstra(Graph, spurNode, sink);
           
           // Entire path is made up of the root path and spur path.
           totalPath = rootPath + spurPath;
           // Add the potential k-shortest path to the heap.
           B.append(totalPath);
           
           // Add back the edges and nodes that were removed from the graph.
           restore edges to Graph;
           restore nodes in rootPath to Graph;
                   
       if B is empty:
           // This handles the case of there being no spur paths, or no spur paths left.
           // This could happen if the spur paths have already been exhausted (added to A), 
           // or there are no spur paths at all - such as when both the source and sink vertices 
           // lie along a "dead end".
           break;
       // Sort the potential k-shortest paths by cost.
       B.sort();
       // Add the lowest cost path becomes the k-shortest path.
       A[k] = B[0];
       B.pop();
   
   return A;
*/