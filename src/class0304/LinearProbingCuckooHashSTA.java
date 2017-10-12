package class0304;

/**
 * @Description 3.4.31
 *      线性布谷鸟散列
 * @author Leon
 * @date 2017-10-12 17:16:13
 */
public class LinearProbingCuckooHashSTA<Key, Value> extends LinearHashST<Key, Value> {

    public LinearProbingCuckooHashSTA() {
        super();
    }
    
    public LinearProbingCuckooHashSTA(int capacity) {
        super(capacity);
    }
    
    public void putCuckoo(Key key, Value val, LinearProbingCuckooHashSTB<Key, Value> st) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);
/*
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return ;
            }
        }
        */
        int i = hash(key);
        if(keys[i] == null) {
            keys[i] = key;
            vals[i] = val;
            n++;
        } else {
            // 弹向B组
            Key k = keys[i];
            Value v = vals[i];
            keys[i] = key;
            vals[i] = val;
            st.putCuckoo(k, v, this);
        }
        
    }
    
    public boolean deleteCuckoo(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return false;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        vals[i] = null;

        // rehash all keys in same cluster
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
        
        return true;
    }
    
    @Override
    protected int hash(Key key) {
        return 11 * (key.hashCode() & 0x7fffffff) % m;
    }
    
    @Override
    protected void resize(int capacity) {
        LinearProbingCuckooHashSTA<Key, Value> temp = 
                new LinearProbingCuckooHashSTA<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }

}
