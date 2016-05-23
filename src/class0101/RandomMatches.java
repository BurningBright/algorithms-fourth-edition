package class0101;

import java.util.Arrays;

import rlgs4.BinarySearch;
import stdlib.StdRandom;

/**
 * @Description 1.1.39
 *              随机匹配
 * @author leon
 * @date 2016-05-23 22:40:43
 */
public class RandomMatches {
    
    public static double run(int N, int T) {
        
        int count = 0;
        int M = T;
        while(M > 0) {
            int a[] = new int[N];
            int b[] = new int[N];
            // init data
            for(int i=0; i<N; i++) {
                a[i] = StdRandom.uniform(900000) + 100000;
                b[i] = StdRandom.uniform(900000) + 100000;
            }
            Arrays.sort(b);
            for(int i=0; i<N; i++) {
                if(BinarySearch.rank(a[i], b) > 0)
                    count++;
            }
//            System.out.println(count);
            M--;
        }
        return (double)count/T;
    }
    
    public static void main(String[] args) {
        int N[] = {1000, 10000, 100000, 1000000};
        for(int i=0; i<N.length; i++)
            System.out.println(run(N[i], 10));
//        run(N[0], 1);
    }

}
