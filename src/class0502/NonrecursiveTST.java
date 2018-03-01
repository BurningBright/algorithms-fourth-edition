package class0502;

import stdlib.StdOut;

/**
 * @Description 5.2.5
 *          非递归型TST
 *          节点是mid"子树字符串"的"起始字符"
 *          amazing
 * @author Leon
 * @date 2018-03-01 15:00:00
 */
public class NonrecursiveTST<Value> {
    
    private Node root;
    
    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }
    
    public Value get(String key) {
        Node n = get(root, key, 0);
        return n == null? null: (Value)n.val;
    }
    
    /**
     * 一开始并没有意识到错误所在
     * TST的mid引用节点是代表者字符串的开头
     * 所以在get / put 索引目标的过程中 步进d 不应步进
     */
    private Node get(Node x, String key, int d) {
        Node next = x;
        while (d < key.length()) {
            char c = key.charAt(d);
            if (c < next.c)
                next = next.left;
            else if (c > next.c)
                next = next.right;
            else if (d == key.length()-1)
                return next;
            else {
                next = next.mid;
                d++;
            }
            
            // 判断节点
            if (next == null) return null;
        }
        
        /*
        for (; d<key.length(); d++) {
            // 引导目标节点
            char c = key.charAt(d);
            if (c < next.c)
                next = next.left;
            else if (c > next.c)
                next = next.right;
            else if (d == key.length()-1)
                return next;
            else
                next = next.mid;
            
            // 判断节点
            if (next == null) return null;
            
        }
        */
        return null;
    }
    
    /**
     * 一开始想到了continue，但没有贯彻到底
     * 还是用while更容易理解
     */
    public void put(String key, Value val) {
        
        if (root == null) {
            root = new Node();
            root.c = key.charAt(0);
        }
        int d = 0;
        Node next = root;
        while (d < key.length()) {
            // 引导目标节点
            char c = key.charAt(d);
            if (c < next.c) {
                if (next.left == null) {
                    next.left = new Node();
                    next.left.c = c;
                }
                next = next.left;
            } else if (c > next.c) {
                if (next.right == null) {
                    next.right = new Node();
                    next.right.c = c;
                }
                next = next.right;
            } else if (d == key.length()-1) {
                next.val = val;
                return;
            } else {
                if (next.mid == null) {
                    next.mid = new Node();
                    next.mid.c = c;
                }
                next = next.mid;
                d++;
            }
            
        }
        
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sells sea shells by the sea shore".split(" ");
        NonrecursiveTST<String> trie = new NonrecursiveTST<String>();
        
        for (int i=0; i<src.length; i++)
            trie.put(src[i], src[i]);
        
        for (int i=0; i<src.length; i++)
            StdOut.println(trie.get(src[i]));
    }

}
