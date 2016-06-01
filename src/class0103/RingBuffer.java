package class0103;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * @Description 1.3.39
 *          环型缓冲区，用于异步线程数据
 *          用途上类似阻塞双缓冲队列
 * @author Leon
 * @date 2016-06-01 16:24:52
 */
public class RingBuffer<T> {
    private int capacity;
    private int N;
    private Node last;
    
    private class Node{
        private T item;
        private Node next;
    }
    
    /**
     * Initializes a circular queue
     */
    public RingBuffer(int cap) {
        this.last = null;
        this.N = 0;
        this.capacity = cap;
    }

    /**
     * Returns the number of items in this queue.
     * @return number of items
     */
    public int size() {
        return N;
    }
    
    /**
     * Returns the least recently added in this queue
     * @return the item least recently added to this queue
     */
    public T peek() {
        return last.next.item;
    }
    
    /**
     * Returns the recently added in this queue
     * @return the item recently added to this queue
     */
    public T tail() {
        return last.item;
    }
    
    /**
     * Adds the item to this queue.
     * @param item self defined class generic type
     */
    public void enqueue(T item) {
        if(N >= capacity) {
            //TODO 当前容器已满，挂起生产者线程
            
        }
        if(last != null) {
            Node enter = new Node();
            enter.item = item;
            enter.next = last.next;
            last.next = enter;
            last = enter;
        }else{
            last = new Node();
            last.item = item;
            last.next = last;
        }
        N++;
    }
    
    /**
     * Removes and returns the item on this queue that was least recently added.
     * @return the item on this queue that was least recently added
     */
    public T dequeue() {
        if(last != null) {
            Node leave = last.next;
            last.next = leave.next;
            leave.next = null;
            N--;
            return leave.item;
        }else{
            // TODO 当前容器为空，挂起消费者线程
            return null;
        }
    }
    
    /**
     * Returns a string representation of this queue.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        Node current = last.next;
        if(current != null) {
            for (int i=0; i<N; i++) {
                s.append(current.item +" ");
                current = current.next;
            }
        }
        return s.toString();
    }
    public Iterator<T> iterator() {
        return new CircularIterator();
    }
    
    private class CircularIterator implements Iterator<T> {
        
        private Node current = last.next;
        
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }
}
