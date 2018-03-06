package class0502;

import java.util.Iterator;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description 5.2.6
 *          trie型字符串集合
 * @author Leon
 * @date 2018-02-22 10:57:00
 */
public class StringSET implements Iterable<String> {
    
    private static int R = 256;
    private Node root;

    private static class Node {
        private int N;                          // 其下键值个数
        private String val;                     // 值
        private Node[] next = new Node[R];      // 某种意义上的键
    }

    public StringSET() {
        // create a string set
    }
    
    public void add(String key) {
        root = add(root, key, 0);
        // update character path node N 新增键值，路径节点键值+1
        root.N = recount(root, key, 0);
    }
    
    private Node add(Node x, String key, int d) {
        // Change value associated with key if in subtrie rooted at x.
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.val = key;
            // count sub trie size 重新统计其下键值数
            x.N = size(x);
            return x;
        }
        // Use dth key char to identify subtrie. 继续探索更深的节点
        char c = key.charAt(d);
        x.next[c] = add(x.next[c], key, d+1);
        return x;
    }
    
    private int size(Node x) {
        if (x == null) return 0;
        int cnt = 0;
        // 键值标识累加
        if (x.val != null) cnt++;
        for (int i=0; i<R; i++)
            cnt += size(x.next[i]);
        return cnt;
    }
    
    private int recount(Node x, String key, int d) {
        // 自上而下的方法压栈
        if (x == null) return 0;
        if (d == key.length()) return x.N;
        // 更新新增、删除路径上的Key的子节点大小
        char c = key.charAt(d);
        recount(x.next[c], key, d+1);
        // 累加其下所挂节点大小
        int cnt = 0;
        if (x.val != null) cnt++;
        for (int i=0; i<R; i++)
            if (x.next[i] != null)
                cnt += x.next[i].N;
        // 更新节点
        x.N = cnt;
        return cnt;
    }
    
    public void delete(String key) {
        // remove key from the set
        root = delete(root, key, 0);
        // update character path node N 删除键值，路径节点键值-1
        root.N = recount(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null)
            return null;
        // 路径大小缩小
        x.N--;
        if (d == key.length())
            // 删除目标键值
            x.val = null;
        else {
            // 移除其下无效引导节点
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d + 1);
        }
        
        // 判断节点下是否还挂有别的节点
        if (x.val != null)
            return x;
        for (char c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        // 没有就删
        return null;
    }
    
    public boolean contains(String key) {
        Node x = get(root, key, 0);
        return (x != null && x.val !=null)? true: false;
    }
    
    private Node get(Node x, String key, int d) {
        // Return value associated with key in the subtrie rooted at x.
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        // Use dth key char to identify subtrie.
        char c = key.charAt(d);
        // 自上而下的方法压栈
        return get(x.next[c], key, d + 1);
    }
    
    public boolean isEmpty() {
        // is the set empty?
        return size() == 0;
    }
    
    public int size() {
        // number of keys in the set
        return root == null? 0: root.N;
    }
    
    public String toString() {
        Queue<Node> q = new Queue<Node>();
        collectNode(root, "", q);
        StringBuilder sb = new StringBuilder();
        for (Node x: q)
            sb.append(x.val).append("\t").append(x.N).append(" | ");
        return sb.toString();
    }
    
    @Override
    public Iterator<String> iterator() {
        Queue<String> q = new Queue<String>();
        collect(root, "", q);
        return q.iterator();
    }
    
    public void collect(Node x, String pre, Queue<String> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(pre);
        for (char c = 0; c < R; c++)
            collect(x.next[c], pre + c, q);
    }
    
    public void collectNode(Node x, String pre, Queue<Node> q) {
        if (x == null) return;
        if (x.val != null) q.enqueue(x);
        // 起始节点下的所有键值节点入队列
        for (char c = 0; c < R; c++)
            collectNode(x.next[c], pre + c, q);
    }
    
    public boolean containsPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        return x != null? true: false;
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        StringSET set = new StringSET();
        for(String s: src)
            set.add(s);
        StdOut.println(set.toString());
        StdOut.println(set.size());
        
        set.add("s");
        StdOut.println(set.toString());
        StdOut.println(set.size());
        
        set.add("sh");
        StdOut.println(set.toString());
        StdOut.println(set.size());
        
        set.delete("s");
        StdOut.println(set.toString());
        StdOut.println(set.size());

        set.delete("sh");
        StdOut.println(set.toString());
        StdOut.println(set.size());
    }
}
