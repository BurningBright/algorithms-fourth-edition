package class0202;

import java.util.Arrays;

import stdlib.StdOut;
/**
 * @Description 2.2.12
 *      合并排序空间次线性
 *      目的在于压缩空间
 * @author Leon
 * @date 2016-08-03 17:10:15
 */
public class SublinearESP {
    
    public static int CUTOFF;
    
    @SuppressWarnings("unchecked")
    public static void sort(Comparable<Object>[] src) {
        int N = src.length;
        CUTOFF = (int) Math.ceil(Math.sqrt(N));
        StdOut.println(CUTOFF);
        Comparable<Object>[] aux = new Comparable[CUTOFF];
        sort(0, src.length-1, aux, src);
        assert isSorted(src);
    }
    
    public static void sort(int lo, int hi, Comparable<Object>[] src,
            Comparable<Object>[] aux) {
        StdOut.println("lo:"+lo+" \t hi:"+hi);
        if(hi - lo <= CUTOFF) {
            MergeImprov.insertForMerge(lo, hi, aux);
            return;
        }
        
        int mid = lo + ((hi - lo + 1) / CUTOFF < 2 ? CUTOFF : (int)(((hi - lo + 1) / CUTOFF) / 2.0) * CUTOFF);
        StdOut.println("--"+mid);
        
        sort(lo, mid, src, aux);
        sort(mid+1, hi, src, aux);
        
        FastMerge.mergeThird(lo, mid, hi, src, aux);
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    public static boolean isSorted(Comparable<Object>[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }

    public static boolean isSorted(Comparable<Object>[] a, int lo, int hi) {
        for (int i = hi; i > lo + 1; i--) {
            if (less(a[i], a[i - 1]))
                return false;
        }
        return true;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static void main(String[] args) {
        Comparable[] a = new Comparable[] { "E", "E", "G", "M", "R", "A", "C", "E", "R", "T" };
        sort(a);
        System.out.println(Arrays.toString(a));
    }

}
