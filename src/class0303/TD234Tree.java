package class0303;

import java.util.UUID;

import class0302.BSTbase;
import class0303.BSTRedBlack.RBNode;
import class0303.BSTRedBlack.Type;

/**
 * @Description 3.3.25
 *     红黑结构，自上而下2-3平衡树
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
            return (color == Type.TWO? "2 ": "3 ") + key + ":" + val;
        }
    }
    
    boolean isThree(BNode node) {
        if(node == null)
            return false;
        return node.color == Type.THREE;
    }
    
    @SuppressWarnings("unchecked")
    public void put(Key key, Value val) {
        root = put((BNode)root, null, key, val);
    }
    
    @SuppressWarnings("unchecked")
    private BNode put(BNode h, BNode p, Key key, Value val) {
        // 默认生成3节点
        if(h == null)
            return new BNode(key, val, 1, Type.THREE);
        
        int cmp = key.compareTo(h.key);
        
        if(cmp > 0)         h.right = put(h.right, h, key, val);
        else if(cmp < 0)    h.left = put(h.left, h, key, val);
        else                h.val = val;
        
        if(h.left.color == Type.THREE && h.right.color == Type.THREE) {
            h.color = Type.THREE;
            h.left.color = Type.TWO;
            h.right.color = Type.TWO;
        }
        if(h.left.color == Type.THREE && h.left.left.color == Type.THREE) {
            BNode middle = h.left;
            
            if (p.left == h) 
                p.left = middle;
            else
                p.right = middle;
            
            middle.right = h;
        };
        if(h.left.color == Type.THREE && h.left.right.color == Type.THREE);
        if(h.right.color == Type.THREE && h.right.left.color == Type.THREE);
        if(h.right.color == Type.THREE && h.right.right.color == Type.THREE);
        
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    
    public static void main(String[] args) {

    }

}
