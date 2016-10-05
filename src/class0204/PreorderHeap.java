package class0204;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;

/**
 * @Description 2.4.42
 *      用前序代替层级序实现堆排
 * @author leon
 * @date 2016-10-05 11:59:35
 */
public class PreorderHeap<Key> implements Iterable<Key> {
    
    public Key[] pq;                    // store items at indices 1 to N
    public int N;                       // number of items on priority queue
    private Comparator<Key> comparator;  // optional Comparator

    @SuppressWarnings("unchecked")
    public PreorderHeap(int initCapacity) {
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    public PreorderHeap() {
        this(1);
    }

    @SuppressWarnings("unchecked")
    public PreorderHeap(int initCapacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Object[initCapacity + 1];
        N = 0;
    }

    public PreorderHeap(Comparator<Key> comparator) {
        this(1, comparator);
    }

    @SuppressWarnings("unchecked")
    public PreorderHeap(Key[] keys) {
        N = keys.length;
        pq = (Key[]) new Object[keys.length + 1];
        /*
        for (int i = 0; i < N; i++)
            pq[i+1] = keys[i];
        for (int k = N/2; k >= 1; k--)
            sink(k);
        */
        for (int i = 0; i < N; i++)
            insert(keys[i]);
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
        eat(1);
        
        assert isMaxHeap();
    }

    public Key delMax() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        Key max = pq[1];
//        exch(1, N--);
        spit(1);
        N--;
        pq[N+1] = null;     // to avoid loiterig and help with garbage collection
        if ((N > 0) && (N == (pq.length - 1) / 4)) resize(pq.length / 2);
        assert isMaxHeap();
        return max;
    }


   /***********************************************************************
    * Helper functions to restore the heap invariant.
    **********************************************************************/

    private void eat(int k) {
        /**
         * 用一方法自上而下路径查找k所应在位置
         * 全局变量保存？维护位置数组问题会变简单
         * 构建后序结构的递归来将其置位？
         */
        
    }

    private void spit(int k) {
        /**
         * 前序结构堆的沉降不像层级结构
         * 它有固定模式 像【贪吃蛇】
         * 递归不构结构不行？
         */
        /*
        // 越界返回
        if(k*2 > N)
            return;
        // 左节点向上到父节点
        pq[k] = pq[k*2];
        // 继续左子节点
        spit(k*2);
        
        // 越界返回
        if(k*2+1 > N)
            return;
        // 右节点到左节点
        pq[k*2] = pq[k*2+1];
        // 继续右子节点
        spit(k*2+1);
        */
    }

    /***********************************************************************
     * 结构打印
     **********************************************************************/
    public void print() {
        int k=0;
        for(int i=1; i > 0; i=Math.min(i*2, N - k)) {
            for(int j=i; j>0; j--)
                StdOut.print(pq[++k]+"   ");
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
        private PreorderHeap<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new PreorderHeap<Key>(size());
            else                    copy = new PreorderHeap<Key>(size(), comparator);
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
        PreorderHeap<Integer> ph = new PreorderHeap<Integer>();
        ph.pq = new Integer[]{null, 7, 6, 3, 5, 4, 2, 1};
        ph.N = 7;
        ph.print();
        ph.delMax();
        ph.print();
    }

}

