package context02;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description context02.16 
 *              B-tree 符号表
 *              部分方法不够优雅
 * @author Leon
 * @date 2018-05-03 14:00:00
 */
public class BTreeST<Key extends Comparable<Key>, Value> {

    private int M = 5;
    private PageST<Key, Value> root = new PageST<Key, Value>(true, M);

    // create an ordered symbol table
    @SuppressWarnings("unchecked")
    public BTreeST() {
        put((Key)"*", null);
    }

    // put key-value pair into the table (remove key from table if value is null)
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
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(PageST<Key, Value> h, Key key) {
        if (h.isExternal())
            return h.get(key);
        return get(h.next(key), key);
    }

    // remove key (and its value) from table
    public void delete(Key key) {
        delete(root, key);
    }
    
    public Key delete(PageST<Key, Value> page, Key key) {
        // 找到key所在的底层节点并删除
        if (page.isExternal()) {
            Key min = page.min();
            page.delete(key);
            // 删除路径上的空节点
            return key.equals(min)? page.min(): null;
        } else {
            Key dkey = delete(page.next(key), key);
            if (dkey != null) {
                page.delete(key, dkey);
                return dkey;
            }
        }
        return null;
    }

    // is there a value paired with key?
    boolean contains(Key key) {
        return contains(root, key) != null? true: false;
    }

    private PageST<Key, Value> contains(PageST<Key, Value> h, Key key) {
        if (h.isExternal())
            return h.contains(key)? h: null;
        return contains(h.next(key), key);
    }

    // is the table empty?
    boolean isEmpty() {
        return size() == 0;
    }

    // number of key-value pairs
    int size() {
        return size(min(), max());
    }
    
    // number of keys in [lo..hi]
    int size(Key lo, Key hi) {
        Queue<PageST<Key, Value>> q = new Queue<PageST<Key, Value>>();
        q.enqueue(root);
        while (!q.peek().isExternal()) {
            PageST<Key, Value> p = q.dequeue();
            for (PageST<Key, Value> pa: p.pages())
                q.enqueue(pa);
        }
        Queue<Key> keys = new Queue<Key>();
        while (!q.isEmpty()) {
            PageST<Key, Value> p = q.dequeue();
            for (Key k: p.keys())
                if (k.compareTo(lo) >= 0 && k.compareTo(hi) <= 0)
                    keys.enqueue(k);
        }
        return keys.size();
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
        return max(root);
    }
    
    private Key max(PageST<Key, Value> node) {
        Key key = node.max();
        PageST<Key, Value> next = node.next(key);
        if (next == null) return key;
        return max(next);
    }

    // largest key less than or equal to key
    public Key floor(Key key) {
        return floor(root, key);
    }
    
    private Key floor(PageST<Key, Value> page, Key key) {
        if (page.isExternal())
            return page.floor(key);
        // 不断寻找当前页中，不大于floor的最大键
        Key cur = page.floor(key);
        if (cur == null) return null; 
        return floor(page.next(cur), key);
    }

    // smallest key greater than or equal to key
    public Key ceiling(Key key) {
        return ceiling(root, key);
    }

    private Key ceiling(PageST<Key, Value> page, Key key) {
        if (page.isExternal())
            return page.ceiling(key);
        Key cur = page.ceiling(key);
        // 不断寻找当前页中，不小于ceiling的最小键
        if (cur == null) return null; 
        return ceiling(page.next(cur), key);
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
    public void deleteMin() {
        deleteMin(root);
    }

    private boolean deleteMin(PageST<Key, Value> page) {
        // 找到最下层，最左位置的节点并删除
        if (page.isExternal()) {
            page.delete(page.min());
        } else {
            Key key = page.min();
            boolean isDelete = deleteMin(page.next(key));
            if (isDelete)
                page.delete(key);
        }
        if (page.isEmpty())
            return true;
        return false;
    }
    
    // delete largest key
    public void deleteMax() {
        deleteMax(root);
    }

    private boolean deleteMax(PageST<Key, Value> page) {
        // 找到最下层，最右位置的节点并删除
        if (page.isExternal()) {
            page.delete(page.max());
        } else {
            Key key = page.max();
            boolean isDelete = deleteMax(page.next(key));
            if (isDelete)
                page.delete(key);
        }
        if (page.isEmpty())
            return true;
        return false;
    }

    // keys in [lo..hi], in sorted order
    Iterable<Key> keys(Key lo, Key hi) {
        
        Queue<PageST<Key, Value>> q = new Queue<PageST<Key, Value>>();
        q.enqueue(root);
        while (!q.peek().isExternal()) {
            PageST<Key, Value> p = q.dequeue();
            for (PageST<Key, Value> pa: p.pages())
                q.enqueue(pa);
        }
        Queue<Key> keys = new Queue<Key>();
        while (!q.isEmpty()) {
            PageST<Key, Value> p = q.dequeue();
            for (Key k: p.keys())
                if (k.compareTo(lo) >= 0 && k.compareTo(hi) <= 0)
                    keys.enqueue(k);
        }
        return keys;
        
    }

    // all keys in the table, in sorted order
    Iterable<Key> keys() {
        return keys(min(), max());
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
        
        StdOut.println(st.get("L"));
        
        st.deleteMax();
        StdOut.println(st);
        st.deleteMax();
        StdOut.println(st);
        st.deleteMax();
        StdOut.println(st);
        
        StdOut.println(st.get("D"));
        
        st.deleteMin();
        StdOut.println(st);
        st.deleteMin();
        StdOut.println(st);
        st.deleteMin();
        StdOut.println(st);
        
        st.delete("D");
        StdOut.println(st);
        StdOut.println(st.get("D"));
        
        StdOut.println(st.floor("D"));
        StdOut.println(st.ceiling("D"));
        
        StdOut.println(st.keys());
        StdOut.println(st.keys("G", "M"));
        
        StdOut.println(st.size());
        StdOut.println(st.size("G", "M"));
    }

}
