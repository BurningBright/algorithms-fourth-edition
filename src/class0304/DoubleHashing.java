package class0304;

/**
 * @Description 3.4.28
 *      Double hash function LinearProbingHashST
 * @author Leon
 * @date 2017-10-08 15:57:13
 */
public class DoubleHashing <Key extends Comparable<Key>, Value> {
    
    private static final int INIT_CAPACITY = 4;

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values

    public DoubleHashing() {
        this(INIT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public DoubleHashing(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
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
    
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        int hashA = hashA(key);
        for (int i = hashA; keys[i] != null; i = (i + hashA) % m)
            if (keys[i].equals(key))
                return vals[i];
        
        int hashB = hashB(key);
        for (int i = hashB; keys[i] != null; i = (i + hashB) % m)
            if (keys[i].equals(key))
                return vals[i];
        
        return null;
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
        for (i = hashA(key); keys[i] != null; i = (i + hashA(key)) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        for (i = hashB(key); keys[i] != null; i = (i + hashB(key)) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        
        keys[i] = key;
        vals[i] = val;
        n++;
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // step forward find position i of key
        int i = hashA(key);
        while (i<m && !key.equals(keys[i])) {
            i = (i + hashA(key)) % m;
        }
        if( i == m) {
            i = hashB(key);
            while (i<m && !key.equals(keys[i])) {
                i = (i + hashB(key)) % m;
            }
        }
        
        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // not rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
        n--;
        
        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);

        // assert check();
    }
    
    private void resize(int capacity) {
        DoubleHashing<Key, Value> temp = new DoubleHashing<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }
    
    private int hashA(Key key) {
        return 11 * (key.hashCode() & 0x7fffffff) % m;
    }
    
    private int hashB(Key key) {
        return 17 * (key.hashCode() & 0x7fffffff) % m;
    }
    
    public static void main(String[] args) {

    }

}
