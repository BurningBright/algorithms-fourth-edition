package class0302;

import rlgs4.Queue;
import rlgs4.Stack;

/**
 * @Description 3.2.36
 *      uses space proportional to the tree height
 * @author Leon
 * @date 2017-03-31 16:22:51
 */
public class NonrecursiveIterator<Key extends Comparable<Key>, Value> extends NonrecursiveOthers<Key, Value> {
    
    public Iterable<Key> keys() {
        return keys(min(), max());
    }
    
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }
    
    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) {
        
        Stack<Node> stack = new Stack<Node>();
        
        // prepare min item
        Node min = x;
        while(min.left != null) {
            stack.push(min);
            min = min.left;
        }
//        stack.push(min);
        
        // first node
//        queue.enqueue(min.key);
//        stack.pop();
        
        while (min != null) {
            int cmplo = lo.compareTo(min.key);
            int cmphi = hi.compareTo(min.key);
            
            if(cmplo <= 0 && cmphi >= 0)
            queue.enqueue(min.key);
//            System.out.println(stack.toString());
            if(min.right != null) {
                
                Node p = min.right;
                while(p.left != null) {
                    stack.push(p);
                    p = p.left;
                }
//                stack.push(min);
                min = p;
            } else {
                // stack top is min's parent
                Node p = stack.size() > 0 ? stack.pop(): null;
                Node ch = min;
                
                while(p != null && ch == p.right) {
                    ch = p;
                    p = stack.size() > 0 ? stack.pop(): null;
                }
//                stack.push(min);
                min = p;
            }
            
        }
        
        
        /*
        for(int i=0; i<x.N; i++) 
            queue.enqueue(select(i));
        */
    }
    
    public static void main(String[] args) {
        NonrecursiveIterator<String, String> bst = new NonrecursiveIterator<String, String>();
        bst.put("5", "v0");
        bst.put("3", "v1");
        bst.put("7", "v1");
        bst.put("2", "v2");
        bst.put("4", "v2");
        bst.put("6", "v2");
        bst.put("8", "v2");
        bst.put("1", "v3");
        bst.put("10", "v4");
        bst.put("9", "v3");
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.print(k.toString() + " ");
        }
        System.out.println("\r\n----------");
        
        for(Object k: bst.keys("2", "8")) {
            System.out.print(k.toString() + " ");
        }
        
        /*
        TreeMap<String, String> tree = new TreeMap<String, String>();
        tree.put("5", "v0");
        tree.put("3", "v1");
        tree.put("7", "v1");
        tree.put("2", "v2");
        tree.put("4", "v2");
        tree.put("6", "v2");
        tree.put("8", "v2");
        tree.put("1", "v3");
        tree.put("10", "v4");
        tree.put("9", "v3");
        
        Iterator<Map.Entry<String, String>> it = tree.entrySet().iterator();
        while(it.hasNext())
            System.out.println(it.next().getKey());
        */
    }

}
