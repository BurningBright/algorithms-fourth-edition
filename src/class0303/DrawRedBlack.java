package class0303;

import java.awt.Color;
import java.awt.Font;

import stdlib.StdDraw;

/**
 * @Description 3.3.0
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
    
    private Color color = Color.BLACK;
    private Font font = new Font("Consolas", Font.BOLD, 20);
    
    {
        StdDraw.setPenColor(color);
        StdDraw.setPenRadius(radius);
        StdDraw.setFont(font);
    }
    
    private class DNode extends RBNode {
        private double x;
        private double y;
        public DNode(Key key, Value val, DNode parent, Type C, int N, double x, double y) {
            super(key, val, N, C);
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
    

    private class NullObj extends DNode{
        public NullObj(DNode p) {
            super(null, null, p, Type.BLACK, 0, 0, 0);
        }
    }
    
    private class BlankObj extends DNode{
        public BlankObj() {
            super(null, null, null, Type.BLACK, 0, 0, 0);
        }
    }
    
    public static void main(String[] args) {
        
    }

}
