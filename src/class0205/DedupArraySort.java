package class0205;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 2.5.4
 *      对字符串数组排序去重
 *      预计用插排、快排、合并排都行
 * @author leon
 * @date 2016-10-23 13:55:35
 */
public class DedupArraySort {
    
    public static void insertionStyle(Comparable<Object>[] a) {
        int N = a.length;
        for (int i=1; i<N; i++) {
            for (int j=i; j>0; j--) {
                if(a[j].compareTo(a[j-1]) == 0) {
                    exch(a, j, N-1);
                    a[(N--)-1] = null;
                    break;
                }
                if(a[j].compareTo(a[j-1]) < 0) {
                    exch(a, j, j-1);
                }
            }
        }
    }
    
    private static void exch(Comparable<Object>[] a, int i, int j) {
        Comparable<Object> t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        String a = "那么，如果我想让图片浮在中间呢？有没有float:middle呢？";
        String[] b = a.split("");
        StdOut.println(Arrays.toString(b));
        
        Comparable<Object>[] c = (Comparable<Object>[])new Comparable[b.length];
        for (int i=0; i<b.length; i++) {
            c[i] = (Comparable) b[i];
        }
        
        insertionStyle(c);
        
        StdOut.println(Arrays.toString(c));
    }

}
