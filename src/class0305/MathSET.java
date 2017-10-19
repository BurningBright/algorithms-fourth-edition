package class0305;

/**
 * @Description 3.5.17 集合运算
 * @author Leon
 * @date 2017-10-19 16:30:00
 */
public class MathSET<Key extends Comparable<Key>> {
    
    private int INIT = 4;
    private int n;
    private int m;
    private Key[] keys;
    private boolean[] flag;
    
    @SuppressWarnings("unchecked")
    MathSET(){
        keys = (Key[])new Object[INIT];
        flag = new boolean[INIT];
        m = INIT;
    }
    
    @SuppressWarnings("unchecked")
    MathSET(int capacity){
        keys = (Key[])new Object[capacity];
        flag = new boolean[capacity];
        m = capacity;
    }
    
    @SuppressWarnings("unchecked")
    MathSET(Key[] universe) {
        m = universe.length;
        keys = (Key[])new Object[m];
        flag = new boolean[m];
        for(int i=0; i<universe.length; i++) {
            if(universe[i] != null) {
                add(universe[i]);
                n++;
            }
        }
    }
    
    void add(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        if (n >= m/2) resize(2*m);
        
        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                flag[i] = false;
                return ;
            }
        }
        keys[i] = key;
        flag[i] = false;
        n++;
    }
    
    @SuppressWarnings("unchecked")
    Key[] keys() {
        Key[] tmp = (Key[])new Object[m];
        for(int i=0; i<m; i++) {
            if(!flag[i])
                tmp[i] = keys[i];
        }
        return tmp;
    }
    
    MathSET<Key> complement() {
        MathSET<Key> tmp = new MathSET<Key>(m);
        for(int i=0; i<m; i++) {
            if(!flag[i])
                tmp.add(keys[i]);
        }
        return tmp;
    }
    
    void union(MathSET<Key> a) {
        for(Key k: a.keys)
            add(k);
    }
    
    void intersection(MathSET<Key> a) {
        for(Key k: a.keys)
            delete(k);
    }
    
    void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        keys[i] = null;
        flag[i] = true;

        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (keys[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            Key   keyToRehash = keys[i];
            keys[i] = null;
            n--;
            add(keyToRehash);
            i = (i + 1) % m;
        }

        n--;

        // halves size of array if it's 12.5% full or less
        if (n > 0 && n <= m/8) resize(m/2);
    }
    
    boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key) && !flag[i])
                return true;
        return false;
    }
    
    boolean isEmpty() {
        return size() == 0;
    }
    
    int size() {
        return n;
    }
    
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
    
    private void resize(int capacity) {
        MathSET<Key> temp = new MathSET<Key>(capacity);
        for (int i = 0; i < m; i++) {
            if (keys[i] != null) {
                temp.add(keys[i]);
            }
        }
        keys = temp.keys;
        flag = temp.flag;
        m    = temp.m;
    }
    
    public static void main(String[] args) {

    }

}
