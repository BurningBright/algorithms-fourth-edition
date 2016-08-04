package class0202;

import stdlib.StdOut;
import stdlib.StdRandom;
/**
 * @Description 2.2.12
 *      合并排序空间次线性
 *      目的在于压缩空间
 *      理解有误-思路应该是：分组-抽取-排好-放回-循环-合并
 *      -_-其实是一样的
 *      但看起来合并依旧不容易
 *      难道是块思路错了
 * @author Leon
 * @date 2016-08-03 17:10:15
 */
public class SublinearESP {
    
    public static int N;
    
    public static int M;
    
    @SuppressWarnings("unchecked")
    public static void sort(Comparable<Object>[] src) {
        N = src.length;
//        M = (int) Math.ceil(Math.sqrt(N));
        M = 5;
        StdOut.println(M);
        Comparable<Object>[] aux = new Comparable[Math.max(N, N/M)];
//        sortOne(0, src.length-1, src, aux);
        sortTwo(0, src.length-1, src, aux);
        assert isSorted(src);
    }
    
    public static void sortOne(int lo, int hi, Comparable<Object>[] src,
            Comparable<Object>[] aux) {
        StdOut.println("lo:"+lo+" \t hi:"+hi);
        if(hi - lo <= M) {
            MergeImprov.insertForMerge(lo, hi, src);
            return;
        }
        
        int tar = ((hi - lo + 1) / M) / 2;
        int mid = lo + (((tar == 0 ? 1 : tar) * M - 1));
        StdOut.println("--"+mid);
        
        sortOne(lo, mid, src, aux);
        sortOne(mid+1, hi, src, aux);
        
        mergeFifth(lo, mid, hi, src, aux);
        
    }
    
    public static void sortTwo(int lo, int hi, Comparable<Object>[] src,
            Comparable<Object>[] aux) {
        // 插排排好各个first key
        for(int i=0; i<M; i++)
            for(int j=i; j<N; j += M)
                for(int k=j; k>i && less(src[k], src[k-M]); k -= M)
                    exch(src, k, k-M);
    }
    
    public static void mergeFifth(int lo, int mid, int hi,
            Comparable<Object>[] a, Comparable<Object>[] aux) {
        
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static void exch(Comparable<Object>[] a, int i, int j) {
        Comparable<Object> t = a[i];
        a[i] = a[j];
        a[j] = t;
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
//        Comparable[] a = new Comparable[] { "E", "E", "G", "M", "R", "A", "C", "E", "R", "T" };
        Comparable[] b = new Comparable[100];
        for(int i=0; i<100; i++) {
            b[i] = StdRandom.uniform(100);
        }
        sort(b);
        for(int i=0; i<100; i++) {
            StdOut.print(b[i]);
            if ((i + 1) % 20 != 0)
                StdOut.print("\t");
            else 
                StdOut.println();
        }
    }

}
