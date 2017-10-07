package class0304;

/**
 * @Description 3.4.31
 *      布谷鸟散列-_-!!!
 * @author Leon
 * @date 2017-10-06 15:26:13
 */
public class CuckooHashing<Key extends Comparable<Key>, Value> {

    SeparateHashSTA<Key, Value> sta = new SeparateHashSTA<Key, Value>();
    SeparateHashSTB<Key, Value> stb = new SeparateHashSTB<Key, Value>();
    
    public int size() {
        return sta.N + stb.N;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public Value get(Key key) {
        return sta.get(key) == null? stb.get(key): sta.get(key);
    }
    
    public void put(Key key, Value val) {
        // what's the 'If this process cycles, restart' mean ???
    }
    
    public void delete(Key key) {
        if(!sta.delete(key))
            stb.delete(key);
    }
    
    public static void main(String[] args) {
        
    }

}
