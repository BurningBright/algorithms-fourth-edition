package class0302;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import stdlib.StdDraw;

/**
 * @Description 3.2.0
 *      draw a tree
 * @author leon
 * @date 2017-03-20 23:06:13
 */
public class Adjustable2DTree<Key extends Comparable<Key>, Value> extends BSTbase<Key, Value>{
    
    /*
    // 起始参考点
    private double refX = 0.5;
    private double refY = 0.5;
    
    // 节点参数
    private double radius = .03;
    private double circle = .003;
    */
    
    private Queue<Node> que = new LinkedList<Node>();
    
    class NullObj extends Node{

        public NullObj(Key key, Value val, int N) {
            super(key, val, N);
        }
        
    }
    
    public void calcTree() {
        que.add(root);
        
        while(que.size() > 0)
            printTree();
    }
    
    public void printTree() {
        
        for(int i = que.size(); i > 0; i--) {
            Node tmp = que.poll();
            if(tmp.N > 0) {
                
                // 缩进
                for(int j=0; j<tmp.N; j++)
                    System.out.print(" ");
                
                // key
                System.out.print(tmp.key + " ");
                
                if(tmp.left != null) que.add(tmp.left);
                else que.add(new NullObj(null, null, 0));
                
                if(tmp.right != null) que.add(tmp.right);
                else que.add(new NullObj(null, null, 0));
                
            } else if(tmp.N == 0) {
                // null
                System.out.print("x ");
                que.add(new NullObj(null, null, -1));
                que.add(new NullObj(null, null, -1));
            } else {
                // blank
                System.out.print("- ");
            }
        }
        
        System.out.println();
    }
    
    public static void main(String[] args) {
        /*
        Adjustable2DTree<String, String> a2t = new Adjustable2DTree<String, String>();
        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(a2t.circle);
        StdDraw.circle(a2t.refX, a2t.refY, a2t.radius);
        */
        
        Adjustable2DTree<String, String> a2t = new Adjustable2DTree<String, String>();
        a2t.put("5", "v0");
        a2t.put("3", "v1");
        a2t.put("7", "v1");
        a2t.put("2", "v2");
        a2t.put("4", "v2");
        a2t.put("6", "v2");
        a2t.put("8", "v2");
        a2t.put("1", "v3");
        a2t.put("10", "v4");
        a2t.put("9", "v3");
        
        a2t.calcTree();
    }

}
