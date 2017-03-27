package class0302;


/**
 * @Description 3.2.7
 *      compute the narrow sense tree's compare times
 * @author Leon
 * @date 2017-03-24 10:12:56
 */
public class BSTcompares<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value> {
    
//    private CNode root;
    
    private class CNode extends Node{
        
        private int CP;                   // # node's tree Compares
        
        public CNode(Key key, Value val, int N, int CP) {
            super(key, val, N);
            this.CP = CP;
        }
        
    }
    
    public int compares() {
        return compares(root, 0);
    }
    
    private int compares(Node node, int height) {
        if (node == null)
            return 0;
        return 1 + height + compares(node.left, height + 1) + compares(node.right, height + 1);
    }
    
    /*****************************************************************/
    
    @SuppressWarnings("unchecked")
    public int comparesSize() {
        return ((CNode)root).CP;
    }
    
    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val, 0);
    }
    
    @SuppressWarnings("unchecked")
    private Node put(Node x, Key key, Value val, int h) {
        if (x == null) {
            return new CNode(key, val, 1, 1);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val, ++h);
        else if (cmp > 0)
            x.right = put(x.right, key, val, ++h);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        ((CNode)x).CP = cmps(x.left) + cmps(x.right) + size(x.left) + size(x.right) + 1;
        return x;
    }
    
    @SuppressWarnings("unchecked")
    private int cmps(Node x) {
        if (x == null)
            return 0;
        else
            return ((CNode)x).CP;
    }
    
    public void delete(Key key) {
        root = delete(root, key);
    }

    @SuppressWarnings("unchecked")
    private Node delete(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = delete(x.left, key);
        else if (cmp > 0)
            x.right = delete(x.right, key);
        else {
            if (x.right == null)
                return x.left;
            if (x.left == null)
                return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        ((CNode)x).CP = cmps(x.left) + cmps(x.right) + size(x.left) + size(x.right) + 1;
        return x;
    }
    
    @SuppressWarnings("unchecked")
    private Node deleteMin(Node x) {
        if (x.left == null)
            return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        ((CNode)x).CP = cmps(x.left) + cmps(x.right) + size(x.left) + size(x.right) + 1;
        return x;
    }
    
    public static void main(String[] args) {
        BSTcompares<String, String> bst = new BSTcompares<String, String>();
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
        
        System.out.println(bst.compares());
        System.out.println(bst.comparesSize());
        
        bst.delete("10");
        
        System.out.println(bst.compares());
        System.out.println(bst.comparesSize());
        
        bst.delete("2");
        bst.delete("8");
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.println(k.toString());
        }
        System.out.println("----------");
        
        System.out.println(bst.compares());
        System.out.println(bst.comparesSize());
        
    }

}
