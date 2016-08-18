package class0203;
/**
 * @Description 2.3.11
 *      快排跳过相等
 * @author Leon
 * @date 2016-08-18 16:09:47
 */
public class ScanOverEqual {
    
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
    
    public static void main(String[] args) {
        
    }

}
