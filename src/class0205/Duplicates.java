package class0205;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.5.31
 *      重复概率验证
 * @author Leon
 * @date 2016-11-10 09:39:08
 */
public class Duplicates {
    
    private static double experiment(int tail, int times, int range) {
        double sum = 0;
        for (int i=0; i<tail; i++) {
            int[] src = new int[times];
            for (int j=0; j<times; j++) 
                src[j] = StdRandom.uniform(range);
            Arrays.sort(src);
            
            int m = 0;
            for (int j=0, k=0; j<times; j++) {
                if (j-k > 1 && src[k] == src[j])
                    m += 1;
                else if (j-k == 1 && src[k] == src[j])
                    m += 2;
                else
                    k = j;
            }
            sum += (double)m/times;
        }
        return sum/tail;
    }
    
    public static void main(String[] args) {
        int T = 10;
        int[] N = {1000, 10000, 100000, 1000000};
        double[] Nm = {.5, 1, 2};
        
        StdOut.printf("%d, %d, %.2f, %.3f, %.3f\n", T, N[0], Nm[0], experiment(T, N[0], (int) (N[0] * Nm[1])), (1-Math.exp(-1/ Nm[1])));
        
        for(int i=0; i<N.length; i++) 
            for(int j=0; j<Nm.length; j++) {
                int M = (int) (N[i] * Nm[j]);
                StdOut.printf("%d, %d, %.2f, %.3f, %.3f\n", T, N[i], Nm[j], experiment(T, N[i], M), (1-Math.exp(-1/ Nm[j])));
            }
         
    }

}
