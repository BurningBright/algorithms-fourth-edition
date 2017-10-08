package class0304;

import class0304.SequentialST.Node;

/**
 * @Description 3.4.27
 *      Double hash function SeparateChainingHashST
 * @author Leon
 * @date 2017-10-08 13:36:13
 */
public class DoubleProbing <Key extends Comparable<Key>, Value> {

    private int M;
    private int N;
    
    private double upperLimit = 1.5;
    private double lowerLimit = 0.33;
    
    SequentialST<Key, Value> boot[];
    
    DoubleProbing() {
        this(3);
    }
    
    @SuppressWarnings("unchecked")
    DoubleProbing(int M) {
        this.M = M;
        boot = (SequentialST<Key, Value>[])new SequentialST[M];
        for (int i=0; i<M; i++)
            boot[i] = new SequentialST<Key, Value>();
    }
    
    public int size() {
        return N;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public Value get(Key key) {
        Value val = boot[hashA(key)].get(key);
        return val == null ? boot[hashB(key)].get(key): val;
    }
    
    // put entry to the shorter slot
    public void put(Key key, Value val) {
        checkCapacity();
        
        int lenA = boot[hashA(key)].size();
        int lenB = boot[hashB(key)].size();
        int slot = lenA < lenB ? hashA(key): hashB(key);
        
        boot[slot].put(key, val);
    }
    
    @SuppressWarnings("rawtypes")
    boolean delete(Key key) {
        checkCapacity();
        Node a = boot[hashA(key)].delete(key);
        if(a != null) {
            N--;
            return true;
        }
        Node b = boot[hashB(key)].delete(key);
        if(b != null)
            N--;
        return b != null;
    }
    
    private int hashA(Key key) {
        return 11 * (key.hashCode() & 0x7fffffff) % M;
    }
    
    private int hashB(Key key) {
        return 17 * (key.hashCode() & 0x7fffffff) % M;
    }
    
    private void checkCapacity() {
        // resize boot , rehash key-value pair
        if (M > 10 && Math.sqrt(size()) >= upperLimit * M) {
            boot = resize(2 * M, M);
        } else if (M > 10 && Math.sqrt(size()) <= lowerLimit * M) {
            boot = resize(M / 2, M);
        }
    }
    
    @SuppressWarnings("unchecked")
    private SequentialST<Key, Value>[] resize(int cap, int ori) {
        
        SequentialST<Key, Value>[] tmp = (SequentialST<Key, Value>[])new SequentialST[cap];
        
        for(int i=0; i<cap; i++)
            tmp[i] = new SequentialST<Key, Value>();
        
        // new capacity
        M = cap;
        N = 0;
        for(int i=0; i<ori; i++) {
            @SuppressWarnings("rawtypes")
            SequentialST.Node node = boot[i].getFirst();
            while(node != null) {
                int lenA = boot[hashA((Key)node.key)].size();
                int lenB = boot[hashB((Key)node.key)].size();
                int slot = lenA < lenB ? hashA((Key)node.key): hashB((Key)node.key);
                
                boot[slot].put((Key)node.key, (Value)node.val);
                node = node.next;
                N++;
            }
        }
        
        return tmp;
    }
    
    public static void main(String[] args) {

    }

}
