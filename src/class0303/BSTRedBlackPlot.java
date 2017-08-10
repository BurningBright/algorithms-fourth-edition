package class0303;

/**
 * @Description 3.3.43
 *      Instrument RedBlackBST 
 * @author Leon
 * @date 2017-08-10 14:20:13
 */
public class BSTRedBlackPlot <Key extends Comparable<Key>, Value> {
    Node root;
    enum Type{RED, BLACK}
    
    class Node {
        public Key key;
        public Value val;
        public Node left, right;
        public int N;
        Type color;
        
        public Node(Key key, Value val, int N, Type color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
        public String toString() {
            return (color == Type.RED? "R ": "B ") + key + ":" + val;
        }
    }
    
    boolean isRed(Node node) {
        if(node == null)
            return false;
        return node.color == Type.RED;
    }
    
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null)
            return 0;
        else
            return x.N;
    }
    
    public boolean isEmpty() {
        return root == null || root.N <= 0;
    }
    
    Node rotateLeft(Node h) {
        // 新王座
        Node x = h.right;
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
    
    Node rotateRight(Node h) {
        // 新王座
        Node x = h.left;
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
    
    void flipColor(Node h) {
        Type tmp = h.color;
        h.color = h.left.color;
        h.left.color = h.right.color = tmp;
    }
    
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = Type.BLACK;
    }
    
    private Node put(Node h, Key key, Value val) {
        
        if(h == null)
            return new Node(key, val, 1, Type.RED);
        
        int cmp = key.compareTo(h.key);
        // 递归找位，压栈是寻找路、径亦是更新路径
        if(cmp < 0)         h.left = put(h.left, key, val);
        else if(cmp > 0)    h.right = put(h.right, key, val);
        else h.val = val;
        
        // 寻路路上现不平，红黑平乱保社稷
        if(!isRed(h.left) && isRed(h.right))    h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))     flipColor(h);
        
        h.N = size(h.left) + size(h.right) + 1;
        
        return h;
    }

    private Integer CMP;
    public int put4Cmp(Key key, Value val) {
        CMP = 0;
        root = put4Cmp(root, key, val);
        root.color = Type.BLACK;
        return CMP;
    }
    
    private Node put4Cmp(Node h, Key key, Value val) {
        if(h == null)
            return new Node(key, val, 1, Type.RED);
        
        int cmp = key.compareTo(h.key);
        CMP += 1;
        
        // 递归找位，压栈是寻找路、径亦是更新路径
        if(cmp < 0)         h.left = put4Cmp(h.left, key, val);
        else if(cmp > 0)    h.right = put4Cmp(h.right, key, val);
        else h.val = val;
        
        // 寻路路上现不平，红黑平乱保社稷
        if(!isRed(h.left) && isRed(h.right))    {h = rotateLeft(h); CMP += 1;}
        if(isRed(h.left) && isRed(h.left.left)) {h = rotateRight(h); CMP += 1;}
        if(isRed(h.left) && isRed(h.right))     {flipColor(h); CMP += 1;}
        
        h.N = size(h.left) + size(h.right) + 1;
        
        return h;
    }
    
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null)
            return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            return get(x.left, key);
        else if (cmp > 0)
            return get(x.right, key);
        else
            return x.val;
    }
    
    public void deleteMin() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = Type.RED;
        root = deleteMin(root);
        if(!isEmpty()) 
            root.color = Type.BLACK;
    }
    
    private Node deleteMin(Node h) {
        // base case used to remove most left red node
        if (h.left == null)
            return null;
        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);
        h.left = deleteMin(h.left);
        return balance(h);
    }
    
    private Node moveRedLeft(Node h) {
        flipColor(h);
        if(isRed(h.right.left)) {
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
        }
        return h;
    }
    
    private Node balance(Node h) {
        if(!isRed(h.left) && isRed(h.right))    h = rotateLeft(h);
        if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if(isRed(h.left) && isRed(h.right))     flipColor(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }
    
    public void deleteMax() {
        if (!isRed(root.left) && !isRed(root.right))
            root.color = Type.RED;
        root = deleteMax(root);
        if(!isEmpty()) 
            root.color = Type.BLACK;
    }
    
    private Node deleteMax(Node h) {
        // left is red turn to right
        if (isRed(h.left))
            h = rotateRight(h);
        // base case used to remove most right red node
        if (h.right == null)
            return null;
        if (!isRed(h.right) && !isRed(h.right.right))
            h = moveRedRight(h);
        h.right = deleteMax(h.right);
        return balance(h);
    }
    
    private Node moveRedRight(Node h) {
        flipColor(h);
        if(isRed(h.left.right)) {
//            h.left = rotateLeft(h.left);
            h = rotateRight(h);
        }
        return h;
    }
    
    public void delete(Key key) {
        if (!isRed(root.left) && !isRed(root.right))
                root.color = Type.RED;
            root = delete(root, key);
            if (!isEmpty()) 
                root.color = Type.BLACK;
    }

    private Node delete(Node h, Key key) {
        if (key.compareTo(h.key) < 0) {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = delete(h.left, key);
        } else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (key.compareTo(h.key) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (key.compareTo(h.key) == 0) {
                h.val = get(h.right, min(h.right).key);
                h.key = min(h.right).key;
                h.right = deleteMin(h.right);
            } else
                h.right = delete(h.right, key);
        }
        return balance(h);
    }
    
    public Key min() {
        return min(root).key;
    }
    
    private Node min(Node x) {
        if (x.left == null)
            return x;
        return min(x.left);
    }
    
}
