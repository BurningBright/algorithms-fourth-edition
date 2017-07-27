package class0303;

import class0302.BSTbase;
import class0303.TD234Tree.BNode;

/**
 * @Description 3.3.0
 *      红黑符号表
 *               S
 *             /   \
 *      E  =  O      U
 *    /   \    \     / \
 *   A I = N    Q   T   Y
 * @author Leon
 * @date 2017-07-13 10:29:13
 */
public class BSTRedBlack<Key extends Comparable<Key>, Value> 
                                    extends BSTbase<Key, Value> {
    
    enum Type{RED, BLACK}
    
    protected class RBNode extends Node{
        protected Type color;
        public RBNode(Key key, Value val, int N, Type color) {
            super(key, val, N);
            this.color = color;
        }
        public String toString() {
            return (color == Type.RED? "R ": "B ") + key + ":" + val;
        }
    }
    
    boolean isRed(RBNode node) {
        if(node == null)
            return false;
        return node.color == Type.RED;
    }
    
    @SuppressWarnings("unchecked")
    RBNode rotateLeft(RBNode h) {
        // 新王座
        RBNode x = (RBNode)h.right;
        // 退位接管继承人手下
        h.right = x.left;
        
        // 挂在新王座下
        x.left = h;
        
        // 继承色
        x.color = h.color;
        // 联合节点
        h.color = Type.RED;
        
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        
        return x;
    }
    
    @SuppressWarnings("unchecked")
    RBNode rotateRight(RBNode h) {
        // 新王座
        RBNode x = (RBNode)h.left;
        // 退位接管继承人手下
        h.left = x.right;
        
        // 挂在新王座下
        x.right = h;
        
        // 继承色
        x.color = h.color;
        // 联合节点
        h.color = Type.RED;
        
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        
        return x;
    }
    
    @SuppressWarnings("unchecked")
    void flipColor(RBNode h) {
        Type tmp = h.color;
        h.color = ((RBNode)h.left).color;
        ((RBNode)h.left).color = 
                ((RBNode)h.right).color = tmp;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void put(Key key, Value val) {
        root = put((RBNode)root, key, val);
        ((RBNode)root).color = Type.BLACK;
    }
    
    @SuppressWarnings("unchecked")
    private RBNode put(RBNode h, Key key, Value val) {
        
        if(h == null)
            return new RBNode(key, val, 1, Type.RED);
        
        int cmp = key.compareTo(h.key);
        // 递归找位，压栈是寻找路、径亦是更新路径
        if(cmp < 0)         h.left = put((RBNode)h.left, key, val);
        else if(cmp > 0)    h.right = put((RBNode)h.right, key, val);
        else h.val = val;
        
        // 寻路路上现不平，红黑平乱保社稷
        if(!isRed((RBNode)h.left) && isRed((RBNode)h.right))    h = rotateLeft(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.left.left)) h = rotateRight(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.right))     flipColor(h);
        
        h.N = size(h.left) + size(h.right) + 1;
        
        return h;
    }
    
    @SuppressWarnings("unchecked")
    public void deleteMin() {
        if (!isRed((RBNode)root.left) && !isRed((RBNode)root.right))
            ((RBNode)root).color = Type.RED;
        root = deleteMin((RBNode)root);
        if(root != null && root.N > 0) 
            ((RBNode)root).color = Type.BLACK;
    }
    
    @SuppressWarnings("unchecked")
    private RBNode deleteMin(RBNode h) {
        if (h.left == null)
            return null;
        if (!isRed((RBNode)h.left) && !isRed((RBNode)h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin((RBNode)h.left);
        return balance(h);
    }
    
    @SuppressWarnings("unchecked")
    private RBNode moveRedLeft(RBNode h) {
        flipColor(h);
        if(isRed((RBNode)h.right.left)) {
            h.right = rotateRight((RBNode)h.right);
            h = rotateLeft(h);
        }
        return h;
    }
    
    @SuppressWarnings("unchecked")
    private RBNode balance(RBNode h) {
        if(!isRed((RBNode)h.left) && isRed((RBNode)h.right))    h = rotateLeft(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.left.left)) h = rotateRight(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.right))     flipColor(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    public static void main(String[] args) {
        BSTRedBlack<String, Object> rb = new BSTRedBlack<String, Object>();
        rb.put("E", "1");
        rb.put("A", "2");
        rb.put("S", "3");
        rb.put("Y", "4");
        rb.put("Q", "5");
        rb.put("U", "6");
        rb.put("T", "7");
        rb.put("I", "8");
        rb.put("O", "9");
        rb.put("N", "0");
        
        System.out.println("Finish");
        
    }
    
}
