package class0402;

import rlgs4.Digraph;
import rlgs4.SET;
import rlgs4.Topological;
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
        for(int i=1; i<order.length; i++) {
            // check cycle
            for(int j: dgh.adj(order[i]))
                if(set.contains(j)) {
                    return false;
                }
            set.add(order[i]);
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
