package class0305;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 3.5.3
 *          二叉搜索集合
 * @author Leon
 * @date 2017-10-16 14:04:00
 */
public class BinarySearchSET<K extends Comparable<K>>{

    private int N = 0;
    private K[] nvps;
    
    // software cache
    private K cacheK = null;
    
    @SuppressWarnings("unchecked")
    public BinarySearchSET() {
        nvps = (K[])new Object[1];
    }
    
    @SuppressWarnings("unchecked")
    public BinarySearchSET(int capacity) {
        nvps = (K[])new Object[capacity];
    }
    
    @SuppressWarnings("unchecked")
    public BinarySearchSET(K[] src) {
        nvps = (K[])new Object[src.length];
        System.arraycopy(nvps, 0, src, 0, src.length);
        Arrays.sort(nvps);
    }
    
    public boolean contains(K key) {
        return get(key) != null;
    }
    
    public int size() {
        return N;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public K get(K key) {
        if (isEmpty())
            return null;
        
        if (key == cacheK)
            return cacheK;
        
        int i = rank(key);
        if (i < N && nvps[i].compareTo(key) == 0) {
            cacheK = key;
            return nvps[i];
        } else
            return null;
    }

    public void put(K key) {
        if (N == nvps.length)
            resize(2 * nvps.length);
        int i = rank(key);
        if (i < N && nvps[i].compareTo(key) == 0)
            return;
        
        for (int j = N; j > i; j--) {
            nvps[j] = nvps[j - 1];
        }
        nvps[i] = key;
        N++;
    }
    
    public void remove(K key) {
        int i = rank(key);
        if (i < N && nvps[i].compareTo(key) == 0) {
            nvps[i] = null;
            
            for (int j = i; j < N; j++) {
                nvps[j] = nvps[j + 1];
            }
            
            N--;
        }
        if (N > 0 && N == nvps.length / 4)
            resize(nvps.length / 2);
    }
    
    public int rank(K key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(nvps[mid]);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }
    
    public K ceiling(K key) {
        int i = rank(key);
        return i >= N? null: nvps[i];
    }
    
    public K floor(K key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(nvps[mid]);
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return nvps[mid];
        }
        return hi < 0? null: nvps[hi];
    }
    
    private void resize(int max) {
        @SuppressWarnings("unchecked")
        K[] temp = (K[])new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = nvps[i];
        nvps = temp;
//      System.arraycopy(a, 0, temp, 0, a.length);
    }
    
    public void print() {
        for(int i=0; i<N; i++) 
            StdOut.println(nvps[i].toString());
    }

}
