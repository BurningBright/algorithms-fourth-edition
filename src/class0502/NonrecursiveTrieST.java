package class0502;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.StdOut;

/**
 * @Description 5.2.5
 *          非递归型tries
 * @author Leon
 * @date 2018-03-01 9:00:00
 */
public class NonrecursiveTrieST<Value> {

    private static int R = 256; // radix
    private Node root;          // root of trie

    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    public NonrecursiveTrieST() {
        root = new Node();
    }

    @SuppressWarnings("unchecked")
    public Value get(String key) {
        Node n = get(root, key, 0);
        return n == null? null: (Value)n.val;
    }
    
    private Node get(Node x, String key, int d) {
        Node next = x;
        for (; d<key.length(); d++) {
            // 引导目标节点
            char c = key.charAt(d);
            next = next.next[c];
            // 判断节点
            if (next == null) return null;
            if (d == key.length()-1)
                return next;
        }
        return null;
    }
    
    public void put(String key, Value val) {
        Node next = root;
        for (int d=0; d<key.length(); d++) {
            // 引导目标节点
            char c = key.charAt(d);
            if (next.next[c] == null) 
                next.next[c] = new Node();
            next = next.next[c];
            // 节点赋值
            if (d == key.length()-1) {
                next.val = val;
                return;
            }
        }
    }
    
    public void delete(String key) {
        // 存储探索路径节点
        Stack<Node> nodes = new Stack<Node>();
        // 存储探索路径索引
        Stack<Integer> indes = new Stack<Integer>();
        Node next = root;
        for (int d=0; d<key.length(); d++) {
            // 引导目标节点
            char c = key.charAt(d);
            next = next.next[c];
            if (next == null) break;
            if (d == key.length()-1) {
                // 删值
                next.val = null;
                // 目标节点推入索引
                indes.push((int)c);
                break;
            }
            // 路径入栈
            nodes.push(next);
            indes.push((int)c);
        }
        while (!nodes.isEmpty()) {
            Node peek = nodes.pop();
            Integer index = indes.pop();
            // 首次循环的去除节点是目标值节点
            peek.next[index] = null;
            // 有值或引中断
            if (peek.val != null) return;
            for (int i=0; i<R; i++)
                if (peek.next[i] != null)
                    return;
            peek = null;
        }
    }
    
    public boolean contains(String key) {
        return get(key) != null;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        Queue<Node> q = new Queue<Node>();
        for (int i=0; i<R; i++)
            if (root.next[i] != null)
                q.enqueue(root.next[i]);
        
        // 广度遍历
        int cnt = 0;
        while(!q.isEmpty()) {
            Node n = q.dequeue();
            if (n.val != null)
                cnt++;
            for (int i=0; i<R; i++)
                if (n.next[i] != null)
                    q.enqueue(n.next[i]);
        }
        
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
    
    /**
     * 原递归中的循环 R      -> Stack<Queue<Integer>>
     * 原递归中的 char c    -> Stack<Integer>
     * 原递归中的 x.next[c] -> Stack<Node>
     * 原递归中的 pre + c   -> pre + Stack<Integer>
     */
    private void collect(Node x, String pre, Queue<String> q) {
        // 初始判断
        if(x == null) return;
        if(x.val != null) q.enqueue(pre);
        
        Stack<Node> path = new Stack<Node>();                       // 路径栈
        Stack<Integer> indes = new Stack<Integer>();                // 索引栈
        Stack<Queue<Integer>> links = new Stack<Queue<Integer>>();  // 待探索栈
        
        // 初始化首节点数据
        path.push(x);
        indes.push(-1);
        Queue<Integer> link = new Queue<Integer>();
        for (int i=0; i<R; i++)
            if (x.next[i] != null)
                link.enqueue(i);
        links.push(link);
        
        while(!path.isEmpty()) {
            // 待迭代数据为空时，弹栈
            if (links.peek().isEmpty()) {
                path.pop();
                indes.pop();
                links.pop();
                continue;
            }
            // 出"待探栈首"的队列得到新的索引，压栈
            int index = links.peek().dequeue();
            Node node = path.peek().next[index];
            path.push(node);
            indes.push(index);
            // 目标Key
            if (node.val != null) {
                String tmp = "";
                for (Integer ch: indes)
                    tmp = (char)ch.intValue() + tmp;
                q.enqueue(pre + tmp);
            }
            // 新的待探队列
            link = new Queue<Integer>();
            for (int i=0; i<R; i++)
                if (node.next[i] != null)
                    link.enqueue(i);
            links.push(link);
        }
        
    }
    
    public Iterable<String> keysThatMatch(String pat) {
        Queue<String> q = new Queue<String>();
        collect(root, "", pat, q);
        return q;
    }
    
    // 类似collect(x, x, x)
    public void collect(Node x, String pre, String pat, Queue<String> q) {
        // 初始判断
        if(x == null) return;
        
        Stack<Node> path = new Stack<Node>();                       // 路径栈
        Stack<Integer> indes = new Stack<Integer>();                // 索引栈
        Stack<Queue<Integer>> links = new Stack<Queue<Integer>>();  // 待探索栈
        
        // 初始化首节点数据
        path.push(x);
        indes.push(-1);
        Queue<Integer> link = new Queue<Integer>();
        for (int i=0; i<R; i++)
            if (x.next[i] != null)
                link.enqueue(i);
        links.push(link);
        
        while(!path.isEmpty()) {
            // 待迭代数据为空时，弹栈
            if (links.peek().isEmpty()) {
                path.pop();
                indes.pop();
                links.pop();
                continue;
            }
            // 出"待探栈首"的队列得到新的索引，压栈
            int index = links.peek().dequeue();
            Node node = path.peek().next[index];
            int d = pre.length() + indes.size() - 1;
            
            // 过滤大于匹配的路径
            if (d < pat.length()) {
                
                path.push(node);
                indes.push(index);
                
                // 当前匹配
                char next = pat.charAt(d);
                if (next == '.' || next == index) {
                    // 全局匹配
                    if (node.val != null && d == pat.length()-1) {
                        String tmp = "";
                        for (Integer ch: indes)
                            tmp = (char)ch.intValue() + tmp;
                        q.enqueue(pre + tmp);
                    }
                    // 新的待探队列
                    link = new Queue<Integer>();
                    for (int i=0; i<R; i++)
                        if (node.next[i] != null)
                            link.enqueue(i);
                    links.push(link);
                    
                } else {
                    // 不符合要求，弹栈
                    path.pop();
                    indes.pop();
                }
            }
            
        }
    }
    
    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }
    
    // 类似get
    private int search(Node x, String s, int d, int length) {
        Node next = x;
        int cnt = 0;
        for (; d<s.length(); d++) {
            // 引导目标节点
            char c = s.charAt(d);
            next = next.next[c];
            // 判断节点
            if (next == null) return cnt;
            if (d == s.length()-1) return cnt;
            cnt++;
        }
        return cnt;
    }
    
    public static void main(String[] args) {
        String[] src = "she sells sea shells by the sea shore".split(" ");
        NonrecursiveTrieST<String> trie = new NonrecursiveTrieST<String>();
        for (int i=0; i<src.length; i++)
            trie.put(src[i], src[i]);
        
        StdOut.println("----------------");
        
        for (int i=0; i<src.length; i++)
            StdOut.println(trie.get(src[i]));
        
        StdOut.println("----------------");
        
        trie.delete("shore");
        StdOut.println(trie.get("shore"));
        StdOut.println(trie.get("shells"));
        StdOut.println(trie.size());
        
        StdOut.println("----------------");
        
        for(String s: trie.keysWithPrefix("se")) 
            StdOut.println(s);
        
        StdOut.println("----------------");
        
        for(String s: trie.keysThatMatch("s.."))
            StdOut.println(s);
        
        StdOut.println("----------------");
        
        StdOut.println(trie.longestPrefixOf("sheet"));
        
    }

}
