package class0204;

import rlgs4.MaxPQ;
import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.Stopwatch;

/**
 * @Description 2.4.28
 *      筛出M个最小的Euclidean distance
 *      约8.5秒
 * @author Leon
 * @date 2016-09-23 16:56:15
 */
public class SelectionFilter {

    public static void main(String[] args) {
        int N = 100000000;
        int M = 10000;
        
        MaxPQ<Integer> mpq = new MaxPQ<Integer>(M);
        
        Stopwatch sw = new Stopwatch();
        for(int i=0; i<N; i++) {
            int x = StdRandom.uniform(1000);
            int y = StdRandom.uniform(1000);
            int z = StdRandom.uniform(1000);
            mpq.insert((int) Math.sqrt(x * x + y * y + z * z));
            if(i>=M) 
                mpq.delMax();
        }
        StdOut.printf("%.2f", sw.elapsedTime());
        
    }

}
