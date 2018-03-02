package class0502;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 5.2.8
 *          max(), min(), floor(), ceil(), rank(), select()
 * @author Leon
 * @date 2018-03-02 10:00:00
 */
public class TrieST<Value> {
    
    private static int R = 256; // radix
    private Node root;          // root of trie

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null)
            return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) { // Return value associated
                                                  // with key in the subtrie
                                                  // rooted at x.
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d);                 // Use dth key char to identify subtrie.
        return get(x.next[c], key, d + 1);
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) { // Change value
                                                             // associated with
                                                             // key if in
                                                             // subtrie rooted
                                                             // at x.
        if (x == null)
            x = new Node();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt(d);                         // Use dth key char to identify subtrie.
        x.next[c] = put(x.next[c], key, val, d + 1);
        return x;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        int cnt = 0;
        if (x.val != null)
            cnt++;
        for (char c = 0; c < R; c++)
            cnt += size(x.next[c]);
        return cnt;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue<String> q = new Queue<String>();
        collect(get(root, pre, 0), pre, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null)
            return;
        if (x.val != null)
            q.enqueue(pre);
        for (char c = 0; c < R; c++)
            collect(x.next[c], pre + c, q);
    }

    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }

    public void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null)
            return;
        if (d == pat.length() && x.val != null)
            q.enqueue(pre);
        if (d == pat.length())
            return;
        char next = pat.charAt(d);
        for (char c = 0; c < R; c++)
            if (next == '.' || next == c)
                collect(x.next[c], pre + c, pat, q);
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null)
            return length;
        if (x.val != null)
            length = d;
        if (d == s.length())
            return length;
        char c = s.charAt(d);
        return search(x.next[c], s, d + 1, length);
    }
    
    public String min() {
        return min(root, "");
    }
    
    private String min(Node x, String pre) {
        if (x == null) return null;
        if (x.val != null) return pre;
        for (char c=0; c<R; c++) {
            String ret = min(x.next[c], pre + c);
            if(ret != null) return ret;
        }
        return null;
    }
    
    public String max() {
        return max(root, "");
    }
    
    private String max(Node x, String pre) {
        if (x == null) return null;
        if (x.val != null) return pre;
        for (char c=(char) (R-1); c>=0; c--) {
            String ret = max(x.next[c], pre + c);
            if(ret != null) return ret;
        }
        return null;
    }
    
    // largest key less than or equal to key
    public String floor(String key) {
        if (key == null) return null;
        int i = rank(key);
        String tmp = select(i);
        if (key.compareTo(tmp) == 0) return key;
        if (i == 0) return null;
        else return select(i-1);
    }
    
    // smallest key greater than or equal to key
    public String ceiling(String key) {
        if (key == null) return null;
        int i = rank(key);
        String tmp = select(i);
        if (key.compareTo(tmp) == 0) return key;
        return select(i+1);
    }
    
    // number of keys less than key
    public int rank(String key) {
        return rank(root, "", key, false);
    }
    
    /**
     * @param x         起始节点
     * @param pre       前缀
     * @param pat       匹配
     * @param isCheck   是否免检其下节点
     * @return
     */
    private int rank(Node x, String pre, String pat, boolean isCheck) {
        // base case : 当节点所代表的字母大于匹配字母则回溯
        if (pre.length() >= pat.length() && !isCheck) return 0;
        // 免检时默认字母最大
        char next = isCheck? Character.MAX_VALUE: pat.charAt(pre.length());
        
        int cnt = 0;
        if (x == null) return 0;
        if (x.val != null) {cnt++; /*StdOut.println(x.val);*/}  // 目标
        for (char c=0; c<R; c++) 
            if (c < next)
                cnt += rank(x.next[c], pre + c, pat, true);     // 免检节点
            else if (c == next)
                cnt += rank(x.next[c], pre + c, pat, false);    // 待检节点
            else if (isCheck)
                cnt += rank(x.next[c], pre + c, pat, true);     // 某节点下的免检
        return cnt;
    }
    
    // key of rank k
    public String select(int k) {
        count = -1;
        return select(root, "", k);
    }
    int count = -1;
    private String select(Node x, String pre, int k) {
        if (x == null) return null;
        if (x.val != null) count++;
        if (k == count) return pre;
        for (char c = 0; c < R; c++) {
            String result = select(x.next[c], pre + c, k);
            if(result != null) return result;
        }
        return null;
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        TrieST<String> st = new TrieST<String>();
        for (int i=0; i<src.length; i++)
            st.put(src[i], src[i]);
        
        StdOut.println(st.min());
        StdOut.println(st.max());
        StdOut.println("----------------");
        
        StdOut.println(st.rank("aa"));
        StdOut.println(st.rank("seal"));
        StdOut.println(st.rank("shell"));
        
        StdOut.println("----------------");
        
        for (int i=0; i<7; i++)
            StdOut.print(st.select(i) + " ");
        StdOut.println();
        
        StdOut.println("----------------");
        
        for (int i=0; i<7; i++)
            StdOut.print(st.rank(st.select(i)) + " ");
        StdOut.println();
        
        StdOut.println("----------------");
        
        StdOut.println(st.floor("seal"));
        StdOut.println(st.ceiling("shi"));
    }
    
}
