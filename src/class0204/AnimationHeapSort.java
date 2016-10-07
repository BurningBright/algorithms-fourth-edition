package class0204;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import class0201.AnimationSortCompare;
import stdlib.StdDraw;
import stdlib.StdRandom;

/**
 * @Description 
 *      堆排可视化
 * @author leon
 * @date 2016-10-07 19:19:47
 */
public class AnimationHeapSort {
    private Comparable<Object>[] pq;
    private int N;
    
    private final double offsetX = 0.0;
    private final double offsetY = 1.0;
    
    //the distance between select and insert sort
    private final double insertX = 13.0;
    //the distance between previous sort and current sort
    private final double insertY = 1.5;
    //record the current collumn num
    private int flagX = 0;
    //record the current row num
    private int flagY = 0;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void sortProcess(int N) {
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
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void sort(Comparable<Object>[] a, int lo, int hi) {
        N = a.length;
        pq = new Comparable[a.length + 1]; 
        pq[0] = (Comparable).0;
        for (int i = 0; i < N; i++)
            pq[i+1] = a[i];
        
        AnimationSortCompare.draw(pq, offsetX+insertX*flagX, offsetY-insertY*++flagY);
        for (int k = N/2; k >= 1; k--) {
            List<Integer> touched = new ArrayList<Integer>();
            sink(k, touched);
        }
        
        AnimationSortCompare.draw(pq, offsetX+insertX*flagX, offsetY-insertY*++flagY);
        ++flagY;
        
        for(int i=0; i<a.length; i++) {
            exch(1, N--);
            List<Integer> touched = new ArrayList<Integer>();
            int p = sink(1, touched);
            
            if(insertY*flagY > 12) {
                flagY = 0;
                flagX++;
            }
            AnimationSortCompare.draw(pq, offsetX+insertX*flagX, offsetY-insertY*++flagY, touched, p);
        }
    }
    

    /**
     * Is the priority queue empty?
     * @return true if the priority queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return N == 0;
    }

    /**
     * Returns the number of keys on the priority queue.
     * @return the number of keys on the priority queue
     */
    public int size() {
        return N;
    }

    // helper function to double the size of the heap array
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity > N;
        Comparable<Object>[] temp = (Comparable<Object>[]) new Object[capacity];
        for (int i = 1; i <= N; i++) temp[i] = pq[i];
        pq = temp;
    }


    /**
     * Removes and returns a largest key on the priority queue.
     * @return a largest key on the priority queue
     * @throws java.util.NoSuchElementException if priority queue is empty.
     */
    public Comparable<Object> delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Comparable<Object> max = pq[1];
        exch(1, N--);
//        sink(1);
        pq[N+1] = null;     // to avoid loiterig and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
//        assert isMaxHeap();
        return max;
    }


   /***********************************************************************
    * Helper functions to restore the heap invariant.
    **********************************************************************/

    private int sink(int k, List<Integer> touched) {
        int p = 1;
        
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            if (!less(k, j)) break;
            else
                p = j;
            touched.add(k);
            touched.add(j);
            exch(k, j);
            k = j;
        }
        
        return p;
    }

   /***********************************************************************
    * Helper functions for compares and swaps.
    **********************************************************************/
    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Comparable<Object> swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    
    public static void main(String[] args) {
        (new AnimationHeapSort()).sortProcess(24);
    }

}
