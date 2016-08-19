package class0203;

import java.util.Arrays;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.3.11
 *      快排跳过相等
 *      感觉效率不是最高
 * @author Leon
 * @date 2016-08-18 16:09:47
 */
public class ScanOverEqual {
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void sort(Comparable[] a, int lo, int hi) { 
      if (hi <= lo) return;
      
      int i = lo, j = hi + 1;
//      int lt = lo, gt = hi;
      
      Comparable v = a[lo];
      while (true) { 
          // 找比v大的元素
          while(v.compareTo(a[++i]) >= 0)
              if(i == hi) break;
          // 找比v小的元素
          while (v.compareTo(a[--j]) <= 0)
              if (j == lo) break;
          // ++i后成大组最小下标，--j后成小组最大下标
          if(i >= j) break;
//          StdOut.printf("%d\t%d\n", i, j);
          
          // 先整理两组
          exch(a, i, j);
          
      }
//      StdOut.println(Arrays.toString(a));
      
      // 整理相同元素
      for(int k=hi; k>= i; k--) 
          if(v.compareTo(a[k]) == 0)
              exch(a, k, i++);
      for(int k=lo+1; k<j+1; k++) 
          if(v.compareTo(a[k]) == 0)
              exch(a, k, j--);
      
      // put partitioning item v at a[j]
      exch(a, lo, j);
      
      // now, a[lo .. j-1] < a[j] = a[i-1] < a[i .. hi]
      
      sort(a, lo, j-1);
      sort(a, i, hi);
    }
    
    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }
    
    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        int N = 50;
        Comparable[] a = new Comparable[N];
        for(int i=0; i<N; i++) {
            a[i] = StdRandom.uniform(10);
        }
        StdOut.println(Arrays.toString(a));
        sort(a, 0, a.length-1);
        StdOut.println(Arrays.toString(a));
    }

}
