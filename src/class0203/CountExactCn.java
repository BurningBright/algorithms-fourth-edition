package class0203;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.6
 *      计数真实比较次数，对比理论2NLnN
 * @author Leon
 * @date 2016-08-16 15:52:36
 */
public class CountExactCn {
    
    private static int COUNTER;
    private static int ZERO, ONE, TWO;
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
//        if(hi < lo) {ZERO++; return;}
//        if(hi == lo) {ONE++; return;}
//        if(hi - lo == 1) TWO++;
        
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
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
    

    /**
     * Rearranges the array so that a[k] contains the kth smallest key;
     * a[0] through a[k-1] are less than (or equal to) a[k]; and
     * a[k+1] through a[N-1] are greater than (or equal to) a[k].
     * 非递归形式排序
     * @param a the array
     * @param k find the kth smallest
     
    @SuppressWarnings("rawtypes")
    public static Comparable select(Comparable[] a, int k) {
        if (k < 0 || k >= a.length) {
            throw new IndexOutOfBoundsException("Selected element out of bounds");
        }
        StdRandom.shuffle(a);
        int lo = 0, hi = a.length - 1;
        while (hi > lo) {
            int i = partition(a, lo, hi);
            if      (i > k) hi = i - 1;
            else if (i < k) lo = i + 1;
            else return a[i];
        }
        return a[lo];
    }
     */


   /***********************************************************************
    *  Helper sorting functions
    ***********************************************************************/
    
    // is v < w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable v, Comparable w) {
        COUNTER++;
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
        
        for(int i=10; i<10001; i *= 10) {
            COUNTER = 0;
            ZERO = ONE = TWO = 0;
            
            Comparable[] a = new Comparable[i];
            for(int j=0; j<i; j++)
                a[j] = j;
            StdRandom.shuffle(a);
            sort(a, 0, a.length-1);
            
            StdOut.printf("%d\t %7.3f\t %d\t %d\t %d\t %d\n", i, 2 * i * Math.log(i), COUNTER, ZERO, ONE, TWO);
            
        }
        
        /*
        Comparable[] a = new Comparable[10];
        for(int i=0; i<a.length; i++)
            a[i] = 1;
        sort(a, 0, a.length-1);
        StdOut.println(COUNTER);
        */
        /*
        // 计算 比对次数标准差
        int N = 10000;
        long[] calc = new long[N];
        for(int i=0; i<10; i++) {
            Comparable[] a = new Comparable[N];
            for(int j=0; j<N; j++)
                a[j] = j;
            StdRandom.shuffle(a);
            COUNTER = 0;
            sort(a, 0, a.length - 1);
            calc[i] = COUNTER;
        }
        
        long mean = 0;
        double stdDev = 0;
        for(int i=0; i<100; i++)
            mean += calc[i];
        mean /= N;
        
        for(int i=0; i<100; i++)
            stdDev += Math.pow(mean - calc[i], 2);
        stdDev /= mean;
        
        StdOut.printf("%d\t %10.3f\n", mean, Math.sqrt(stdDev));
        */
    }

}
