package class0203;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.25
 *      子数组小于阀值时熵较低，使用插入排序
 * @author Leon
 * @date 2016-09-11 09:53:43
 */
public class CutoffToInsertion {
    
    protected static int CUTOFF = 8;  // cutoff to insertion sort, must be >= 1
    protected static int COUNT = 0;
    protected static int DEPTH = 0;
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) {
        DEPTH++;
        int N = hi - lo + 1;
        
        // cutoff to insertion sort
        if (N <= CUTOFF) {
            COUNT++;
            insertionSort(a, lo, hi);
            return;
        }
        
        int p = CountExactCn.partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }
    
    // sort from a[lo] to a[hi] using insertion sort
    @SuppressWarnings("rawtypes")
    protected static void insertionSort(Comparable[] a, int lo, int hi) {
        for (int i = lo; i <= hi; i++)
            for (int j = i; j > lo && less(a[j], a[j-1]); j--)
                exch(a, j, j-1);
    }
    
    // is v < w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
    
    // exchange a[i] and a[j]
    protected static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        int N = 100000;
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = i;
        }
        
        int[] M = {10, 20, 50};
        for(int i=0; i<M.length; i++) {
            CUTOFF = M[i];
            StdRandom.shuffle(a);
            sort(a, 0, N-1);
            StdOut.println(M[i]+" "+COUNT);
            COUNT = 0;
        }
        
    }
    
}
