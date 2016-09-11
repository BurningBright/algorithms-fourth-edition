package class0203;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.18
 *      取三个中中间的元素作为标杆，可以将样本分割得更均衡。
 *      为什么要小于40时做三元素比较？
 *      可能是大样本时混乱度(熵)高，选谁做标杆都差不多
 * @author Leon
 * @date 2016-08-31 11:05:25
 */
public class MedianOfThree {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        
        int N = hi - lo + 1;
        if(N <= 40) {
            int m = median3(a, lo, lo+N/2, hi);
            exch(a, m, lo);
        }
        
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    @SuppressWarnings("rawtypes")
    public static int median3(Comparable[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    } 
    
    @SuppressWarnings("rawtypes")
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
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
    
 // is v < w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Comparable[] a = new Comparable[50];
        for(int i=0; i<a.length; i++)
            a[i] = i;
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
    }
    
}
