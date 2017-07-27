package class0303;

import java.awt.Color;
import java.awt.Font;

import rlgs4.Queue;
import stdlib.StdDraw;

/**
 * @Description 3.3.31
 *      画红黑符号表
 * @author Leon
 * @date 2017-07-13 10:29:13
 */
public class DrawRedBlack  <Key extends Comparable<Key>, Value> 
                                        extends BSTRedBlack<Key, Value> {
    
    // 节点默认参数
    private double circle = .03;
    private double radius = .003;
    
    private double ROOTY = .75;
    private double YSTEP = .1;
    
    private Color color = Color.RED;
    private Font font = new Font("Consolas", Font.BOLD, 20);
    
    {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(radius);
        StdDraw.setFont(font);
    }
    
    private class DNode extends RBNode {
        private double x;
        private double y;
        private DNode parent;
        public DNode(Key key, Value val, int N, Type C, double x, double y) {
            super(key, val, N, C);
            this.x = x;
            this.y = y;
        }
        public DNode(Key key, Value val, int N, Type C, DNode parent, double x, double y) {
            super(key, val, N, C);
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
    

    @SuppressWarnings("unchecked")
    DNode rotateLeft(DNode h) {
        DNode x = (DNode)h.right;
        h.right = x.left;
        
        if (x.left != null) 
            ((DNode)x.left).parent = h;
        
        x.left = h;
        
        x.parent = h.parent;
        h.parent = x;
        
        x.color = h.color;
        h.color = Type.RED;
        
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        
        return x;
    }
    
    @SuppressWarnings("unchecked")
    DNode rotateRight(DNode h) {
        DNode x = (DNode)h.left;
        h.left = x.right;
        
        if (x.right != null) 
            ((DNode)x.right).parent = h;
        
        x.right = h;
        
        x.parent = h.parent;
        h.parent = x;
        
        x.color = h.color;
        h.color = Type.RED;
        
        x.N = h.N;
        h.N = 1 + size(h.left) + size(h.right);
        
        return x;
    }
    
    @SuppressWarnings("unchecked")
    void flipColor(RBNode h) {
        h.color = Type.RED;
        ((RBNode)h.left).color = 
                ((RBNode)h.right).color = Type.BLACK;
    }
    
    private class NullObj extends DNode{
        public NullObj(DNode p) {
            super(null, null, 0, Type.BLACK, p, 0, 0);
        }
    }
    
    private class BlankObj extends DNode{
        public BlankObj() {
            super(null, null, 0, Type.BLACK, null, 0, 0);
        }
    }
    

    @SuppressWarnings("unchecked")
    @Override
    public void put(Key key, Value val) {
        root = put((DNode)root, key, val);
        ((DNode)root).color = Type.BLACK;
    }
    
    @SuppressWarnings("unchecked")
    private DNode put(DNode h, Key key, Value val) {
        
        if(h == null)
            return new DNode(key, val, 1, Type.RED, .0, .0);
        
        int cmp = key.compareTo(h.key);
        // 递归找位，压栈是寻找路、径亦是更新路径
        if(cmp < 0)         {h.left = put((DNode)h.left, key, val); ((DNode)h.left).parent = h;}
        else if(cmp > 0)    {h.right = put((DNode)h.right, key, val); ((DNode)h.right).parent = h;}
        else h.val = val;
        
        // 寻路路上现不平，红黑平乱保社稷
        if(!isRed((RBNode)h.left) && isRed((RBNode)h.right))    h = rotateLeft(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.left.left)) h = rotateRight(h);
        if(isRed((RBNode)h.left) && isRed((RBNode)h.right))     flipColor(h);
        
        h.N = size(h.left) + size(h.right) + 1;
        
        return h;
    }
    
    public void draw() {
        
        Key min = min();
        Key max = max();
        
        calcNodeCooridate();
        
        draw(root, min, max);
    }

    @SuppressWarnings("unchecked")
    private void draw(Node x, Key lo, Key hi) {
        if (x == null)
            return;
        
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0)
            draw(x.left, lo, hi);
        
        // translate
        DNode t = (DNode)x;
        if(!isRed(t)) {
            Color tmpCol = StdDraw.getPenColor();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.circle(t.x, t.y, circle);
            StdDraw.text(t.x, t.y, x.key.toString());
            StdDraw.setPenColor(tmpCol);
        } else {
            StdDraw.circle(t.x, t.y, circle);
            StdDraw.text(t.x, t.y, x.key.toString());
        }
        if(t.parent != null)
                drawLine(((DNode)t.parent).x, ((DNode)t.parent).y, t.x, t.y, isRed(t));
        
        if (cmphi > 0)
            draw(x.right, lo, hi);
        
    }
    
    /** 
     * parent node & child node
     */
    private void drawLine(double xp, double yp, double xc, double yc, boolean isRed) {
        
        double xChild, yChild, xParent, yParent;
        if(xp-xc > 0) {
            double angle = Math.atan((yp-yc)/(xp-xc));
            xChild = xc + Math.cos(angle) * circle;
            yChild = yc + Math.sin(angle) * circle;
            xParent = xp - Math.cos(angle) * circle;
            yParent = yp - Math.sin(angle) * circle;
        } else {
            double angle = Math.atan((yp-yc)/(xc-xp));
            xChild = xc - Math.cos(angle) * circle;
            yChild = yc + Math.sin(angle) * circle;
            xParent = xp + Math.cos(angle) * circle;
            yParent = yp - Math.sin(angle) * circle;
        }
        if(!isRed) {
            Color tmpCol = StdDraw.getPenColor();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.line(xParent, yParent, xChild, yChild);
            StdDraw.setPenColor(tmpCol);
        } else {
            StdDraw.line(xParent, yParent, xChild, yChild);
        }
    }
    
    @SuppressWarnings("unchecked")
    private void calcNodeCooridate() {
        
        double offsetY = ROOTY;
        
        Queue<? super Node> que = new Queue<Node>();
        que.enqueue(root);
        
        while(que.size() > 0) {
            
            int size = que.size();
            boolean blankFlag = false;
            
            for(int i=size; i > 0; i--) {
                
                Object o = que.dequeue();
                
                if(o instanceof DrawRedBlack.NullObj) {
                    que.enqueue(new BlankObj());
                    que.enqueue(new BlankObj());
                } else if(o instanceof DrawRedBlack.BlankObj && blankFlag) {
    //                que.enqueue(new BlankObj());
                    que.enqueue(new BlankObj());
                } else if(o.getClass().getSimpleName().equals(DNode.class.getSimpleName())){
                    
                    blankFlag = true;
                    DNode node = (DNode)o;
                    
                    if(node.left == null)
                        que.enqueue(new NullObj(node));
                    else
                        que.enqueue(node.left);
                    
                    if(node.right == null)
                        que.enqueue(new NullObj(node));
                    else
                        que.enqueue(node.right);
                    
                    node.y = offsetY;
                    
                    if(node.parent == null) {
                        
                        if(root.left != null && root.right != null) 
                            node.x = root.left.N / (double) (root.left.N + root.right.N);
                        else 
                            node.x = .5;
                        
                    } else {
                        
                        /**
                         * calc x cooridate position
                         */
                        node.x = (double) (size - i + 1) / (size + 1);
                        
                    }
                    
                }
                
            }
            
            offsetY -= YSTEP;
        }
    }

}
