package class0203;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 2.3.16
 *      产生快排的最佳案例
 * @author Leon
 * @date 2016-08-26 15:59:03
 */
public class BestCase {
    
    public static int[] bestCase(int N) {
        
        int[] a = new int[N];
        for(int i=0; i<N; i++) 
            a[i] = i;
        
        create(a, 0, a.length-1);
        return a;
    }
    
    @SuppressWarnings("unused")
    private static void createOne(int[] a, int lo, int hi) {
        // base case
        if(hi <= lo) return;
        
        // find middle
        int j = lo + (hi - lo) / 2;
        
        // rerange array left
        int tmp = a[j];
        for(int i=j; i>lo; i--)
            a[i] = a[i-1];
        a[lo] = tmp;
        
        // rerange array right
        tmp = a[hi];
        for(int i=hi; i>j+1; i--)
            a[i] = a[i-1];
        a[j+1] = tmp;
        
        for(int i=lo+1, k=hi; i<k; i++, k--)
            exch(a, i, k);
        
//        StdOut.println(Arrays.toString(a)+"-"+lo+"-"+j+"-"+hi);
        // create left part
        createOne(a, lo+1, j);
        // create right part
        createOne(a, j+1, hi);
    }
    
    // 把算法2.5倒过来
    private static void create(int[] a, int lo, int hi) {
        if(hi <= lo) return;
        int j = lo + (hi - lo) / 2;
        
        // create right part
        create(a, j+1, hi);
        create(a, lo, j);
        
        merge(a, lo, j, hi);
        StdOut.println(Arrays.toString(a)+"-"+lo+"-"+j+"-"+hi);
    }
    
    private static void merge(int[] a, int lo, int j, int hi) {
        if(hi - lo == 1) {exch(a, lo, hi); return;}
        
        /*
         *  如果左边大取左边最大的
         *  如果右边大取右边最小的
         */
        if (j - lo + 1 >= hi - j) {
            int max = lo;
            for(int i=lo+1; i<=j; i++)
                max = a[i] > a[max]? i: max;
            exch(a, lo, max);
        } else {
            int min = j+1;
            for(int i=j+2; i<=hi; i++)
                min = a[i] < a[min]? i: min;
            
            int tmp = a[min];
            for(int i=min; i>lo; i--)
                a[i] = a[i-1];
            a[lo] = tmp;
        }
        
        // 换左右部分
        for(int i=lo+1, k=hi; i<=j; i++, k--)
            exch(a, i, k);
    }
    
    private static void exch(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    
    public static void main(String[] args) {
        StdOut.println(Arrays.toString(bestCase(9)));
        /*
        int[] a = new int[] { 1, 3, 4, 0, 2, 5, 7, 8, 6 };
        merge(a, 0, 4, 8);
        StdOut.println(Arrays.toString(a));
        */
    }

}
