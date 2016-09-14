package class0204;

import java.util.Arrays;
import java.util.Comparator;

import stdlib.StdOut;

/**
 * @Description 2.4.3
 *      unordered array, ordered array
 * @author Leon
 * @date 2016-09-14 16:34:34
 */
public class MaxArrayPQ<Item extends Comparable<Item>> {
    
    @SuppressWarnings("unchecked")
    private Item[] pq = (Item[]) new Comparable[1];
    private Comparator<Item> comparator;
    // pq[0] in no use , count from 1
    int N = 0;
    
    public MaxArrayPQ() {
        
    }
    
    @SuppressWarnings("unchecked")
    public MaxArrayPQ(int max) {
        pq = (Item[]) new Comparable[max]; 
    }
    
    @SuppressWarnings("unchecked")
    public MaxArrayPQ(Item[] a, boolean isOdd) {
        pq = (Item[]) new Comparable[a.length];
        for(Item i: a)
            if(isOdd)
                this.oddInsert(i);
            else
                this.uddInsert(i);
    }
    
    
    /** 有序插、删 */
    public void oddInsert(Item v) {
        if (N == pq.length)
            resize(2 * pq.length);
        pq[N++] = v;
        
        for(int i=N-1; i>0 && less(i, i-1); i--) 
            exch(i, i-1);
    }
    
    public Item oddMax() {
        return pq[N-1];
    }
    
    public Item oddDelMax() {
        Item max = pq[N-1];
        pq[N-1] = null;
        N--;
        
        if (N > 0 && N == pq.length / 4)
            resize(pq.length / 2);
        return max;
    }
    
    /** 无序插、删 */
    public void uddInsert(Item v) {
        if (N == pq.length)
            resize(2 * pq.length);
        pq[N++] = v;
    }
    
    public Item uddMax() {
        int max=0;
        for(int i=0; i<N; i++)
            max = less(max, i)? i: max;
        return pq[max];
    }
    
    public Item uddDelMax() {
        int max=0;
        for(int i=0; i<N; i++)
            max = less(max, i)? i: max;
        
        Item tmp = pq[max];
        exch(max, N-1);
        pq[N-1] = null;
        N--;
        
        if (N > 0 && N == pq.length / 4)
            resize(pq.length / 2);
        return tmp;
    }
    
    
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    private void resize(int max) { // Move stack to a new array of size max.
        @SuppressWarnings("unchecked")
        Item[] temp = (Item[]) new Comparable[max];
        for (int i = 0; i < N; i++)
            temp[i] = pq[i];
        pq = temp;
//      System.arraycopy(a, 0, temp, 0, a.length);
    }
    
    private boolean less(int i, int j) {
        if (comparator == null) {
            return ((Comparable<Item>) pq[i]).compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void exch(int i, int j) {
        Item swap = pq[i];
        pq[i] = pq[j];
        pq[j] = swap;
    }
    
    public String toString() {
        return "MaxArrayPQ [pq=" + Arrays.toString(pq) + ", N=" + N + "]";
    }
    
    public static void main(String[] args) {
        /*
        MaxArrayPQ<Integer> uddMax = new MaxArrayPQ<Integer>();
        uddMax.uddInsert(3);
        uddMax.uddInsert(1);
        uddMax.uddInsert(5);
        uddMax.uddInsert(4);
        uddMax.uddInsert(2);
        uddMax.uddDelMax();
        StdOut.println(uddMax.toString());
        StdOut.println(uddMax.uddMax());
        */
        MaxArrayPQ<Integer> oddMax = new MaxArrayPQ<Integer>();
        oddMax.oddInsert(3);
        oddMax.oddInsert(1);
        oddMax.oddInsert(5);
        oddMax.oddInsert(4);
        oddMax.oddInsert(2);
        oddMax.oddDelMax();
        StdOut.println(oddMax.toString());
        StdOut.println(oddMax.oddMax());
        
    }

}
