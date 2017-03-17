package class0302;


/**
 * @Description 3.2.6
 *      computes the height of the tree
 * @author Leon
 * @date 2017-03-16 17:46:28
 */
public class HeightBST<Key extends Comparable<Key>, Value> extends BaseBST<Key, Value> {
    
    private int height;
    
    private class HNode extends HeightBST.Node{
//        private Key key;            // key
//        private Value val;          // associated value
//        private HNode left, right;  // links to subtrees
//        private int N;              // # nodes in subtree rooted here
        
        @SuppressWarnings("unused")
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
        HeightBST<String, String> bst = new HeightBST<String, String>();
        bst.put("5", "v0");
        bst.put("3", "v1");
        bst.put("7", "v1");
        bst.put("2", "v2");
        bst.put("4", "v2");
        bst.put("6", "v2");
        bst.put("8", "v2");
        bst.put("1", "v3");
        
        System.out.println(bst.height());
        System.out.println(bst.heightSize());
    }
    
}
