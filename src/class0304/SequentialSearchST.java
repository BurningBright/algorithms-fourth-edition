package class0304;

import rlgs4.Queue;

/**
 * @Description 3.4.2
 *      顺序搜索符号表
 *      
 * @author Robert Sedgewick
 * @author Kevin Wayne
 * @date 2017-08-29 11:01:37
 */
public class SequentialSearchST <Key, Value> {
    private int n;
    private Node first;
    
    private class Node {
        private Key key;
        private Value val;
        private Node next;

        public Node(Key key, Value val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }
    
    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public boolean contains(Key key) {
        return get(key) != null;
    }

    public Value get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return x.val;
        }
        return null;
    }

    private int CMP;
    public int put(Key key, Value val) {
        CMP = 0;
        if (val == null) {
            delete(key);
            return CMP;
        }

        for (Node x = first; x != null; x = x.next) {
            CMP++;
            if (key.equals(x.key)) {
                x.val = val;
                return CMP;
            }
        }
        first = new Node(key, val, first);
        n++;
        return CMP;
    }

    public void delete(Key key) {
        first = delete(first, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        if (key.equals(x.key)) {
            n--;
            return x.next;
        }
        x.next = delete(x.next, key);
        return x;
    }

    public Iterable<Key> keys()  {
        Queue<Key> queue = new Queue<Key>();
        for (Node x = first; x != null; x = x.next)
            queue.enqueue(x.key);
        return queue;
    }
    
    public Object[][] entrys() {
        Object[][] tmp = new Object[2][size()];
        int i = 0;
        for (Node x = first; x != null; x = x.next) {
            tmp[0][i] = x.key;
            tmp[1][i] = x.val;
        }
        return tmp;
    }
    
}
