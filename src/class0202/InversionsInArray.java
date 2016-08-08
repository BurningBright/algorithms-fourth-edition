package class0202;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.2.19
 *      累加样本中的"倒置量"
 *      需要有线性级的性能
 * @author Leon
 * @date 2016-08-04 17:20:15
 */
public class InversionsInArray {
    
    // merge and count (Comparable version)
    @SuppressWarnings("rawtypes")
    private static long merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        long inversions = 0;

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        StdOut.println( i + " " + mid + " " + hi);
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                a[k] = aux[j++];
            else if (j > hi)                 a[k] = aux[i++];
            else if (less(aux[j], aux[i])) {
                StdOut.println(aux[j] + " " + aux[i] + " " + j + " " + i + " " + mid + " --");
                a[k] = aux[j++];
                /* 
                 * 这里使用mid作为标准是由于：
                 * 分合时可以将mid作为i在右半边的开始，
                 * 右半边的"倒置量"已在右半边的分合中被计算
                 */
                inversions += (mid - i + 1);
                }
            else                             a[k] = aux[i++];
        }
        return inversions;
    }

    /*
     * return the number of inversions in the subarray b[lo..hi]
     * side effect b[lo..hi] is rearranged in ascending order
     * a 用来断言不被排序，b 用来计数会被排序
     */
    @SuppressWarnings("rawtypes")
    private static long count(Comparable[] a, Comparable[] b, Comparable[] aux, int lo, int hi) {
        long inversions = 0;
        if (hi <= lo) return 0;
        int mid = lo + (hi - lo) / 2;
        inversions += count(a, b, aux, lo, mid);  
        inversions += count(a, b, aux, mid+1, hi);
        inversions += merge(b, aux, lo, mid, hi);
        assert inversions == brute(a, lo, hi);
        return inversions;
    }


    /**
     * Returns the number of inversions in the comparable array.
     * The argument array is not modified.
     * @param  a the array
     * @return the number of inversions in the array. An inversion is a pair of 
     *         indicies <tt>i</tt> and <tt>j</tt> such that <tt>i &lt; j</tt>
     *         and <tt>a[i].compareTo(a[j]) &gt; 0</tt>.
     */
    @SuppressWarnings("rawtypes")
    public static long count(Comparable[] a) {
        Comparable[] b   = new Comparable[a.length];
        Comparable[] aux = new Comparable[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
        long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }


    // is v < w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // count number of inversions in a[lo..hi] via brute force (for debugging only)
    @SuppressWarnings("rawtypes")
    private static long brute(Comparable[] a, int lo, int hi) {
        long inversions = 0;
        for (int i = lo; i <= hi; i++)
            for (int j = i + 1; j <= hi; j++)
                if (less(a[j], a[i])) inversions++;
        return inversions;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        int N = 5;
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = StdRandom.uniform(100);
        }
        StdOut.println(Arrays.toString(a));
        StdOut.println(count(a));
        StdOut.println(brute(a, 0, a.length-1));
    }

}
