package class0302;

import class0302.BaseBST.Node;

/**
 * @Description 3.2.6
 * @author Leon
 * @date 2017-03-16 17:46:28
 */
public class HeightBST<Key extends Comparable<Key>, Value> extends BaseBST<Key, Value> {
    
    private class HNode extends HeightBST.Node{
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
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val.
        if (x == null)
            return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, val);
        else if (cmp > 0)
            x.right = put(x.right, key, val);
        else
            x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }
    
    
    
    public static void main(String[] args) {
        HeightBST<String, String> bst = new HeightBST<String, String>();
        bst.put("k1", "v1");
        bst.get("k1");
    }
    
}
