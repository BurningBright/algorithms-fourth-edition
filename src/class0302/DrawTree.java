package class0302;

import java.awt.Color;
import java.awt.Font;

import rlgs4.Queue;
import rlgs4.Stack;
import stdlib.StdDraw;

/**
 * @Description 3.2.38
 *      draw a tree
 * @author Leon
 * @date 2017-04-05 09:46:39
 */
public class DrawTree <Key extends Comparable<Key>, Value> extends BSTbase<Key, Value> {
    
    // 节点默认参数
    private double circle = .03;
    private double radius = .003;
    private Color color = Color.RED;
    private Font font = new Font("Consolas", Font.BOLD, 20);
    
    private double DISTANCE = .1;
    
    {
//        StdDraw.setCanvasSize(700, 700);
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(radius);
        StdDraw.setFont(font);
//        StdDraw.circle(.5, .5, circle);
//        StdDraw.text(.5, .5, "H");
    }
    
    private class DNode extends Node {
        private double x;
        private double y;
        private DNode parent;
        public DNode(Key key, Value val, DNode parent, int N, double x, double y) {
            super(key, val, N);
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
    
    private class NullObj extends DNode{
        public NullObj(DNode p) {
            super(null, null, p, 0, 0, 0);
        }
    }
    
    private class BlankObj extends DNode{
        public BlankObj() {
            super(null, null, null, 0, 0, 0);
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void put(Key key, Value val) {
        
        if(root == null) {
            root = new DNode(key, val, null, 1, .5, .9);
            return;
        }
        Node x = root;
        Stack<Node> sk = new Stack<Node>();
        
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) {
                x.val = val;
            } else if (cmp < 0) {
                sk.push(x);
                if(x.left == null) {
                    
                    x.left = new DNode(key, val, (DNode)x, 1, 0, 0);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                    
                x = x.left;
            } else if (cmp > 0) {
                sk.push(x);
                if(x.right == null) {
                    
                    x.right = new DNode(key, val, (DNode)x, 1, 0, 0);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                
                x = x.right;
            }
        }
        
    }
    
    public void draw() {
        
        Key min = min();
        Key max = max();
        
        calcCooridate();
        
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
        
        DNode t = (DNode)x;
        StdDraw.circle(t.x, t.y, circle);
        StdDraw.text(t.x, t.y, x.key.toString());
        if(t.parent != null)
            StdDraw.line(t.x, t.y, t.parent.x, t.parent.y);
        
        if (cmphi > 0)
            draw(x.right, lo, hi);
        
    }
    
    @SuppressWarnings("unchecked")
    private void calcCooridate() {
        
        double offsetY = .9;
        
        Queue<? super Node> que = new Queue<Node>();
        que.enqueue(root);
        
        while(que.size() > 0) {
            
            int size = que.size();
            boolean blankFlag = false;
            
            for(int i=size; i > 0; i--) {
                
                Object o = que.dequeue();
//                System.out.println(((Node)o).toString()+" "+size+" "+i);
                
                if(o instanceof DrawTree.NullObj) {
                    que.enqueue(new BlankObj());
                    que.enqueue(new BlankObj());
                } else if(o instanceof DrawTree.BlankObj && blankFlag) {
                    que.enqueue(new BlankObj());
                    que.enqueue(new BlankObj());
                } else if(o instanceof DrawTree.DNode && !(o instanceof DrawTree.BlankObj)){
                    
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
            
            offsetY -= .1;
        }
    }
    
    public static void main(String[] args) {
        DrawTree<String, String> dt = new DrawTree<String, String>();
        dt.put("5", "v0");
        dt.put("3", "v1");
        dt.put("7", "v1");
        dt.put("2", "v2");
        dt.put("4", "v2");
        dt.put("6", "v2");
        dt.put("8", "v2");
        dt.put("1", "v3");
        dt.put("10", "v4");
        dt.put("9", "v3");
        dt.draw();
        
    }

}
