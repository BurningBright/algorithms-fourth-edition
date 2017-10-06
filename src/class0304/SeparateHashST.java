package class0304;

/**
 * @Description 3.4.31
 *      基础散列表
 * @author Leon
 * @date 2017-10-06 16:53:13
 */
public abstract class SeparateHashST <Key extends Comparable<Key>, Value> {

    private int M;
    private int N;
    
    private double upperLimit = 1.5;
    private double lowerLimit = 0.33;
    
    SequentialST<Key, Value> boot[];
    
    SeparateHashST() {
        this(3);
    }
    
    @SuppressWarnings("unchecked")
    SeparateHashST(int M) {
        this.M = M;
        boot = (SequentialST<Key, Value>[])new SequentialST[M];
        for (int i=0; i<M; i++)
            boot[i] = new SequentialST<Key, Value>();
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
                boot[hash((Key)node.key)].put((Key)node.key, (Value)node.val);
                node = node.next;
                N++;
            }
        }
        
        return tmp;
    }
    
    abstract int hash(Key key);
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<M; i++) {
            @SuppressWarnings("rawtypes")
            SequentialST.Node node = boot[i].getFirst();
            
            while(node != null) {
                sb.append(node.key.toString()).append(":").
                    append(node.val.toString()).append(" <- ");
                node = node.next;
            }
            sb.append("\r\n");
            
        }
        return sb.toString();
    }
    
}
