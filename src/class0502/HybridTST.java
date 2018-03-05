package class0502;

import stdlib.StdOut;

/**
 * @Description 5.2.13
 *          混合字符串 符号表
 * @author Leon
 * @date 2018-03-05 14:04:00
 */
public class HybridTST<Value> {
    
    private static int R = 256; // radix
    private Node root;          // root of hybrid
    private static int M = 2;
    
    /**
     * 无法创建泛型数组
     * 只能脱开外部类实例，做一个静态类
     */
    private static class Node{
        private Node[] next = new Node[R];
        private Node left, mid, right;
        private char c;
        private Object val;
    }
    
    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }
    
    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        // 前两层的深度会多1
        if (d == key.length() && d < M) return x;
        // 两层后的深度保持正确
        if (d == key.length() + 1) return x;
        
        if (d < M) {
            char c = key.charAt(d);
            return get(x.next[c], key, d + 1);
        }
        
        char c = key.charAt(d-1);
        
        if (c < x.c)
            return get(x.left, key, d);
        else if (c > x.c)
            return get(x.right, key, d);
        else if (d < key.length())
            return get(x.mid, key, d + 1);
        else
            return x;
    }
    
    public void put(String key, Value val) {
        root = put(root, key, val, 0);
    }
    
    private Node put(Node x, String key, Value val, int d) {
        
        if (d < M) {
            if (x == null) 
                x = new Node();
            if (d == key.length()) {
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            x.next[c] = put(x.next[c], key, val, d + 1);
            return x;
        }
        
        // tries树的探索深度比实际深度多了1
        char c = key.charAt(d-1);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        
        if (c < x.c)
            x.left = put(x.left, key, val, d);
        else if (c > x.c)
            x.right = put(x.right, key, val, d);
        else if (d < key.length())
            x.mid = put(x.mid, key, val, d + 1);    // 在下一帧中 x.val = val
        else
            x.val = val;
        return x;
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        // she sells sea shells by the sea shore
        HybridTST<String> st = new HybridTST<String>();
        
        for (int i=0; i<src.length; i++)
            st.put(src[i], src[i]);
        
        for (int i=0; i<src.length; i++)
            StdOut.println(st.get(src[i]));
        
    }

}
