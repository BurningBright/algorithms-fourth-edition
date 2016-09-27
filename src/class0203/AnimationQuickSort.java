package class0203;

import java.util.ArrayList;
import java.util.List;

import class0201.AnimationSortCompare;
import stdlib.StdDraw;
import stdlib.StdRandom;
/**
 * @Description 
 *      快排可视化
 * @author leon
 * @date 2016-09-27 23:04:23
 */
public class AnimationQuickSort {
    
    private static final double offsetX = 0.0;
    private static final double offsetY = 1.0;
    
    //the distance between select and insert sort
    private static final double insertX = 13.0;
    //the distance between previous sort and current sort
    private static final double insertY = 1.5;
    //record the current collumn num
    private static int flagX = 0;
    //record the current row num
    private static int flagY = 0;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void sortProcess(int N) {
        AnimationSortCompare.internalX = .20;
        AnimationSortCompare.halfWidth = .13;
        
        StdDraw.setCanvasSize(1024, 768);
        StdDraw.setXscale(0, 40.0);
        StdDraw.setYscale(-13.0, 0);
        
        Comparable<Object>[] src = (Comparable[]) new Double[N];
        for (int i = 0; i < N; i++) {
            src[i] = (Comparable)(StdRandom.uniform());
        }
        
        sort(src, 0, src.length-1);
    }
    
    @SuppressWarnings("rawtypes")
    public static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        
//        if(insertY*flagY > 12) {
//            flagY = 0;
//            flagX++;
//        }
//        AnimationSortCompare.draw(a, offsetX+insertX*flagX, offsetY-insertY*++flagY);
//        int j = CountExactCn.partition(a, lo, hi);
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        
    }
    @SuppressWarnings("rawtypes")
    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        List<Integer> touched = new ArrayList<Integer>();
        while (true) { 
            
            // find item on lo to swap
            touched.add(i+1);
            while (less(a[++i], v)) {
                touched.add(i);
                if (i == hi) break;
            }
            // find item on hi to swap
            touched.add(j-1);
            while (less(v, a[--j])) {
                touched.add(j);
                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            }
            // check if pointers cross
            if (i >= j) break;
            
            exch(a, i, j);
        }

        // put partitioning item v at a[j]
        exch(a, lo, j);
        
        if(insertY*flagY > 12) {
          flagY = 0;
          flagX++;
        }
        
        AnimationSortCompare.draw(a, offsetX+insertX*flagX, offsetY-insertY*++flagY, touched, j);
        
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
        sortProcess(35);
    }

}
