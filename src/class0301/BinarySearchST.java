package class0301;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 3.1.12/16/17/18/25[cache]
 *          二叉搜索符号表
 * @author Leon
 * @date 2016-12-15 16:28:00
 */
public class BinarySearchST<K extends Comparable<K>, V>{

    private int N = 0;
    private NameValuePair<K, V>[] nvps;
    
    // software cache
    private K cacheK = null;
    private V cacheV = null;
    
    @SuppressWarnings("unchecked")
    public BinarySearchST() {
        nvps = (NameValuePair<K, V>[])new NameValuePair[1];
    }
    
    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        nvps = (NameValuePair<K, V>[])new NameValuePair[capacity];
    }
    
    @SuppressWarnings("unchecked")
    public BinarySearchST(NameValuePair<K, V>[] src) {
        nvps = (NameValuePair<K, V>[])new NameValuePair[src.length];
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
    
    public V get(K key) {
        if (isEmpty())
            return null;
        
        if (key == cacheK)
            return cacheV;
        
        int i = rank(key);
        if (i < N && nvps[i].getKey().compareTo(key) == 0) {
            cacheK = key;
            cacheV = nvps[i].getValue();
            return nvps[i].getValue();
        } else
            return null;
    }

    public void put(K key, V val) {
        // if val is null that mean delete operation
        if (val == null) { remove(key); return; }
        
        if (N == nvps.length)
            resize(2 * nvps.length);
        int i = rank(key);
        if (i < N && nvps[i].getKey().compareTo(key) == 0) {
            nvps[i].setValue(val);
            return;
        }
        for (int j = N; j > i; j--) {
            nvps[j] = nvps[j - 1];
        }
        nvps[i] = new NameValuePair<K, V>(key, val);
        N++;
    }
    
    public void remove(K key) {
        int i = rank(key);
        if (i < N && nvps[i].getKey().compareTo(key) == 0) {
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
            int cmp = key.compareTo(nvps[mid].getKey());
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }
    
    public V ceiling(K key) {
        int i = rank(key);
        return i >= N? null: nvps[i].getValue();
    }
    
    public V floor(K key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(nvps[mid].getKey());
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return nvps[mid].getValue();
        }
        return hi < 0? null: nvps[hi].getValue();
    }
    
    private void resize(int max) {
        @SuppressWarnings("unchecked")
        NameValuePair<K, V>[] temp = (NameValuePair<K, V>[])new NameValuePair[max];
        for (int i = 0; i < N; i++)
            temp[i] = nvps[i];
        nvps = temp;
//      System.arraycopy(a, 0, temp, 0, a.length);
    }
    
    public void print() {
        for(int i=0; i<N; i++) 
            StdOut.println(nvps[i].getKey().toString() + "   " + nvps[i].getValue().toString());
        
    }
    
    public static void main(String[] args) {
        BinarySearchST<String, Integer> bst = new BinarySearchST<String, Integer>();
        bst.put("a", 1);
        bst.put("b", 2);
        bst.put("c", 3);
        bst.put("d", 4);
        bst.put("b", 1);
        bst.remove("a");
        bst.remove("c");
        bst.print();
        StdOut.println(bst.floor("a") == null);
        StdOut.println(bst.floor("c") == null);
        
        StdOut.println(bst.ceiling("c") == null);
        StdOut.println(bst.ceiling("e") == null);
    }

}

class NameValuePair<Key extends Comparable<Key>, Value> implements Comparable<NameValuePair<Key, Value>> {
    
    private Key key;
    private Value value;
    
    public NameValuePair(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
    
    public Key getKey() {
        return key;
    }
    
    public void setKey(Key key) {
        this.key = key;
    }
    
    public Value getValue() {
        return value;
    }
    
    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public int compareTo(NameValuePair<Key, Value> o) {
        return key.compareTo(o.getKey());
    }

}
