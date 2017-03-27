package class0302;
/**
 * @Description 3.2.12
 *      not use node's field
 *      implement select(int) rank(Node)
 * @author Leon
 * @date 2017-03-27 10:07:28
 */
public class BruteSelectRank<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value>{
    
    int nodes;
    
    @Override
    public Key select(int k) {
        nodes = 0;
        Node n = select(root, k);
        return n == null? null: n.key;
    }
    
    private Node select(Node x, int k) {
        
        // base case
        if(x == null) return null;
        
        Node left = select(x.left, k);
        
        // count
        if(k == ++nodes) return x;
        
//        System.out.println(x.key + "  " + nodes);
        
        Node right = select(x.right, k);
        
        // check result
        return left == null? right == null? null: right: left;
    }
    
    @Override
    public int rank(Key key) {
        return rank(key, root);
    }
    
    private int rank(Key key, Node x) {
        
        if(x == null) return 0;
        
        int left = rank(key, x.left);
        
        int cmp = key.compareTo(x.key);
        if(cmp <= 0)
            return left;
        else
            return 1 + left + rank(key, x.right);
        
    }
    
    public static void main(String[] args) {
        BruteSelectRank<String, String> bst = new BruteSelectRank<String, String>();
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
        
        Object n = bst.select(9);
        System.out.println(n == null? "null": n.toString());
        
        System.out.println(bst.rank(bst.select(9)));
    }

}
