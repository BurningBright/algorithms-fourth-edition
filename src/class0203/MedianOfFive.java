package class0203;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 
 *      取5元素比较后取元素做标杆
 *      样本空间应该要更大一些40->70
 * @author Leon
 * @date 2016-09-08 15:30:07
 */
public class MedianOfFive {

    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        
        int N = hi - lo + 1;
        if(N <= 70) {
            int m = median5(a, lo, lo+N/4, lo+N/2, lo+3*N/4, hi);
            exch(a, m, lo);
        }
        
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
    }
    
    @SuppressWarnings("rawtypes")
    public static int median5(Comparable[] a, int i, int j, int k, int m, int n) {
        int t = (less(a[i], a[j]) ?
                    (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
                    (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
        return (less(a[t], a[m]) ?
                    (less(a[m], a[n]) ? m : less(a[t], a[n]) ? n : t) :
                    (less(a[n], a[m]) ? m : less(a[n], a[t]) ? n : t));
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
