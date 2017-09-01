package class0304;

import rlgs4.Queue;

/**
 * @Description 3.4.2
 *      顺序搜索符号表
 *      
 * @author Leon
 * @date 2017-08-29 11:01:37
 */
public class SeparateChainingHashST<Key extends Comparable<Key>, Value> {
    
    private int M;
    private int N;
    
    private double upperLimit = 1.5;
    private double lowerLimit = 0.33;
    
    SequentialSearchST<Key, Value> boot[];
    
    SeparateChainingHashST() {
        this(3);
    }
    
    @SuppressWarnings("unchecked")
    SeparateChainingHashST(int M) {
        this.M = M;
        boot = (SequentialSearchST<Key, Value>[])new SequentialSearchST[M];
        for (int i=0; i<M; i++)
            boot[i] = new SequentialSearchST<Key, Value>();
    }
    
    public void put(Key key, Value val) {
        checkCapacity();
        N -= boot[hash(key)].size();
        boot[hash(key)].put(key, val);
        N += boot[hash(key)].size();
    }
    
    void delete(Key key) {
        checkCapacity();
        N -= boot[hash(key)].size();
        boot[hash(key)].delete(key);
        N += boot[hash(key)].size();
    }
    
    public Value get(Key key) {
        return (Value) boot[hash(key)].get(key);
    }
    
    public boolean contains(Key key) {
        return get(key) != null;
    }
    
    public int size() {
        return N;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<Key>();
        for (int i=0; i<M; i++) {
            for (Key k: boot[i].keys()) {
                queue.enqueue(k);
            }
        }
        return queue;
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
    private SequentialSearchST<Key, Value>[] resize(int cap, int ori) {
        
        SequentialSearchST<Key, Value>[] tmp = 
                (SequentialSearchST<Key, Value>[])new SequentialSearchST[cap];
        
        for(int i=0; i<cap; i++)
            tmp[i] = new SequentialSearchST<Key, Value>();
        
        // new capacity
        M = cap;
        N = 0;
        for(int i=0; i<ori; i++) {
            Object[][] entrys = boot[i].entrys();
            for(int j=0; j<entrys[0].length; j++) {
                boot[hash((Key)entrys[0][j])].put((Key)entrys[0][j], (Value)entrys[1][j]);
            }
            N += entrys[0].length;
        }
        
        return tmp;
    }
    
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<M; i++) {
            Object[][] entrys = boot[i].entrys();
            for(int j=0; j<entrys[0].length; j++) 
                sb.append(entrys[0][j].toString()).append(":").
                append(entrys[1][j].toString()).append(" <- ");
            
            sb.append("\r\n");
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {

    }

}
