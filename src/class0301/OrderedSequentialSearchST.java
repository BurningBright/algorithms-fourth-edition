package class0301;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 3.1.3
 *      node型有序符号表
 * @author Leon
 * @date 2016-11-29 16:33:37
 */
public class OrderedSequentialSearchST <Key extends Comparable<Key>, Value> {
    
    private int N;
    private Node first;
    
    public OrderedSequentialSearchST() {
        N = 0;
        first = null;
    }
    
    class Node {
        Key k;
        Value v;
        Node next;
        
        Node(Key k, Value v, Node next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }
    }
    
    public void put(Key key, Value val) {
        for (Node prv=null, cur = first; cur!=null; prv=cur, cur=cur.next) {
            if (key.compareTo(cur.k) == 0) {
                cur.v = val;
                return;
            }
            
            if (key.compareTo(cur.k) < 0) {
                if (prv == null)
                    break;
                prv.next = new Node(key, val, cur);
                N++;
                return;
            }
            
        }
        first = new Node(key, val, first);
        N++;
    }
    
    @SuppressWarnings("unchecked")
    public int put(Key key) {
        int i = 0;
        
        for (Node x = first; x != null; x = x.next) {
            i++;
            if (key.equals(x.k)) {
                return i;
            }
        }
        
        /*
        for (Node prv=null, cur = first; cur!=null; prv=cur, cur=cur.next) {
            
            int cmp = key.compareTo(cur.k);
            i++;
            if (cmp == 0) {
                return i;
            }
            
            if (cmp < 0) {
                if (prv == null)
                    break;
                prv.next = new Node(key, (Value)(new Object()), cur);
                N++;
                return i;
            }
        }
        */
        first = new Node(key, (Value)(new Object()), first);
        N++;
        return i;
    }
    
    public Value get(Key key) {
        for (Node cur = first; cur!=null; cur=cur.next) 
            if (key.compareTo(cur.k) == 0) 
                return cur.v;
        return null;
    }
    
    public void delete(Key key) {
        for (Node prv=null, cur = first; cur!=null; prv=cur, cur=cur.next) 
            if (key.compareTo(cur.k) == 0) {
                prv.next = cur.next;
                cur = null;
                N--;
                break;
            }
    }
    
    public boolean contains(Key key) {
        return get(key) != null;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (Node cur = first; cur!=null; cur=cur.next) 
                q.enqueue(cur.k);
        return q;
    }
    
    public String toString() {
        String ret = "";
        for (Node cur = first; cur!=null; cur=cur.next)
            ret +="k:" + cur.k + " v:" + cur.v + "   ";
        return ret;
    }
    
    public static void main(String[] args) {
        OrderedSequentialSearchST<String, Integer> a = new OrderedSequentialSearchST<String, Integer>();
        a.put("c", 3);
        a.put("a", 1);
        a.put("b", 2);
        a.delete("b");
        a.put("a", 2);
        StdOut.println(a.toString());
        StdOut.println(a.size());
        StdOut.println(a.isEmpty());
        StdOut.println(a.contains("a"));
        StdOut.println(a.contains("b"));
    }

}
