package class0203;

import java.util.Arrays;

import class0201.InsertionSort;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.27
 *      忽略子数组，结束后统一插排
 *      大幅度的交互、对比会影响cache命中率？
 * @author Leon
 * @date 2016-09-11 14:14:32
 */
public class IgnoreSmallSubarrays {
    
    private static int CUTOFF = 8;
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void sort(Comparable[] a) {
        sort(a, 0, a.length-1);
        InsertionSort.sort(a);
    }
    
    @SuppressWarnings("rawtypes")
    private static void sort(Comparable[] a, int lo, int hi) {
        int N = hi - lo + 1;
        
        // cutoff to insertion sort
        if (N <= CUTOFF) return;
        
        int p = CountExactCn.partition(a, lo, hi);
        sort(a, lo, p - 1);
        sort(a, p + 1, hi);
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        int N = 50;
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = i;
        }
        StdRandom.shuffle(a);
        StdOut.println(Arrays.toString(a));
        sort(a);
        StdOut.println(Arrays.toString(a));
    }

}
