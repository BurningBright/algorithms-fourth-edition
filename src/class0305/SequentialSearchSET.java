package class0305;

import rlgs4.Queue;

/**
 * @Description 3.5.2
 *      顺序搜索集合
 *      
 * @author Leon
 * @date 2017-10-14 21:12:37
 */
public class SequentialSearchSET <Key> {
    private int n;
    private Node first;
    
    private class Node {
        private Key key;
        private Node next;

        public Node(Key key, Node next)  {
            this.key  = key;
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

    public Key get(Key key) {
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key))
                return key;
        }
        return null;
    }

    public void put(Key key) {
        if (key == null) {
            return;
        }

        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                return;
            }
        }
        first = new Node(key, first);
        n++;
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
    
}
