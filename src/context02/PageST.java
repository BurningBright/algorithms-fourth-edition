package context02;

import rlgs4.Queue;

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
    
    @SuppressWarnings("unchecked")
    public Key min() {
        if (eq((Key)"*", nodes[0].key))
            return nodes[1].key;
        return nodes[0].key;
    }
    
    public Key max() {
        return nodes[n].key;
    }
    
    public boolean isExternal() {
        return bottom;
    }
    
    public boolean isFull() {
        return n == M;
    }
    
    public int size() {
        return n;
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
