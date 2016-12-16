package class0301;

/**
 * @Description 3.1.12
 *          二叉搜索符号表
 * @author Leon
 * @date 2016-12-15 16:28:00
 */
public class BinarySearchST<K extends Comparable<K>, V>{

    private int N = 0;
    private NameValuePair<K, V>[] nvps;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        nvps = (NameValuePair<K, V>[])new Object[capacity];
    }
    
    public int size() {
        return N;
    }
    
    public boolean isEmpty() {
        return N == 0;
    }
    
    public V get(K key) {
        if (isEmpty())
            return null;
        int i = rank(key);
        if (i < N && nvps[i].getKey().compareTo(key) == 0)
            return nvps[i].getValue();
        else
            return null;
    }

    public void put(K key, V val) { // Search for key. Update value if
        // found; grow table if new.
        int i = rank(key);
        if (i < N && nvps[i].getKey().compareTo(key) == 0) {
            nvps[i].setValue(val);
            return;
        }
        for (int j = N; j > i; j--) {
            nvps[j] = nvps[j - 1];
        }
        nvps[i] = new NameValuePair<K, V>(key, val);
    }
    
    public int rank(K key) {
        int lo = 0, hi = N - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(nvps[mid].getKey());
            if (cmp < 0)
                hi = mid - 1;
            else if (cmp > 0)
                lo = mid + 1;
            else
                return mid;
        }
        return lo;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}

class NameValuePair <Key extends Comparable<Key>, Value>{
    
    private Key key;
    private Value value;
    
    public NameValuePair(Key key, Value value) {
        this.key = key;
        this.value = value;
    }
    
    public Key getKey() {
        return key;
    }
    
    public void setKey(Key key) {
        this.key = key;
    }
    
    public Value getValue() {
        return value;
    }
    
    public void setValue(Value value) {
        this.value = value;
    }

}
