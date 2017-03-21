package class0302;

import java.awt.Color;

import stdlib.StdDraw;

/**
 * @Description 3.2.0
 *      draw a tree
 * @author leon
 * @date 2017-03-20 23:06:13
 */
public class Adjustable2DTree<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value>{
    
    // 起始参考点
    private double refX = 0.5;
    private double refY = 0.5;
    
    // 节点参数
    private double radius = .03;
    private double circle = .003;
    
    public static void main(String[] args) {
        Adjustable2DTree<String, String> a2t = new Adjustable2DTree<String, String>();
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(a2t.circle);
        StdDraw.circle(a2t.refX, a2t.refY, a2t.radius);
    }

}
