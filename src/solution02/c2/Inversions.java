package solution02.c2;

import stdlib.StdIn;
import stdlib.StdOut;

/******************************************************************************
 *  Compilation:  javac Inversions.java
 *  Execution:    java Inversions < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  
 *  Read in N integers and count number of inversions in N log N time.
 *
 ******************************************************************************/

/**
 *  The <tt>Inversions</tt> class provides static methods to count the 
 *  number of <em>inversions</em> in either an array of integers or comparables.
 *  An inversion in an array <tt>a[]</tt> is a pair of indicies <tt>i</tt> and
 *  <tt>j</tt> such that <tt>i</tt> &lt; <tt>j</tt> and <tt>a[i] &gt; a[j]</tt>.
 *  <p>
 *  This implementation uses a generalization of mergesort. The <em>count</em>
 *  operation takes time proportional to <em>n</em> log <em>n</em>,
 *  where <em>n</em> is the number of keys in the array.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/22mergesort">Section 2.2</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Inversions {

    // do not instantiate
    private Inversions() { }

    // merge and count
    private static long merge(int[] a, int[] aux, int lo, int mid, int hi) {
        long inversions = 0;

        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k]; 
        }

        // merge back to a[]
        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)           a[k] = aux[j++];
            else if (j > hi)            a[k] = aux[i++];
            else if (aux[j] < aux[i]) { a[k] = aux[j++]; inversions += (mid - i + 1); }
            else                        a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo..hi]
    // side effect b[lo..hi] is rearranged in ascending order
    private static long count(int[] a, int[] b, int[] aux, int lo, int hi) {
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
     * Returns the number of inversions in the integer array.
     * The argument array is not modified.
     * @param  a the array
     * @return the number of inversions in the array. An inversion is a pair of 
     *         indicies <tt>i</tt> and <tt>j</tt> such that <tt>i &lt; j</tt>
     *         and <tt>a[i]</tt> &gt; <tt>a[j]</tt>.
     */
    public static long count(int[] a) {
        int[] b   = new int[a.length];
        int[] aux = new int[a.length];
        for (int i = 0; i < a.length; i++)
            b[i] = a[i];
        long inversions = count(a, b, aux, 0, a.length - 1);
        return inversions;
    }



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
        for (int k = lo; k <= hi; k++) {
            if      (i > mid)                a[k] = aux[j++];
            else if (j > hi)                 a[k] = aux[i++];
            else if (less(aux[j], aux[i])) { a[k] = aux[j++]; inversions += (mid - i + 1); }
            else                             a[k] = aux[i++];
        }
        return inversions;
    }

    // return the number of inversions in the subarray b[lo..hi]
    // side effect b[lo..hi] is rearranged in ascending order
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

    // count number of inversions in a[lo..hi] via brute force (for debugging only)
    private static long brute(int[] a, int lo, int hi) {
        long inversions = 0;
        for (int i = lo; i <= hi; i++)
            for (int j = i + 1; j <= hi; j++)
                if (a[j] < a[i]) inversions++;
        return inversions;
    }

    /**
     * Reads in a sequence of integers from standard input and prints
     * the number of inversions.
     */
    public static void main(String[] args) {
        int[] a = StdIn.readAllInts();
        int n = a.length;
        Integer[] b = new Integer[n];
        for (int i = 0; i < n; i++)
            b[i] = a[i];
        StdOut.println(Inversions.count(a));
        StdOut.println(Inversions.count(b));
    }
}
