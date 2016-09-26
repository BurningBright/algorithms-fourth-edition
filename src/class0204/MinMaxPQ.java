package class0204;

import java.util.Comparator;
import java.util.NoSuchElementException;

import rlgs4.MaxPQ;
import rlgs4.MinPQ;

/**
 * @Description 2.4.29
 *      通过结合两堆，对最大、最小
 *      插入、删除操作时间都是对数级
 * @author Leon
 * @date 2016-09-26 09:47:22
 */
public class MinMaxPQ<Key> {
    
    MaxPQ<Key> maxPQ;
    MinPQ<Key> minPQ;
    
    public MinMaxPQ() {
        this(1);
    }
    
    public MinMaxPQ(int initCapacity) {
        maxPQ = new MaxPQ<Key>(initCapacity);
        minPQ = new MinPQ<Key>(initCapacity);
    }
    
    public MinMaxPQ(int initCapacity, Comparator<Key> comparator) {
        maxPQ = new MaxPQ<Key>(initCapacity, comparator);
        minPQ = new MinPQ<Key>(initCapacity, comparator);
    }
    
    public MinMaxPQ(Key[] keys) {
        maxPQ = new MaxPQ<Key>(keys);
        minPQ = new MinPQ<Key>(keys);
    }
    
    public boolean isEmpty() {
        return maxPQ.isEmpty() || minPQ.isEmpty();
    }
    
    public int size() {
        return Math.min(maxPQ.size(), minPQ.size());
    }
    
    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return maxPQ.max();
    }
    
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return minPQ.min();
    }
    
    public Key delMax() {
        return maxPQ.delMax();
    }
    
    public Key delMin() {
        return minPQ.delMin();
    }
    
    public static void main(String[] args) {
        
    }

}
