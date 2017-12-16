package class0402;

import java.util.Iterator;

import rlgs4.SET;
import stdlib.In;

/**
 * @Description 4.2.9
 *          验证DAG的拓扑顺序
 * @author Leon
 * @date 2017-12-11 10:20:00
 */
public class CheckTopologicalOrder {
    
    public static boolean checkOrder(Digraph dgh, int[] order) {
        if(dgh == null || dgh.V() < 1 || order == null 
                || order.length < 1 || dgh.V() != order.length)
            throw new IllegalArgumentException();
        SET<Integer> set = new SET<Integer>();
        set.add(order[0]);
        for(int v=1; v<order.length; v++) {
            // check cycle
            for(int w: dgh.adj(order[v]))
                if(set.contains(w)) {
                    return false;
                }
            set.add(order[v]);
        }
        return true;
    }
    
    public static boolean checkOrder(Digraph dgh, Iterator<Integer> order) {
        if(dgh == null || dgh.V() < 1 || order == null || !order.hasNext())
            throw new IllegalArgumentException();
        SET<Integer> set = new SET<Integer>();
        set.add(order.next());
        while (order.hasNext()) {
            int v = order.next();
            // check cycle
            for(int w: dgh.adj(v))
                if(set.contains(w)) {
                    return false;
                }
            set.add(v);
        }
        return true;
    }
    
    public static void main(String[] args) {
        Digraph dgh = new Digraph(new In("resource/4.2/tinyDAG.txt"));
        Topological top = new Topological(dgh);
        for(int i: top.order())
            System.out.print(i + " ");
        System.out.println();
        
        int[] right = {8, 7, 2, 3, 0, 6, 9, 10, 11, 12, 1, 5, 4};
        int[] wrong = {8, 7, 2, 3, 0, 6, 9, 10, 12, 11, 1, 5, 4};
        System.out.println(checkOrder(dgh, right));
        System.out.println(checkOrder(dgh, wrong));
    }

}
