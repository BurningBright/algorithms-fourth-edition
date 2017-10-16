package class0305;

import java.util.Arrays;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 3.5.4/6
 *          原始类型散列
 * @author Leon
 * @date 2017-10-16 15:04:00
 */
public class HashSTdouble {
    private static final int INIT_CAPACITY = 4;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private double[] keys;      // the keys
    private boolean zero;    // keys[0] = .0 true/false

    public HashSTdouble() {
        this(INIT_CAPACITY);
    }

    public HashSTdouble(int capacity) {
        m = capacity;
        n = 0;
        keys = new double[m];
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(double key) {
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Double key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        HashSTdouble temp = new HashSTdouble(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != 0) {
                temp.put(keys[i]);
            }
        }
        keys = temp.keys;
        m    = temp.m;
    }

    public void put(double key) {
        
        if(key == .0){
            zero = true;
            return;
        }
        
        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        for (i = hash(key); keys[i] != .0 || zero; i = (i + 1) % m) 
            if (keys[i] ==key) 
                return;
            
        keys[i] = key;
        n++;
    }

    public Double get(double key) {
        for (int i = hash(key); keys[i] != .0 || zero; i = (i + 1) % m)
            if (keys[i] == key)
                return key;
        return null;
    }

    public void delete(double key) {
        if(key == .0) {
            zero = false;
            return;
        }
        
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (key != keys[i]) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = 0;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != 0 || zero) {
            // delete keys[i] an vals[i] and reinsert
            double keyToRehash = keys[i];
            keys[i] = 0;
            n--;
            put(keyToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

        assert check();
    }

    public Iterable<Double> keys() {
        Queue<Double> queue = new Queue<Double>();
        for (int i = 0; i < m; i++)
            if (keys[i] != 0 || zero) queue.enqueue(keys[i]);
        return queue;
    }

    // integrity check - don't check after each put() because
    // integrity not maintained during a delete()
    private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
            if (keys[i] == 0 || zero) continue;
            else if (get(keys[i]) != keys[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]));
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        HashSTdouble hst = new HashSTdouble();
        for(double i= .1; i<1; i+=.1) {
            hst.put(i);
        }
        StdOut.println(Arrays.toString(hst.keys));
        for(double i=1; i>0; i-=.1) {
            hst.delete(i);
        }
        StdOut.println(Arrays.toString(hst.keys));
        
    }
    
}
