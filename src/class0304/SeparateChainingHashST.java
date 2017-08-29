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
        N -= boot[hash(key)].size();
        boot[hash(key)].put(key, val);
        N += boot[hash(key)].size();
    }
    
    void delete(Key key) {
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
        // TODO resize boot , rehash key-value pair
    }
    
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    
    public static void main(String[] args) {

    }

}
