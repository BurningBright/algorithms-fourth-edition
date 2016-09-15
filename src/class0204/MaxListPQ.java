package class0204;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;

/**
 * @Description 2.4.3
 *      unordered linked list, and linked list
 * @author Leon
 * @date 2016-09-14 16:34:34
 */
public class MaxListPQ<Item extends Comparable<Item>> implements Iterable<Item>{
    
    private int N = 0;
    private Comparator<Item> comparator;
    private Node first;
    private Node last;
    
    public MaxListPQ() {
        
    }
    
    public MaxListPQ(Item[] a, boolean isOdd) {
        for(Item i: a)
            if(isOdd)
                this.oddInsert(i);
            else
                this.uddInsert(i);
    }
    
    /** 有序插、删 */
    public void oddInsert(Item v) {
        Node nv = new Node(v);
        if(first==null) {
            first = nv;
            last = nv;
            N++;
            return;
        }
        
        /*
         * 先判断自己是不是比头小XXX
         * 判断自己是不是比尾大XXX
         * 然后向后看谁比自己大
         */
        if(less(nv, first)) {
            nv.next = first;
            first.prev = nv;
            first = nv;
            N++;
            return;
        }
        
        if(!less(nv, last)) {
            last.next = nv;
            nv.prev = last;
            last = nv;
            N++;
            return;
        }
        
        Node front = first.next, back = first;
        for(int i=N; i>1 && less(front, nv); i--) {
            back = front;
            front = front.next;
        }
        
        back.next = nv;
        nv.prev = back;
        
        nv.next = front;
        front.prev = nv;
        
        N++;
        
    }
    public Item oddMax() {
        return last.item;
    }
    
    public Item oddDelMax() {
        if(N == 1){
            Item tmp = first.item;
            first = null;
            last = null;
            N--;
            return tmp;
        }
        Item tmp = last.item;
        last = last.prev;
        last.next.prev = null;
        last.next = null;
        N--;
        return tmp;
    }
    
    /** 无序插、删 */
    public void uddInsert(Item v) {
        Node nv = new Node(v);
        if(first==null) {
            first = nv;
            last = nv;
            N++;
            return;
        }
        last.next = nv;
        nv.prev = last;
        last = nv;
        N++;
    }
    
    public Item uddMax() {
        Item max = first.item;
        Node cur = first;
        
        while(cur.next!=null) {
            if(less(cur, cur.next))
                max = cur.next.item;
            cur = cur.next;
        }
        
        return max;
    }
    
    public Item uddDelMax() {
        if(N == 1){
            Item tmp = first.item;
            first = null;
            last = null;
            N--;
            return tmp;
        }
        
        // 记录删除点，判断头尾
        Node max = first, cur = first;
        int i = 0;
        
        while(cur.next!=null) {
            if(great(cur, max)) {
                max = cur;
                i++;
            }
            cur = cur.next;
        }
        if (i == 0) {
            Item tmp = first.item;
            first = first.next;
            first.prev.next = null;
            first.prev = null;
            N--;
            return tmp;
        }
        
        if (i == N - 1) {
            Item tmp = last.item;
            last = last.prev;
            last.next.prev = null;
            last.next = null;
            N--;
            return tmp;
        }
        
        max.prev.next = max.next;
        max.next.prev = max.prev;
        max.prev = null;
        max.next = null;
        
        return max.item;
        
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    private boolean less(Node i, Node j) {
        if (comparator == null) {
            return ((Comparable<Item>) i.item).compareTo(j.item) < 0;
        }
        else {
            return comparator.compare(i.item, j.item) < 0;
        }
    }
    
    private boolean great(Node i, Node j) {
        if (comparator == null) {
            return ((Comparable<Item>) i.item).compareTo(j.item) > 0;
        }
        else {
            return comparator.compare(i.item, j.item) > 0;
        }
    }
    
    class Node{
        Item item;
        Node next;
        Node prev;
        public Node(Item item) {
            this.item = item;
//            next = this;
//            prev = this;
        }
    }
    
    @Override
    public Iterator<Item> iterator() {
        return new LinkIterator();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Item i: this) {
            sb.append(i+" ");
        }
        return sb.toString();
    }
    
    private class LinkIterator implements Iterator<Item> {
        
        private Node current = first;
        @Override
        public boolean hasNext() {
            return current!=null;
        }
        
        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
    
    public static void main(String[] args) {
        /*
        MaxListPQ<Integer> oddMax = new MaxListPQ<Integer>();
        oddMax.oddInsert(3);
        oddMax.oddInsert(1);
        oddMax.oddInsert(5);
        oddMax.oddInsert(4);
        oddMax.oddInsert(2);
        oddMax.oddDelMax();
        StdOut.println(oddMax.toString());
        StdOut.println(oddMax.oddMax());
        */
        MaxListPQ<Integer> uddMax = new MaxListPQ<Integer>();
        uddMax.uddInsert(3);
        uddMax.uddInsert(1);
        uddMax.uddInsert(5);
        uddMax.uddInsert(4);
        uddMax.uddInsert(2);
        uddMax.uddDelMax();
        StdOut.println(uddMax.toString());
        StdOut.println(uddMax.uddMax());
    }

}
