package class0402;

import java.util.Iterator;

import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.2.24
 *          汉密尔顿路径，每个节点访问一次
 * @author Leon
 * @date 2017-12-13 10:50:00
 */
public class HamiltonianPath {
    
    private Iterable<Integer> path;
    
    public HamiltonianPath(Digraph G) {
        Topological tp = new Topological(G);
        if (tp.isDAG()) {
            Iterator<Integer> it = tp.order().iterator();
            int prv = it.next();
            // 判断是否有连续顺序
            boolean flagP = true;
            while (it.hasNext()) {
                int w = it.next();
                
                // 判断是否有邻接顺序
                boolean flagV = false;
                for(int v: G.adj(prv))
                    if (v == w)
                        flagV = true;
                // 拓扑顺序非连续情况
                if (!flagV) {
                    flagP = false;
                    break;
                }
                prv = w;
            }
            
            if (flagP)
                path = tp.order();
        }
    }
    
    public boolean havePath() {
        return path != null;
    }
    
    public Iterable<Integer> path() {
        return path;
    }
    
    public static void main(String[] args) {
        HamiltonianPath hp = new HamiltonianPath(
                new Digraph(new In("resource/4.2/hamiltonianPath.txt")));
        StdOut.println(hp.havePath());
        StdOut.println(hp.path());
    }

}
