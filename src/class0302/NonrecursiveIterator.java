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
        
//        boolean flag = false;
        
//        Node x = node;
        stack.push(x);
        
        while (stack.size() > 0) {
//            int cmplo = lo.compareTo(x.key);
//            int cmphi = hi.compareTo(x.key);
            
            // botton nodes
            if(stack.peek().left == null && stack.peek().right == null) {
                queue.enqueue(stack.peek().key);
                stack.pop();
                continue;
            }
            
            if(stack.peek().left != null) {
                stack.push(stack.peek().left);
                continue;
            } else {
                // left is null so right is not null
                queue.enqueue(stack.peek().key);
                stack.pop();
                stack.push(stack.peek().right);
                
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
//        bst.put("2", "v2");
//        bst.put("4", "v2");
//        bst.put("6", "v2");
//        bst.put("8", "v2");
//        bst.put("1", "v3");
//        bst.put("10", "v4");
//        bst.put("9", "v3");
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.println(k.toString());
        }
        System.out.println("----------");
    }

}
