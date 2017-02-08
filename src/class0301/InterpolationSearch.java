package class0301;

import java.util.Arrays;

import stdlib.StdOut;

/**
 * @Description 3.1.24
 *      anticipate where the key is
 *      key must support arithmetic operations
 *      like Integer, Long, Float, Double
 *      
 *      rank result is the position that 
 *      if key is in this source, it's space should be located.
 *      
 * @author Leon
 * @date 2017-02-07 15:36:22
 */
public class InterpolationSearch <K extends Number, V>{

    private int N = 0;
    private ItrNameValuePair<K, V>[] nvps;
    Integer i;
    @SuppressWarnings("unchecked")
    public InterpolationSearch() {
        nvps = (ItrNameValuePair<K, V>[])new ItrNameValuePair[1];
    }
    
    @SuppressWarnings("unchecked")
    public InterpolationSearch(int capacity) {
        nvps = (ItrNameValuePair<K, V>[])new ItrNameValuePair[capacity];
    }
    
    @SuppressWarnings("unchecked")
    public InterpolationSearch(ItrNameValuePair<K, V>[] src) {
        nvps = (ItrNameValuePair<K, V>[])new ItrNameValuePair[src.length];
        System.arraycopy(nvps, 0, src, 0, src.length);
        Arrays.sort(nvps);
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
        int i = rank(key);
        if (i < N && compare(nvps[i].getKey(), key) == 0)
            return nvps[i].getValue();
        else
            return null;
    }

    public void put(K key, V val) {
        if (N == nvps.length)
            resize(2 * nvps.length);
        int i = rank(key);
        if (i < N && compare(nvps[i].getKey(), key) == 0) {
            nvps[i].setValue(val);
            return;
        }
        for (int j = N; j > i; j--) {
            nvps[j] = nvps[j - 1];
        }
        nvps[i] = new ItrNameValuePair<K, V>(key, val);
        N++;
    }
    
    public void remove(K key) {
        int i = rank(key);
        if (i < N && compare(nvps[i].getKey(), key) == 0) {
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
        
        // mid comes through proportion predict calculation
        
        int lo = 0, hi = N - 1;
        K loK = nvps[lo].getKey(), hiK = nvps[hi].getKey();
        
        if (compare(key, loK) <= 0)
            return 0;
        
        while (lo <= hi) {
            int mid = lo + (hi - lo)
                    * (int) ((key.doubleValue() - loK.doubleValue()) / (hiK.doubleValue() - loK.doubleValue()));
            int cmp = compare(key, nvps[mid].getKey());
            if (cmp < 0) {
                hi = mid - 1;
                hiK = nvps[hi].getKey();
            } else if (cmp > 0) {
                lo = mid + 1;
                loK = nvps[lo].getKey();
            } else
                return mid;
        }
        return lo;
        
        /*
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(key, nvps[mid].getKey());
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
        */
    }
    
    public V ceiling(K key) {
        int i = rank(key);
        return i >= N? null: nvps[i].getValue();
    }
    
    public V floor(K key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = compare(key, nvps[mid].getKey());
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
        ItrNameValuePair<K, V>[] temp = (ItrNameValuePair<K, V>[])new ItrNameValuePair[max];
        for (int i = 0; i < N; i++)
            temp[i] = nvps[i];
        nvps = temp;
//      System.arraycopy(a, 0, temp, 0, a.length);
    }
    
    private int compare(K k1, K k2) {
        double ret = k1.doubleValue()-k2.doubleValue();
        return ret > 0 ? 1: (ret < 0 ? -1: 0);
    }
    
    public void print() {
        for(int i=0; i<N; i++) 
            StdOut.println(nvps[i].getKey().toString() + "   " + nvps[i].getValue().toString());
        
    }
    
    public static void main(String[] args) {
        BinarySearchST<Long, Integer> bst = new BinarySearchST<Long, Integer>();
        bst.put(1l, 1);
        bst.put(2l, 2);
        bst.put(3l, 3);
        bst.put(4l, 4);
        bst.put(2l, 1);
        bst.remove(1l);
        bst.remove(3l);
        bst.print();
        StdOut.println(bst.floor(1l) == null);
        StdOut.println(bst.floor(3l) == null);
        
        StdOut.println(bst.ceiling(3l) == null);
        StdOut.println(bst.ceiling(5l) == null);
    }
    
}

class ItrNameValuePair<Key extends Number, Value> {
    
    private Key key;
    private Value value;
    
    public ItrNameValuePair(Key key, Value value) {
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

}
