package class0403;

import java.awt.Color;

import rlgs4.Queue;
import rlgs4.SET;
import stdlib.In;
import stdlib.StdDraw;
import stdlib.StdOut;

/**
 * @Description 4.3.0
 *          最小生成树 - 蛮力
 * @author Leon
 * @date 2017-12-18 16:27:00
 */
public class MST {
    
    private double weight;
    private Queue<Edge> tree = new Queue<Edge>();   // 树的探索顺序
    private SET<Edge> set = new SET<Edge>();        // 防止重复边
    
    public MST(EdgeWeightedGraph G) {
        // 假设是个联通权重图
        SET<Integer> checkV = new SET<Integer>();   // 防止重复节点
        checkV.add(0);
        
        // 未并为一个集合，进行下一轮切分，找树枝
        while (checkV.size() < G.V()) {
            
            int minW = -1;
            Edge minE = null;
            
            // 直接查找最小树枝
            for (Integer v: checkV) 
                for (Edge e: G.adj(v)) {
                    // 忽略已是树的树枝
                    if (set.contains(e))
                        continue;
                    if (minE == null || e.compareTo(minE) < 0) {
                        int w = e.other(v);
                        // 忽略成环的节点
                        if (checkV.contains(w))
                            continue;
                        minW = w;
                        minE = e;
                    }
                }
            
            // 加入集合
            if (minE != null) {
                checkV.add(minW);
                weight += minE.weight();
                tree.enqueue(minE);
                set.add(minE);
            }
        }
        
    }
    
    // all of the MST edges
    public Iterable<Edge> edges() {
        return tree;
    }
    
    // weight of MST
    public double weight() {
        return weight;
    }
    
    public int size(){
        return tree.size();
    }
    
    public void show() {
        StdDraw.setPenColor(Color.BLACK);
        for (Edge e: edges()) 
            e.show();
    }
    
    public static void main(String[] args) {
        MST mst = new MST(new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        for (Edge e: mst.edges())
            StdOut.println(e);
        StdOut.println(mst.weight());
    }

}
