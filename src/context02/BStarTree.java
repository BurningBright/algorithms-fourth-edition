package context02;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description context02.20
 *              B*-tree, 合并兄妹节点节省空间
 * @author Leon
 * @date 2018-05-07 15:00:00
 */
public class BStarTree<Key extends Comparable<Key>, Value> {
    
    private static final int M = 6;
    private static final int MR = 8;    // 根节点容量 4M/3
    
    private Node root;       // root of the B-tree
    private int height;      // height of the B-tree
    private int n;           // number of key-value pairs in the B-tree
    
    // helper B-tree node data type
    private static final class Node {
        private int m;                             // number of children
        private int c;                             // capacity of node
        private Entry[] children;                  // the array of children
        // 兄弟节点
        private Node left, right;
        // create a node with k children 自定义容量， 当前键值个数
        private Node(int c, int k) {
            children = new Entry[c];
            m = k;
            this.c = c;
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(")
            .append(left==null? "": left.children[0].key)
            .append(",")
            .append(right==null? "": right.children[0].key)
            .append(")")
            .append(c).append("-").append(m).append(":");
            for (int i=0; i<m; i++)
                sb.append(children[i].key).append(" ");
            return sb.toString();
        }
    }

    // internal nodes: only use key and next
    // external nodes: only use key and value
    @SuppressWarnings("rawtypes")
    private static class Entry {
        private Comparable key;
        private final Object val;
        
        // helper field to iterate over array entries
        private Node next;
        public Entry(Comparable key, Object val, Node next) {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }
    }
    
    public BStarTree() {
        root = new Node(MR, 0);
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return n;
    }
    
    public int height() {
        return height;
    }
    
    public Value get(Key key) {
        return search(root, key, height);
    }
    
    @SuppressWarnings("unchecked")
    private Value search(Node x, Key key, int ht) {
        Entry[] children = x.children;

        // external node
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (eq(key, children[j].key)) return (Value) children[j].val;
            }
        }

        // internal node
        else {
            for (int j = 0; j < x.m; j++) {
                if (j+1 == x.m || less(key, children[j+1].key))
                    return search(children[j].next, key, ht-1);
            }
        }
        return null;
    }
    
    public void put(Key key, Value val) {
        Node u = insert(root, key, val, height, null, 0); 
        n++;
        if (u == null) return;

        // need to split root
        Node t = new Node(MR, 2);
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
    }
    
    private Node insert(Node h, Key key, Value val, int ht, Node prv, int prvIndex) {
        int j = 0;
        Entry t = new Entry(key, val, null);

        // external node
        if (ht == 0) {
            for (j = 0; j < h.m; j++) 
                if (less(key, h.children[j].key)) 
                    break;
        // internal node
        } else {
            for (j = 0; j < h.m; j++) {
                if ((j+1 == h.m) || less(key, h.children[j+1].key)) {
                    Node u = insert(h.children[j].next, key, val, ht-1, h, j++);
                    // 最左位置可能会出现被分裂节点首元素变化的情况[可能用哨兵更好]
                    if (j-1 == 0) 
                        h.children[j-1].key = h.children[0].next.children[0].key;
                    
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }

        for (int i = h.m; i > j; i--)
            h.children[i] = h.children[i-1];
        h.children[j] = t;
        h.m++;
        
        // combine sibling - base case
        if (h == root) 
            if (h.m < MR) return null;
            else          return split(h);
        
        // 非root节点首先检查兄弟节点
        if (h.m < M) 
            return null;
        else if ((h.left == null || h.left.m == M-1)    // 兄弟节点不存在或将满，不合并直接分裂节点
                && (h.right == null || h.right.m == M-1))
            return split(h);
        else if (h.left != null && h.left.m < M-1) {      // 转移首节点到左兄弟节点
            
            Entry first = h.children[0];
            // 元素前移
            for (int i=0; i<h.m-1; i++)
                h.children[i] = h.children[i+1];
            h.m--;
            
            // 替换父节点引用Key
            prv.children[prvIndex].key = h.children[0].key;
            
            // 放入左兄弟节点
            h.left.children[h.left.m++] = first;
            
        } else if (h.right != null && h.right.m < M-1) {    // 转移新节点到右兄弟节点
            
            Entry last = h.children[h.m-1];
            h.m--;
            // 替换兄弟父节点引用Key
            prv.children[prvIndex+1].key = last.key;
            
            // 元素后移
            for (int i=h.right.m; i>0; i--)
                h.right.children[i] = h.right.children[i-1];
            
            // 放入右兄弟节点
            h.right.children[0] = last;
            h.right.m++;
            
        }
        return null;
    }
    
    private Node split(Node h) {
        Node t = null;
        
        // root split rule
        if (h == root) {
            t = new Node(M, MR/2);
            h.m = MR/2;
            for (int j = 0; j < MR/2; j++)
                t.children[j] = h.children[MR/2+j];
        } else {
            t = new Node(M, M/2);
            h.m = M/2;
            for (int j = 0; j < M/2; j++)
                t.children[j] = h.children[M/2+j];
        }
        // 重建兄弟引用
        if (h.right != null)
            h.right.left = t;
        t.right = h.right;
        t.left = h;
        h.right = t;
        return t;
    }
    
    public String toString() {
        return toString(root);
    }

    private String toString(Node h) {
        StringBuilder sb = new StringBuilder();
        Queue<Node> q = new Queue<Node>();
        q.enqueue(h);
        int prv = 1;
        // 广度分层遍历 B-tree
        while(!q.isEmpty()) {
            Node node = q.dequeue();
            sb.append(node.toString()).append(" | ");
            prv--;
            for (int i=0; i<node.m; i++)
                if (node.children[i].next != null)
                    q.enqueue(node.children[i].next);
            if(prv == 0) {
                sb.append("\r\n");
                prv = q.size();
            }
        }
        return sb.toString();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean less(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) < 0;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private boolean eq(Comparable k1, Comparable k2) {
        return k1.compareTo(k2) == 0;
    }
    
    public static void main(String[] args) {
        String org = "A B C D E F G H I J K L M N O P Q R S T";
//        org = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z a b c d e f g h i j k l m n o p q r s t u v w x y z";
//        org = "z y x w v u t s r q p o n m l k j i h g f e d c b a Z Y X W V U T S R Q P O N M L K J I H G F E D C B A";
        org = "J u U i j P Z R H L O K W V D e M b m d p z Y F r t G N k B I v g A Q c a f C s n x o S X E h y w T l q";
      String[] src = org.split(" ");
      BStarTree<String, String> tree = new BStarTree<String, String>();
      
      for (String s: src) {
          tree.put(s, s);
          StdOut.println(s);
          StdOut.println(tree.toString());
      }
      
      StdOut.println(tree.size());
      StdOut.println(tree.get("K"));
      StdOut.println(tree.get("t"));
      StdOut.println(tree.get("XX"));
    }

}
