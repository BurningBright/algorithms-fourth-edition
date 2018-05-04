package context02;

import rlgs4.Queue;

/**
 * @Description context02.16 
 *              B-tree 符号表节点
 *              部分方法不够优雅
 * @author Leon
 * @date 2018-05-04 15:00:00
 */
public class PageST <Key extends Comparable<Key>, Value> {
    
    private boolean bottom;
    private int     M;
    private int     n;
    private Node[]  nodes;
    
    class Node implements Comparable<Node>{

        Key                 key;
        Value               val;
        PageST<Key, Value>  probe;
        
        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

        public String toString() {
            return key + ":" + val;
        }

        @Override
        public int compareTo(PageST<Key, Value>.Node o) {
            return key.compareTo(o.key);
        }

    }
    
    @SuppressWarnings("unchecked")
    PageST(boolean bottom, int M) {
        this.M = M;
        this.bottom = bottom;
        nodes = new PageST.Node[M];
    }
    
    public void close() {
        
    }
    
    public void put(Key key, Value val) {
        if (key == null) return;
        int flag = 0;
        for (int i=0; i<M; i++) {
            if (nodes[i]!= null && eq(key, nodes[i].key))
                return;
            if (nodes[i]!= null && more(key, nodes[i].key))
                flag++;
        }
        for (int i=M-1; i>flag; i--)
            nodes[i] = nodes[i-1];
        nodes[flag] = new Node(key, val);
        n++;
    }
    
    public void put(PageST<Key, Value> page) {
        if (page == null) return;
        
        Node first = page.firstNode();
        boolean isAdd = false;
        int flag = 0;
        
        if (n == 0) {
            isAdd = true;
        } else {
            for (int i=0; i<M; i++) {
                if (nodes[i] != null && eq(first.key, nodes[i].key)) {
                    isAdd = false;
                    flag = i;
                    break;
                }
                if (nodes[i] != null && more(first.key, nodes[i].key)) {
                    flag ++;
                    isAdd = true;
                }
            }
            
            for (int i=M-1; i>flag && isAdd; i--) 
                nodes[i] = nodes[i-1];
            
        }
        if (isAdd) n++;
        
        nodes[flag] = new Node(first.key, first.val);
        nodes[flag].probe = page;
    }
    
    public Value get(Key key) {
        for (int i=0; i<n; i++) 
            if (eq(key, nodes[i].key))
                return nodes[i].val;
        return null;
    }
    
    public void delete(Key key) {
        if (key == null) return;
        
        for (int i=0; i<n; i++) 
            if (eq(key, nodes[i].key)) {
                nodes[i].probe = null;
                if (!eq(key, "*"))
                    nodes[i] = null;
                n--;
                for (int j=i; j<M-1; j++)
                    nodes[j] = nodes[j+1];
                break;
            }
        if (nodes[0] == null || nodes[0].probe == null)
            bottom = true;
    }
    
    public void delete(Key deleted, Key key) {
        for (int i=0; i<n; i++) 
            if (eq(deleted, nodes[i].key)) {
                nodes[i].key = key;
                break;
            }
    }
    
    public PageST<Key, Value> split() {
        PageST<Key, Value> another = new PageST<Key, Value>(bottom, M);
        for (int i=M/2; i < M && nodes[i] != null; i++) {
            another.put(nodes[i].key, nodes[i].val);
            another.put(nodes[i].probe);
            nodes[i] = null;
            n--;
        }
        return another;
    }
    
    public PageST<Key, Value> next(Key key) {
        for (int i=0; i<M; i++) {
            if (nodes[i] == null || less(key, nodes[i].key))
                return nodes[i-1].probe;
            if (i == M-1)
                return nodes[i].probe;
        }
        return null;
    }
    
    public boolean contains(Key key) {
        for (int i=0; i<M; i++)
            if (nodes[i] != null && eq(key, nodes[i].key))
                return true;
        return false;
    }
    
    public Key min() {
        if (nodes[0] == null) return null;
        return nodes[0].key;
    }
    
    public Key max() {
        return nodes[n-1].key;
    }
    
    public boolean isExternal() {
        return bottom;
    }
    
    public boolean isFull() {
        return n == M;
    }
    
    @SuppressWarnings("unchecked")
    public boolean isEmpty() {
        return n == 0 || (n == 1 && eq((Key)"*", nodes[0].key)) ;
    }
    
    public int size() {
        return n;
    }
    
    public Key floor(Key key) {
        int flag = 0;
        for (int i=0; i<n; i++)
            if (eq(key, nodes[i].key))
                return key;
            else if (less(nodes[i].key, key))
                flag = i;
        return nodes[flag].key;
    }
    
    public Key ceiling(Key key) {
        int flag = 0;
        for (int i=n-1; i>=0; i--)
            if (eq(key, nodes[i].key))
                return key;
            else if (more(nodes[i].key, key))
                flag = i;
        return nodes[flag].key;
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
    
    Node firstNode() {
        return nodes[0];
    }
    
    Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (int i=0; i<M; i++)
            if (nodes[i] != null)
                q.enqueue(nodes[i].key);
        return q;
    }
    
    Iterable<PageST<Key, Value>> pages() {
        Queue<PageST<Key, Value>> q = new Queue<PageST<Key, Value>>();
        for (int i=0; i<M; i++)
            if (nodes[i] != null && nodes[i].probe != null)
                q.enqueue(nodes[i].probe);
        return q;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n).append(bottom).append(":");
        for (Node k: nodes)
            sb.append(k).append(",");
        return sb.substring(0, sb.length()-1);
    }
    
}
