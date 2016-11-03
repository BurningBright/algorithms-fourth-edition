package class0205;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import stdlib.StdOut;

/**
 * @Description 2.5.19
 *      计算两组元素的差异性，排序熵
 *      经过处理后的comparator，均唯一，!less==great
 * @author Leon
 * @date 2016-11-02 10:58:09
 */
public class KendallTauDistance {
    
    private static int COUNTER = 0;
    @SuppressWarnings("rawtypes")
    private static Comparator c;
    
    @SuppressWarnings("rawtypes")
    public static int calc(Comparable[] a, Comparable[] b) {
        
        if (a.length != b.length)
            throw new IllegalArgumentException("Compare impossible");
        int N = a.length;
        
        /* 将顺序样本整理为唯一对象 */
        Map<Comparable, Integer> counterA = new HashMap<Comparable, Integer>();
        Force[] forceA = new Force[N];
        for(int i=0; i<N; i++) {
            if(counterA.containsKey(a[i])) 
                forceA[i] = new Force(counterA.get(a[i])+1, a[i]);
            else
                forceA[i] = new Force(0, a[i]);
            counterA.put(a[i], forceA[i].index);
        }
        
        /* 将数据样本整理为唯一对象 */
        Map<Comparable, Integer> counterB = new HashMap<Comparable, Integer>();
        Force[] forceB = new Force[N];
        for(int i=0; i<N; i++) {
            if(counterB.containsKey(b[i])) 
                forceB[i] = new Force(counterB.get(b[i])+1, b[i]);
            else
                forceB[i] = new Force(0, b[i]);
            counterB.put(b[i], forceB[i].index);
        }
        
        /* 使用顺序样本生成目标对比器 */
        c = new KtdComparator(forceA);
        /* 排序中的交换次数即 Kendall Tau Distance*/
        sort(forceB, 0, N-1);
        
        //reset counter
        int tmp = COUNTER;
        COUNTER = 0;
        
        return tmp;
    }
    
    /** 比较器，对比数据在目标中的下标大小 */
    private static class KtdComparator implements Comparator<Force>{
        
        private Force[] src;
        private int N = 0;
        
        public KtdComparator(Force[] src) {
            this.src = src;
            N = src.length;
        }
        
        @Override
        public int compare(Force o1, Force o2) {
            
            int i1 = 0, i2 = 1;
            for (int i=0; i<N; i++)
                if (o1.equals(src[i])) { i1=i; break; }
            for (int i=0; i<N; i++)
                if (o2.equals(src[i])) { i2=i; break; }
            
            return i1 - i2;
        }

    }
    /** 强稳封装类 */
    private static class Force implements Comparable<Force>{
        int index;
        @SuppressWarnings("rawtypes")
        Comparable a;
        @SuppressWarnings("rawtypes")
        public Force(int index, Comparable a) {
            this.index = index;
            this.a = a;
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public int compareTo(Force o) {
            return this.a.compareTo(o.a);
        }
        
        // 经整理后的封装样本里是不可能事件
        public boolean equals(Force o) {
            return this.index == o.index && this.a.equals(o.a);
        }

        @Override
        public String toString() {
            return "Force [index=" + index + ", a=" + a + "]";
        }
        
    }
    
    @SuppressWarnings("rawtypes")
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
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
    
    /***********************************************************************
     *  Helper sorting functions
     ***********************************************************************/
     
    // is v < w ?
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static boolean less(Comparable v, Comparable w) {
        if (c != null)
            return c.compare(v, w) < 0;
        return (v.compareTo(w) < 0);
    }
    
    
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        COUNTER++;
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Comparable[] a = "Press Wait to give the application time to respond or to get a response from you.".split("");
        Comparable[] b = "Press Wait to get a response from you or to give the application time to respond.".split("");
        StdOut.println(calc(a, b));
        
        Comparable[] a1 = "0123456789".split("");
        Comparable[] b1 = "4012395678".split("");
        StdOut.println(calc(a1, b1));
        
    }

}
