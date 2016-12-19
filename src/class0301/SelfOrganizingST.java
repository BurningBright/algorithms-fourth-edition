package class0301;

import java.util.Arrays;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 3.1.22<-3.1.2
 *      符号插入向前的启发式自组织符号表
 * @author Leon
 * @date 2016-11-29 14:21:09
 */
public class SelfOrganizingST<Key extends Comparable<Key>, Value> {
    
    private int N;
    private Key[] keys;
    private Value[] values;
    
    @SuppressWarnings("unchecked")
    public SelfOrganizingST() {
        N = 0;
        keys = (Key[]) new Comparable[1];
        values = (Value[]) new Comparable[1];
    }
    
    public void put(Key key, Value val) {
        if (N == keys.length) resize(2*keys.length);
        for (int i=0; i<N; i++) {
            if (key.compareTo(keys[i]) == 0) {
                values[i] = val;
                if (i != 0)
                    moveFront(i);
                return;
            }
        }
        keys[N] = key;
        values[N] = val;
        moveFront(N++);
    }
    
    private void moveFront(int index) {
        Key kTmp = keys[index];
        Value vTmp = values[index];
        for (int i=index; i>0; i--) 
            keys[i] = keys[i - 1];
        for (int i=index; i>0; i--) 
            values[i] = values[i - 1];
        
        keys[0] = kTmp;
        values[0] = vTmp;
    }
    
    public Value get(Key key) {
        for (int i=0; i<N; i++) {
            if (key.compareTo(keys[i]) == 0)
                return values[i];
        }
        return null;
    }
    
    public void delete(Key key) {
        int t = -1;
        for (int i=0; i<N; i++) 
            if (key.compareTo(keys[i]) == 0)
                t = i;
        
//        if (t == -1)
//            throw new NoSuchElementException();
        
        if (t != -1) {
            for(int i=t; i<N-1; i++) {
                keys[i] = keys[i+1];
                values[i] = values[i+1];
            }
            keys[N-1] = null;
            values[N-1] = null;
            N--;
        }
    }
    
    public boolean contains(Key key) {
        for (int i=0; i<N; i++) {
            if (key.compareTo(keys[i]) == 0)
                return true;
        }
        return false;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public int size() {
        return N;
    }
    
    Iterable<Key> keys() {
        Queue<Key> q = new Queue<Key>();
        for (int i=0; i<N; i++)
            if (keys[i] != null)
                q.enqueue(keys[i]);
        return q;
    }
    
    @SuppressWarnings("unchecked")
    private void resize(int max) {
        Key[] tmpK = (Key[]) new Comparable[max];
        Value[] tmpV = (Value[]) new Comparable[max];
        System.arraycopy(keys, 0, tmpK, 0, N);
        System.arraycopy(values, 0, tmpV, 0, N);
        keys = tmpK;
        values = tmpV;
    }
    
    public String toString() {
        return Arrays.toString(keys) + "\n" + Arrays.toString(values);
    }
    
    public static void main(String[] args) {
        SelfOrganizingST<String, Integer> a = new SelfOrganizingST<String, Integer>();
        a.put("a", 1);
        a.put("b", 2);
        a.put("c", 3);
        a.delete("b");
        a.put("c", 2);
        StdOut.println(a.toString());
        StdOut.println(a.size());
        StdOut.println(a.isEmpty());
        StdOut.println(a.contains("a"));
        StdOut.println(a.contains("b"));
    }

}
