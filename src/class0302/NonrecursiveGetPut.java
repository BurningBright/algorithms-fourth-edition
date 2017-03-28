package class0302;

import rlgs4.Stack;

/**
 * @Description 3.2.13
 *      norecursive get/ put
 * @author Leon
 * @date 2017-03-28 17:24:48
 */
public class NonrecursiveGetPut <Key extends Comparable<Key>, Value> extends BSTbase<Key, Value> {
    
    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)
                return x.val;
            else if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
        }
        return null;
    }
    
    public void put(Key key, Value val) {
        //root = put(root, key, val);
        
        if(root == null) {
            root = new Node(key, val, 1);
            return;
        }
        Node x = root;
        Stack<Node> sk = new Stack<Node>();
        
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                x.val = val;
            } else if (cmp < 0) {
                sk.push(x);
                if(x.left == null) {
                    x.left = new Node(key, val, 1);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                    
                x = x.left;
            } else if (cmp > 0) {
                sk.push(x);
                if(x.right == null) {
                    x.right = new Node(key, val, 1);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                
                x = x.right;
            }
        }
        
    }
    
    public static void main(String[] args) {
        NonrecursiveGetPut<String, String> bst = new NonrecursiveGetPut<String, String>();
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
        
        System.out.println(bst.get("10"));
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.println(k.toString());
        }
        System.out.println("----------");
    }

}
