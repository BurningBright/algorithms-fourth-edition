package class0304;

/**
 * @Description 3.4.40
 *      线性探测散列分布
 * @author leon
 * @date 2017-10-12 16:34:37
 */
public class LinearProbingHashSTPlot<Key, Value> extends LinearHashST<Key, Value>{
    
    public LinearProbingHashSTPlot() {
        super();
    }
    
    public LinearProbingHashSTPlot(int capacity) {
        super(capacity);
    }
    
    private int CMP;
    
    public int put4Cmp(Key key, Value val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        
        CMP = 0;
        if (val == null) {
            delete(key);
            return CMP;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            CMP++;
            if (keys[i].equals(key)) {
                vals[i] = val;
                return CMP;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
        return CMP;
    }

    
    @Override
    protected void resize(int capacity) {
        LinearProbingHashSTPlot<Key, Value> temp = 
                new LinearProbingHashSTPlot<Key, Value>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }
        keys = temp.keys;
        vals = temp.vals;
        m    = temp.m;
    }
    
    protected int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
}
