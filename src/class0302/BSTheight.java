package class0302;

/**
 * @Description 3.2.6
 *      computes the height of the tree
 * @author Leon
 * @date 2017-03-16 17:46:28
 */
public class BSTheight<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value> {
    
    private int height;
    
    private class HNode extends BSTheight.Node{
//        private Key key;            // key
//        private Value val;          // associated value
//        private HNode left, right;  // links to subtrees
//        private int N;              // # nodes in subtree rooted here
        
        private int H;                // # node's tree height

        public HNode(Key key, Value val, int N, int H) {
            super(key, val, N);
            this.H = H;
        }
    }
    
    @Override
    public void put(Key key, Value val) {
        // Search for key. Update value if
        // found; grow table if new.
        root = put(root, key, val, 0);
        
    }

    private Node put(Node x, Key key, Value val, int h) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val.
        if (x == null) {
            // maintain the tree's height
            if(h+1 > height)
                height = h+1;
            return new HNode(key, val, 1, ++h);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val, ++h);
        else if (cmp > 0)
            x.right = put(x.right, key, val, ++h);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    @Override
    public void delete(Key key) {
        root = delete(root, key);
        height = height(root);
    }

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
        return x;
    }
    
    private Node deleteMin(Node x) {
        if (x.left == null) {
            // right part height minus 1
            optHeight(x.right);
            return x.right;
        }
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    @SuppressWarnings("unchecked")
    private void optHeight(Node root) {
        if(root == null)
            return;
        optHeight((HNode)root.left);
        ((HNode)root).H--;
        optHeight((HNode)root.right);
    }
    
    public int height() {
        return height(root);
    }
    
    private int height(Node x) {
        if (x == null) return 0;
        int left = 1+ height(x.left);
//        StdOut.println(((HNode)x).H);
        int right = 1 + height(x.right);
        if(left > right)
            return left;
        else
            return right;
    }
    
    public int heightSize() {
        return height;
    }
    
    public static void main(String[] args) {
        BSTheight<String, String> bst = new BSTheight<String, String>();
        bst.put("5", "v0");
        bst.put("3", "v1");
        bst.put("7", "v1");
        bst.put("2", "v2");
        bst.put("4", "v2");
        bst.put("6", "v2");
        bst.put("8", "v2");
        bst.put("1", "v3");
        bst.put("10", "v3");
        bst.put("9", "v4");
        
        System.out.println(bst.height());
        System.out.println(bst.heightSize());
        
        bst.delete("10");
        
        System.out.println(bst.height());
        System.out.println(bst.heightSize());
        
        bst.delete("2");
        bst.delete("8");
        
        System.out.println("----------");
        for(Object k: bst.keys()) {
            System.out.println(k.toString());
        }
        System.out.println("----------");
        System.out.println(bst.height());
        System.out.println(bst.heightSize());
    }
    
}
