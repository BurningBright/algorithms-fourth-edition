package class0502;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 5.2.9
 *          size()
 *          keysWithPrefix()
 *          keysThatMatch()
 *          longestPrefixOf()
 * @author Leon
 * @date 2018-03-02 14:00:00
 */
public class TST<Value> {

    private Node root;              // root of trie

    private class Node {
        char c;                     // character
        Node left, mid, right;      // left, middle, and right subtries
        Value val;                  // value associated with string
    }

    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        
        char c = key.charAt(d);
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length() - 1)
            return get(x.mid, key, d + 1);
        else
            return x;
    }

    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c)
            x.left = put(x.left, key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length() - 1)
            x.mid = put(x.mid, key, val, d + 1);
        else
            x.val = val;
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
        cnt += size(x.left);
        cnt += size(x.mid);
        cnt += size(x.right);
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
        //合上最后一个字符
        if (x.val != null)
            q.enqueue(pre + x.c);
        
        // 左右引用不记入Key
        collect(x.left, pre, q);
        collect(x.mid, pre + x.c, q);
        collect(x.right, pre, q);
    }
    
    
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }
    
    public void collect(Node x, String pre, String pat, Queue<String> q) {
        int d = pre.length();
        if (x == null) return;
        /*
         *  由于根节点有字符，
         *  所以在递归的一开始 pre 的前缀长度就应该是1
         *  后序的递归深度中 前缀都少1
         *  在入队列中时，应将当前节点字符补上
         */
        if ((d+1) == pat.length() && x.val != null) q.enqueue(pre + x.c);
        if (d == pat.length()) return;
        
        // pattern 通配符递归3个方向
        char next = pat.charAt(d);
        if (next == '.') {
            collect(x.left, pre, pat, q);
            collect(x.mid, pre + x.c, pat, q);
            collect(x.right, pre, pat, q);
            return;
        }
        
        if (next < x.c)
            collect(x.left, pre, pat, q);
        else if (next == x.c)
            collect(x.mid, pre + x.c, pat, q);
        else if(next > x.c)
            collect(x.right, pre, pat, q);
        
    }
    
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }
    
    private int search(Node x, String s, int d, int length) {
        if (x == null)
            return length;
        /*
         *  保存上一帧的 长度变量
         *  在这里由于首节点有字符，所以这里的Length少了x的字符
         */
        if (x.val != null)
            length = d+1;
        if (d == s.length())
            return length;
        char c = s.charAt(d);
        if (c < x.c)
            return search(x.left, s, d, length);
        else if (c > x.c)
            return search(x.right, s, d, length);
        else
            return search(x.mid, s, d+1, length);
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        TST<String> st = new TST<String>();
        for (int i=0; i<src.length; i++)
            st.put(src[i], src[i]);
        
        StdOut.println(st.get("she"));
        StdOut.println(st.size());
        
        StdOut.println("----------------");
        
        for (String s: st.keys())
            StdOut.print(s + " ");
        StdOut.println();
        
        StdOut.println("----------------");
        
        for(String s: st.keysThatMatch("s.."))
            StdOut.println(s);
        
        StdOut.println("----------------");
        
        StdOut.println(st.longestPrefixOf("sheet"));
        
    }

}
