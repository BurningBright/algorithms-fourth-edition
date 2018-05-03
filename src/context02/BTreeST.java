package context02;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description context02.16 
 *              B-tree 符号表
 * @author Leon
 * @date 2018-05-03 14:00:00
 */
public class BTreeST<Key extends Comparable<Key>, Value> {

    private int M = 5;
    private PageST<Key, Value> root = new PageST<Key, Value>(true, M);

    // create an ordered symbol table
    @SuppressWarnings("unchecked")
    BTreeST() {
        put((Key)"*", null);
    }

    // put key-value pair into the table (remove key from table if value is
    // null)
    public void put(Key key, Value val) {
        put(root, key, val);
        if (root.isFull()) {
            PageST<Key, Value> lefthalf = root;
            PageST<Key, Value> righthalf = root.split();
            root = new PageST<Key, Value>(false, M);
            root.put(lefthalf);
            root.put(righthalf);
        }
    }
    
    private void put(PageST<Key, Value> h, Key key, Value val) {
        if (h.isExternal()) {
            h.put(key, val);
            return;
        }
        PageST<Key, Value> next = h.next(key);
        put(next, key, val);
        if (next.isFull())
            h.put(next.split());
        next.close();
    }

    // value paired with key (null if key is absent)
    Value get(Key key) {
        return null;
    }

    // remove key (and its value) from table
    void delete(Key key) {
    }

    // is there a value paired with key?
    boolean contains(Key key) {
        return false;
    }

    // is the table empty?
    boolean isEmpty() {
        return false;
    }

    // number of key-value pairs
    int size() {
        return 0;
    }

    // smallest key
    Key min() {
        return min(root);
    }

    private Key min(PageST<Key, Value> node) {
        Key key = node.min();
        PageST<Key, Value> next = node.next(key);
        if (next == null) return key;
        return min(next);
    }

    // largest key
    Key max() {
        return null;
    }

    // largest key less than or equal to key
    Key floor(Key key) {
        return null;
    }

    // smallest key greater than or equal to key
    Key ceiling(Key key) {
        return null;
    }

    // number of keys less than key
    int rank(Key key) {
        return 0;
    }

    // key of rank k
    Key select(int k) {
        return null;
    }

    // delete smallest key
    void deleteMin() {
    }

    // delete largest key
    void deleteMax() {
    }

    // number of keys in [lo..hi]
    int size(Key lo, Key hi) {
        return 0;
    }

    // keys in [lo..hi], in sorted order
    Iterable<Key> keys(Key lo, Key hi) {
        return null;
    }

    // all keys in the table, in sorted order
    Iterable<Key> keys() {
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<PageST<Key, Value>> q = new Queue<PageST<Key, Value>>();
        q.enqueue(root);
        int prv = 1;
        while(!q.isEmpty()) {
            PageST<Key, Value> p = q.dequeue();
            sb.append(p.toString()).append(" | ");
            prv--;
            for (PageST<Key, Value> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                sb.append("\r\n");
                prv = q.size();
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        BTreeST<String, String> st = new BTreeST<String, String>();
        String keys = "A B C D E F G H I J K L M N O P Q R S T";
        String vals = "a b c d e f g h i j k l m n o p q r s t";
        String[] keysArray = keys.split(" ");
        String[] valsArray = vals.split(" ");
//        StdRandom.shuffle(keysArray);
        for (int i=0; i<20; i++) 
            st.put(keysArray[i], valsArray[i]);
        
        StdOut.println(st);
    }

}
