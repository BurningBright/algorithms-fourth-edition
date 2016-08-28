package class0203;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 2.3.17
 *          哨兵“跳过”越界检查
 * @author Leon
 * @date 2016-08-28 13:58:19
 */
public class Sentinels {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a) {
        StdRandom.shuffle(a);
        StdOut.println(Arrays.toString(a));
        
        int max = 0;
        for(int i=0; i<a.length; i++)
            max = less(a[max], a[i])? i: max;
        exch(a, max, a.length-1);
        
        StdOut.println(Arrays.toString(a));
        
        sort(a, 0, a.length - 1);
//        assert isSorted(a);
        StdOut.println(Arrays.toString(a));
    }
    
    @SuppressWarnings("rawtypes")
    private static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
//        assert isSorted(a, lo, hi);
    }
    
    @SuppressWarnings("rawtypes")
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) { 

            // find item on lo to swap
            while (less(a[++i], v));
//                if (i == hi) break;
            /*
             * 右边界的哨兵问题当时有考虑到，
             * 当时想的时如果去掉检查那循环会越界。
             * 现在需要先遍历一遍将最大的放好做哨，
             * 保证初始的右边不会越界，
             * 之后的分开递归如果右越界，右组第一个做哨。
             */
            
            // find item on hi to swap
            while (less(v, a[--j]));
//                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            
            /*
             *  既然左边界冗余(自己不会比自己还小)，
             *  那只要从右边找到 >=v 的元素就行
             */
            
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
        int N = 100;
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = StdRandom.uniform(10);
        }
        sort(a);
    }

}
