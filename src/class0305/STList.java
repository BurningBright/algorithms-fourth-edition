package class0305;

import java.util.Iterator;
import java.util.NoSuchElementException;

import rlgs4.ST;

/**
 * @Description 3.5.27 符号链表[诡异]
 * @author Leon
 * @date 2017-10-25 09:24:00
 */
public class STList <T extends Comparable<T>> implements Iterable<T> {
    
    private int n;
    private int head;
    private int tail;
    
    // find index
    private ST<T, Integer> sta = new ST<T, Integer>();
    // find T
    private ST<Integer, T> stb = new ST<Integer, T>();
    
    public void addFront(T item) {
        if(item == null)  throw new IllegalArgumentException("error item");
        if(n > 0)
            head--;
        sta.put(item, head);
        stb.put(head, item);
        n++;
    }
    public void addBack(T item) {
        if(item == null)  throw new IllegalArgumentException("error item");
        if(n > 0)
            tail++;
        sta.put(item, tail);
        stb.put(tail, item);
        n++;
    }
    public T deleteFront() {
        if(!stb.contains(head)) return null;
        T tmp = stb.get(head);
        sta.delete(tmp);
        stb.delete(head);
        if(n > 1)
            head++;
        n--;
        return tmp;
    }
    public T deleteBack() {
        if(!stb.contains(tail)) return null;
        T tmp = stb.get(tail);
        sta.delete(tmp);
        stb.delete(tail);
        if(n > 1)
            tail--;
        n--;
        return tmp;
    }
    public void delete(T item) {
        if(item == null)  throw new IllegalArgumentException("error item");
        if(!sta.contains(item)) throw new RuntimeException("index not exists");
        delete(sta.get(item));
    }
    public void add(int i, T item) {
        if(item == null)  throw new IllegalArgumentException("error item");
        if(i < head) {
            addFront(item);
            return;
        }
        if(i > tail) {
            addBack(item);
            return;
        }
        if(!stb.contains(i))
            n++;
        sta.put(item, i);
        stb.put(i, item);
        
        /*
        ST<T, Integer> a = new ST<T, Integer>();
        ST<Integer, T> b = new ST<Integer, T>();
        for(int j=0; j<i; j++) {
            T t = stb.get(j);
            a.put(t, j);
            b.put(j, t);
        }
        a.put(item, i);
        b.put(i, item);
        n++;
        for(int j=i+1; j<n; j++) {
            T t = stb.get(j);
            a.put(t, j+1);
            b.put(j+1, t);
        }
        sta = a;
        stb = b;
        */
    }
    public T delete(int i) {
        if(!stb.contains(i)) return null;
        if(i == head) 
            return deleteFront();
        if(i == tail) 
            return deleteBack();
        
        T tmp = stb.get(i);
        sta.delete(tmp);
        stb.delete(i);
        n--;
        return tmp;
    }
    public boolean contains(T item) {
        return sta.contains(item);
    }
    public boolean isEmpty() {
        return size() <= 0;
    }
    public int size() {
        return n;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new STIterator();
    }
    
    private class STIterator implements Iterator<T> {
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            T t = stb.get(i);
            i++;
            return t;
        }
    }
    
    public static void main(String[] args) {
        
    }

}
