package context02;

import rlgs4.Queue;
import stdlib.StdOut;

/**
 * @Description context02.0
 *              集合B-tree
 * @author Leon
 * @date 2018-05-01 14:00:00
 */
public class BTreeSET<Key extends Comparable<Key>> {
    
    private int M = 5;
    private Page<Key> root = new Page<Key>(true, M);

    @SuppressWarnings("unchecked")
    public BTreeSET() {
        add((Key)"*");
    }
    public BTreeSET(Key sentinel) {
        add(sentinel);
    }

    public boolean contains(Key key) {
        return contains(root, key);
    }

    private boolean contains(Page<Key> h, Key key) {
        if (h.isExternal())
            return h.contains(key);
        return contains(h.next(key), key);
    }

    public void add(Key key) {
        add(root, key);
        if (root.isFull()) {
            Page<Key> lefthalf = root;
            Page<Key> righthalf = root.split();
            root = new Page<Key>(false, M);
            root.put(lefthalf);
            root.put(righthalf);
        }
    }

    public void add(Page<Key> h, Key key) {
        if (h.isExternal()) {
            h.put(key);
            return;
        }
        Page<Key> next = h.next(key);
        add(next, key);
        if (next.isFull())
            h.put(next.split());
        next.close();
    }
    
    public int size() {
        Queue<Page<Key>> q = new Queue<Page<Key>>();
        q.enqueue(root);
        int count = 0;
        while(!q.isEmpty()) {
            Page<Key> p = q.dequeue();
            if (p.isExternal())
                count += p.size();
            for (Page<Key> pa: p.pages())
                q.enqueue(pa);
        }
        return count - 1;
    }
    
    public String toString() {
        return toString(root);
    }
    
    private String toString(Page<Key> page) {
        StringBuilder sb = new StringBuilder();
        Queue<Page<Key>> q = new Queue<Page<Key>>();
        q.enqueue(page);
        int prv = 1;
        // 广度分层遍历B-tree
        while(!q.isEmpty()) {
            Page<Key> p = q.dequeue();
            sb.append(p.toString()).append(" | ");
            prv--;
            for (Page<Key> pa: p.pages())
                q.enqueue(pa);
            if(prv == 0) {
                sb.append("\r\n");
                prv = q.size();
            }
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String org = "It was the best of times it was the worst of times" + 
                "it was the age of wisdom it was the age of foolishness" + 
                "it was the epoch of belief it was the epoch of incredulity" + 
                "it was the season of Light it was the season of Darkness" + 
                "it was the spring of hope it was the winter of despair" + 
                "we had everything before us we had nothing before us" + 
                "we were all going direct to Heaven we were all going direct" + 
                "the other way--in short the period was so far like the present" + 
                "period that some of its noisiest authorities insisted on its" + 
                "being received for good or for evil in the superlative degree" + 
                "of comparison only";
//        org = "A B C D E F G H I J K L M N O P Q R S T";
//        org = "B L K R O A I S D M P F E J G Q H T C N";
        String[] src = org.split(" ");
        BTreeSET<String> set = new BTreeSET<String>();
        for (String s: src)
            set.add(s);
        
        StdOut.println(set.contains("we"));
        StdOut.println(set.contains("all"));
        StdOut.println(set.contains("good"));
        StdOut.println(set.contains("bye"));
        
        StdOut.println(set.contains("L"));
        StdOut.println(set.contains("Q"));
        StdOut.println(set.contains("Z"));
        
        StdOut.println(set.toString());
        StdOut.println(set.size());
        
    }
    
}
