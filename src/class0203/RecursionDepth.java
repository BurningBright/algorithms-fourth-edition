package class0203;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.28
 *      实践有阀值过滤的快排深度
 * @author Leon
 * @date 2016-09-11 14:54:02
 */
public class RecursionDepth extends CutoffToInsertion{

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        
        int[] N = {1000, 10000, 100000, 1000000};
        
        for(int i=0; i<N.length; i++) {
            
            Comparable[] a = new Comparable[N[i]];
            for(int j=0; j<N[i]; j++) {
                a[j] = j;
            }
            
            int[] M = {10, 20, 50};
            
            for(int j=0; j<M.length; j++) {
                CUTOFF = M[j];
                StdRandom.shuffle(a);
                sort(a, 0, N[i]-1);
                StdOut.println(N[i]+" "+M[j]+" "+DEPTH);
                DEPTH=0;
            }
            
        }
        
    }

}
