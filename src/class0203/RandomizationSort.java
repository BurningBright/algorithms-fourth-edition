package class0203;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.29
 *      不改变样本，而改变取分割点方式
 *      敌不动我动。
 * @author Leon
 * @date 2016-09-11 16:04:40
 */
public class RandomizationSort extends CutoffToInsertion{
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) { 
        int N = hi - lo + 1;
        
        // cutoff to insertion sort
        if (N <= CUTOFF) {
            insertionSort(a, lo, hi);
            return;
        }
        
        int p = partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }
    
    @SuppressWarnings("rawtypes")
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        
        // 随机取分割点
        int p = StdRandom.uniform(lo, hi+1);
        exch(a, lo, p);
        
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v))
                if (i == hi) break;

            // find item on hi to swap
            while (less(v, a[--j]))
                if (j == lo) break;      // redundant since a[lo] acts as sentinel

            // check if pointers cross
            if (i >= j) break;

            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }
    
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
                StdOut.println(N[i]+" "+M[j]);
            }
            
        }
    }

}
