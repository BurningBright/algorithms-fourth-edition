package class0303;

import class0302.BSTbase;

/**
 * @Description 3.3.25
 *     红黑结构，自上而下2-3平衡树
 *     S E A R C H X M P L
 *              M
 *          /       \
 *         E         R
 *        /  \      /  \
 *    C = A  H = L P    S = X
 * @author Leon
 * @date 2017-07-13 10:29:13
 */
public class TD234Tree<Key extends Comparable<Key>, Value> 
                                extends BSTbase<Key, Value> {
    
    enum Type{TWO, THREE}
    
    protected class BNode extends Node{
        protected Type color;                // color to parent[null is black]
        protected BNode left;
        protected BNode right;
        public BNode(Key key, Value val, int N, Type color) {
            super(key, val, N);
            this.color = color;
        }
        public String toString() {
            return (color == Type.TWO? "2 ": "3 ") + key + ":" + val +" | "+ N;
        }
    }
    
    boolean isThree(BNode node) {
        if(node == null)
            return false;
        return node.color == Type.THREE;
    }
    
    @SuppressWarnings("unchecked")
    public void put(Key key, Value val) {
        root = put((BNode)root, key, val);
        ((BNode)root).color = Type.TWO;
    }
    
    private BNode put(BNode h, Key key, Value val) {
        // 默认生成3节点
        if(h == null)
            return new BNode(key, val, 1, Type.THREE);
        
        int cmp = key.compareTo(h.key);
        if(cmp > 0)         h.right = put(h.right, key, val);
        else if(cmp < 0)    h.left = put(h.left, key, val);
        else                h.val = val;
        
        if (isThree(h.left) && isThree(h.left.right)) {
            // rotateLeft[h.left]
            h.left = rotateLeft(h.left);
        }
        if (isThree(h.left) && isThree(h.left.left)) {
            // rotateRight
            h = rotateRight(h);
        }
        if (isThree(h.right) && isThree(h.right.left)) {
            // rotateRight[h.right]
            h.right = rotateRight(h.right);
        }
        if (isThree(h.right) && isThree(h.right.right)) {
            // rotateLeft
            h = rotateLeft(h);
        }
        if (isThree(h.right) && isThree(h.left)) {
            // flip color
            flipColor(h);
        }
        
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    BNode rotateLeft(BNode h) {
        BNode x = h.right;
        
        h.right = x.left;
        x.left = h;
        
        x.color = h.color;
        h.color = Type.THREE;
        
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        
        return x;
    }
    
    BNode rotateRight(BNode h) {
        BNode x = h.left;
        
        h.left = x.right;
        x.right = h;
        
        x.color = h.color;
        h.color = Type.THREE;
        
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        
        return x;
    }
    
    void flipColor(BNode h) {
        h.left.color = h.right.color = Type.TWO;
        h.color = Type.THREE;
    }
    
    public static void main(String[] args) {
        TD234Tree<String, Object> tdt = new TD234Tree<String, Object>();
        tdt.put("S", "1");
        tdt.put("E", "2");
        tdt.put("A", "3");
        tdt.put("R", "4");
        tdt.put("C", "5");
        tdt.put("H", "6");
        tdt.put("X", "7");
        tdt.put("M", "8");
        tdt.put("P", "9");
        tdt.put("L", "0");
        System.out.println(tdt);
    }

}
