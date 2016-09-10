package class0203;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.22
 *       J. Bentley and D. McIlroy 发明
 *       Quick3way算法，在循环中区分大中小，将区域分为三块
 *       Fast3Way算法，在循环中区分大小，将相等key放在两端
 *       为什么感觉效率依旧不高
 * @author Leon
 * @date 2016-09-10 14:04:04
 */
public class Fast3Way {
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) {
        if(hi<=lo) return;
        // 等key交互定位变量
        int pl = lo, pr = hi+1;
        // 循环定位变量
        int i = lo, j = hi+1;
        
        Comparable v = a[lo];
        while(true) {
            
            while(!than(a[++i], v)) {
                if (i == hi) break;
                if (eq(v, a[i]))
                    exch(a, ++pl, i);
            }
            
            while(!than(v, a[--j])) {
                if (j == lo) break;
                if (eq(v, a[j]))
                    exch(a, --pr, j);
            }
            // pointers cross
            if (i == j && eq(a[i], v))
                exch(a, ++pl, i);
            if (i >= j) break;
            
            exch(a, i, j);
        }
        StdOut.println("="+i+" "+j+" "+pl+" "+pr);
        StdOut.println("+"+Arrays.toString(a));
        
        
        // 标记 little than v的部分hi；great than v的部分lo
        int lt, gt;
        // 计算两侧分割点
        lt = j - (lo - pl + 1);
        gt = i + (hi - pr + 1);
        
        // 整理相同键
        for(int k = lo; j>pl && k<=pl ; j--, k++)
            exch(a, k, j);
        
        for(int k = hi; i<pr && k>=pr ; i++, k--)
            exch(a, k, i);
        
        StdOut.println("+"+Arrays.toString(a));
        StdOut.println("*"+lt+" "+gt);
        
        sort(a, lo, lt);
        sort(a, gt, hi);
        
    }
    
    // is v > w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean than(Comparable v, Comparable w) {
        return (v.compareTo(w) > 0);
    }
    
    // does v == w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean eq(Comparable v, Comparable w) {
        return (v.compareTo(w) == 0);
    }
        
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        int N = 10;
//        Comparable[] a = new Comparable[]{5, 4, 7, 5, 0, 2, 3, 1, 5, 6};
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = i;
        }
        StdRandom.shuffle(a);
        StdOut.println(Arrays.toString(a));
        sort(a, 0, N-1);
        StdOut.println(Arrays.toString(a));
    }

}
