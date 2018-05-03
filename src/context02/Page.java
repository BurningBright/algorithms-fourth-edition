package context02;

import rlgs4.Queue;

/**
 * @Description context02.15
 *              集合页
 * @author Leon
 * @date 2018-05-01 14:00:00
 */
public class Page<Key extends Comparable<Key>> {
    
    private Key[]       keys;
    private Page<Key>[] pages;
    private boolean     isExternal;
    private int         M;
    private int         n;
    
    // create and open a page
    @SuppressWarnings("unchecked")
    Page(boolean bottom, int M) {
        this.M = M;
        isExternal = bottom;
        keys = (Key[])new Comparable[M];
        pages = new Page[M];
    }
    
    // close a page
    void close() {
        
    }
    
    // put key into the (external) page
    void put(Key key) {
        if (key == null) return;
        int flag = 0;
        for (int i=0; i<M; i++) {
            if (keys[i]!= null && eq(key, keys[i]))
                return;
            if (keys[i]!= null && more(key, keys[i]))
                flag++;
        }
        for (int i=M-1; i>flag; i--)
            keys[i] = keys[i-1];
        keys[flag] = key;
        n++;
    }
    
    // open p and put an entry into this (internal) page 
    // that associates the smallest key in p with p
    void put(Page<Key> p) {
        if (p == null) return;
        
        Key key = p.keys().iterator().next();
        boolean isAdd = false;
        int flag = 0;
        for (int i=0; i<M; i++) {
            if (n == 0) {
                isAdd = true;
                break;
            }
            if (keys[i] != null && eq(key, keys[i])) {
                isAdd = false;
                flag = i;
                break;
            }
            if (keys[i] != null && more(key, keys[i])) {
                flag ++;
                isAdd = true;
            }
        }
        for (int i=M-1; i>flag && isAdd ; i--) {
            keys[i] = keys[i-1];
            pages[i] = pages[i-1];
        }
        if (isAdd) n++;
        keys[flag] = key;
        pages[flag] = p;
    }
    
    // is this page external?
    boolean isExternal() {
        return isExternal;
    }
    
    // is key in the page?
    boolean contains(Key key) {
        for (int i=0; i<M; i++)
            if (keys[i] != null && eq(key, keys[i]))
                return true;
        return false;
    }
    
    // the subtree that could contain the key
    Page<Key> next(Key key) {
        for (int i=0; i<M; i++) {
            if (keys[i] == null || less(key, keys[i]))
                return pages[i-1];
            if (i == M-1)
                return pages[i];
        }
        return null;
    }
    
    // has the page overﬂowed?
    boolean isFull() {
        return n == M;
    }
    
    int size() {
        return n;
    }
    
    // move the highest-ranking half of the keys in the page to a new page
    Page<Key> split() {
        Page<Key> another = new Page<Key>(isExternal, M);
        for (int i=M/2; i < M && keys[i] != null; i++) {
            another.put(keys[i]);
            another.put(pages[i]);
            keys[i] = null;
            pages[i] = null;
            n--;
        }
        return another;
    }
    
    Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (int i=0; i<M; i++)
            if (keys[i] != null)
                q.enqueue(keys[i]);
        return q;
    }
    
    Iterable<Page<Key>> pages() {
        Queue<Page<Key>> q = new Queue<Page<Key>>();
        for (int i=0; i<M; i++)
            if (pages[i] != null)
                q.enqueue(pages[i]);
        return q;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean more(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) > 0;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append(":");
        for (Key k: keys)
            sb.append(k).append(",");
        return sb.substring(0, sb.length()-1);
    }
    
}
