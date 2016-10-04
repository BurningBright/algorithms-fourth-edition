package class0204;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import rlgs4.MaxPQ;
import rlgs4.Stopwatch;
import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * @Description 2.4.40
 *      弗洛伊德优先队列排列法
 *      分别对比最大优先队列在：
 *      综合操作，构建、删最大操作上的性能差距
 *      确实快不少
 * @author leon
 * @date 2016-10-04 18:12:56
 */
public class FloydMaxPQ<Key> implements Iterable<Key> {
    
    private Key[] pq;                    // store items at indices 1 to N
    private int N;                       // number of items on priority queue
    private Comparator<Key> comparator;  // optional Comparator

    @SuppressWarnings("unchecked")
    public FloydMaxPQ(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    public FloydMaxPQ() {
        this(1);
    }

    @SuppressWarnings("unchecked")
    public FloydMaxPQ(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    public FloydMaxPQ(Comparator<Key> comparator) {
        this(1, comparator);
    }

    @SuppressWarnings("unchecked")
    public FloydMaxPQ(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Object[keys.length + 1]; 
        for (int i = 0; i < N; i++)
            pq[i+1] = keys[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
        assert isMaxHeap();
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }
    // helper function to double the size of the heap array
    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        assert capacity > N;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= N; i++) temp[i] = pq[i];
        pq = temp;
    }

    public void insert(Key x) {
        
        // double size of array if necessary
        if (N >= pq.length - 1) resize(2 * pq.length);
        
        // add x, and percolate it up to maintain heap invariant
        pq[++N] = x;
        swim(N);
        assert isMaxHeap();
    }

    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null;     // to avoid loiterig and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }


   /***********************************************************************
    * Helper functions to restore the heap invariant.
    **********************************************************************/

    private void swim(int k) {
        while (k > 1 && less(k/2, k)) {
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        /**
         * 传统堆结构下沉需要判断是否到底、是否在正确位置
         * 改进方法做的是不管是不是在正确位置、直接交换到底
         * 再从正确的分支上向上浮动
         */
        /*
        while (2*k <= N) {
            int j = 2*k;
            if (j < N && less(j, j+1)) j++;
            // 将这1步转换为为一系列上浮
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
        */
        int i = k;
        while (2*i <= N) {
            int j = 2*i;
            if (j < N && less(j, j+1)) j++;
            exch(i, j);
            i = j;
        }
        /** 交换用插排的优化法还能更进一步 */
        swim(i);
    }

    /***********************************************************************
     * 结构打印
     **********************************************************************/
    public void print() {
        int k=0;
        for(int i=1; i > 0; i=Math.min(i*2, N - k)) {
            for(int j=i; j>0; j--)
                StdOut.printf("%.2f   ", pq[++k]);
            StdOut.println();
        }
//        StdOut.println(Arrays.toString(pq));
    }

   /***********************************************************************
    * Helper functions for compares and swaps.
    **********************************************************************/
    @SuppressWarnings("unchecked")
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Key>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Key swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }

    // is pq[1..N] a max heap?
    private boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    // is subtree of pq[1..N] rooted at k a max heap?
    private boolean isMaxHeap(int k) {
        if (k > N) return true;
        int left = 2*k, right = 2*k + 1;
        if (left  <= N && less(k, left))  return false;
        if (right <= N && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }


   /***********************************************************************
    * Iterator
    **********************************************************************/

    /**
     * Returns an iterator that iterates over the keys on the priority queue
     * in descending order.
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     * @return an iterator that iterates over the keys in descending order
     */
    public Iterator<Key> iterator() { return new HeapIterator(); }

    private class HeapIterator implements Iterator<Key> {

        // create a new pq
        private FloydMaxPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new FloydMaxPQ<Key>(size());
            else                    copy = new FloydMaxPQ<Key>(size(), comparator);
            for (int i = 1; i <= N; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMax();
        }
    }
    
    public static void main(String[] args) {
        int N = 5000000;
//        Double[] test = {.1, .2, .3, .4, .5};
        Double[] a = new Double[N];
        for(int i=0; i<N; i++)
            a[i] = StdRandom.uniform();
        Double[] b = a.clone();
        
        /** group A */
        Stopwatch sw = new Stopwatch();
        FloydMaxPQ<Double> fm = new FloydMaxPQ<Double>();
        for(int i=0; i<N; i++)
            fm.insert(a[i]);
        for(int i=0; i<N; i++)
            fm.delMax();
        StdOut.printf("g-a fm %.2f\n", sw.elapsedTime());
        
        sw = new Stopwatch();
        MaxPQ<Double> pm = new MaxPQ<Double>();
        for(int i=0; i<N; i++)
            pm.insert(a[i]);
        for(int i=0; i<N; i++)
            pm.delMax();
        StdOut.printf("g-a om %.2f\n", sw.elapsedTime());
        
        /** group B */
        sw = new Stopwatch();
        fm = new FloydMaxPQ<Double>(a);
        for(int i=0; i<N; i++)
            fm.delMax();
        StdOut.printf("g-b fm %.2f\n", sw.elapsedTime());
        
        sw = new Stopwatch();
        pm = new MaxPQ<Double>(b);
        for(int i=0; i<N; i++)
            pm.insert(a[i]);
        for(int i=0; i<N; i++)
            pm.delMax();
        StdOut.printf("g-b om %.2f\n", sw.elapsedTime());
        
    }

}
