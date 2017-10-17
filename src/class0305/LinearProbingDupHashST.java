package class0305;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import rlgs4.Queue;

/**
 * @Description 3.5.8
 *          保存多值的线性探索散列表
 * @author Leon
 * @date 2017-10-16 20:48:00
 */
public class LinearProbingDupHashST<Key, Value> {
    private static final int INIT_CAPACITY = 4;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private List<Value>[] vals;    // the values
    
    public LinearProbingDupHashST() {
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingDupHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (ArrayList<Value>[]) new ArrayList[m];
        
    }
    
    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    // resizes the hash table to the given capacity by re-hashing all of the keys
    private void resize(int capacity) {
        LinearProbingDupHashST<Key, Value> temp = 
                new LinearProbingDupHashST<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }

    public void put(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                if(vals[i] == null)
                    vals[i] = new ArrayList<Value>();
                // Set - no repeat
                if(!vals[i].contains(val))
                    vals[i].add(val);
                return;
            }
        }
        keys[i] = key;
        if(vals[i] == null)
            vals[i] = new ArrayList<Value>();
        vals[i].add(val);
        n++;
    }
    
    private void put(Key key, List<Value> vals) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (vals == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                this.vals[i] = vals;
                return;
            }
        }
        keys[i] = key;
        this.vals[i] = vals;
        n++;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key)) {
                int r = new Random().nextInt(vals[i].size());
                return vals[i].get(r);
            }
        return null;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        if(vals[i] != null)
            vals[i].clear();
        vals[i] = null;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            List<Value> valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

        assert check();
    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.enqueue(keys[i]);
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
            if (keys[i] == null) continue;
            else if (get(keys[i]) != vals[i]) {
                System.err.println("get[" + keys[i] + "] = " + get(keys[i]) + "; vals[i] = " + vals[i]);
                return false;
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        LinearProbingDupHashST<String, String> lst = 
                new LinearProbingDupHashST<String, String>();
        lst.put("1", "a");
        lst.put("1", "a");
        lst.put("1", "b");
        lst.put("2", "c");
        lst.put("2", "d");
        lst.put("3", "e");
        
        lst.delete("2");
        
        System.out.println(lst.get("1"));
        System.out.println(lst.get("1"));
        System.out.println(lst.get("1"));
        System.out.println(lst.get("1"));
        System.out.println(lst.get("1"));
        
        System.out.println(lst.get("2"));
        System.out.println(lst.get("3"));
        
        for(String a: lst.keys())
            System.out.println(a);
        
    }

}
