package class0302;

import java.awt.Color;
import java.awt.Font;

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
        public DNode(Key key, Value val, int N, double x, double y) {
            super(key, val, N);
            this.x = x;
            this.y = y;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void put(Key key, Value val) {
        
        if(root == null) {
            root = new DNode(key, val, 1, .5, .9);
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
                    
                    // calc node position
                    DNode t = (DNode)x;
                    double tx = t.x - Math.sin(Math.PI/4) * DISTANCE;
                    double ty = t.y - Math.cos(Math.PI/4) * DISTANCE;
                    
                    x.left = new DNode(key, val, 1, tx, ty);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                    
                x = x.left;
            } else if (cmp > 0) {
                sk.push(x);
                if(x.right == null) {
                    
                    // calc node position
                    DNode t = (DNode)x;
                    double tx = t.x + Math.sin(Math.PI/4) * DISTANCE;
                    double ty = t.y - Math.cos(Math.PI/4) * DISTANCE;
                    
                    x.right = new DNode(key, val, 1, tx, ty);
                    while(sk.size() > 0) 
                        sk.pop().N++;
                    
                    return;
                }
                
                x = x.right;
            }
        }
        
    }
    
    public void draw() {
        draw(root, min(), max());
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
        
        if (cmphi > 0)
            draw(x.right, lo, hi);
        
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
