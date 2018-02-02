package class0404;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import class0401.StdDraw;
import class0402.DrawTinyDG;
import rlgs4.Queue;
import stdlib.In;

/**
 * /**
 * @Description 4.4.0
 *          动画 贝尔曼-福特
 *          动画 不检查负环
 *          
 *      灰色节点        未标记节点
 *      白色节点        已标记节点
 *      红色节点        队头节点
 *      
 *      灰色箭头        未探索边
 *      黑箭头         SPT
 *      
 *      虚线箭头        负权边
 *      
 * @author Leon
 * @date 2018-01-26 15:30:00
 */
public class AnimateBellmanFord {
    int V;
    int E;
    
//    double pBigRadius = .015;
    double pNorRadius = .006;    // 画笔大小
    Color grey = new Color(189, 189, 191);
    
    double xSc = 300;           // 缩放比例
    double ySc = 300;
    
    double cRadius = 15;        // 圆半径
    double offset = 5;          // 双向联线偏移量
    
    // 预设坐标
    double[] px = {132, 120, 191, 191, 45, 45, 270, 108};
    double[] py = {153, 80, 130, 89, 185, 101, 185, 122};
    
    Circle[] circles;
    Line[] lines;
    
    // v-w to lines 边到线的映射
    Map<String, Integer> mapping;
    
    EdgeWeightedDigraph G;
    

    public AnimateBellmanFord(EdgeWeightedDigraph G) {

        this.V = G.V();
        this.E = G.E();
        this.G = G;
        
        StdDraw.setFont(new Font("MyriadPro", Font.BOLD, 20));
        StdDraw.setXscale(0, xSc);
        StdDraw.setYscale(ySc, 0);
        StdDraw.setPenRadius(pNorRadius);
        StdDraw.setPenColor(StdDraw.BLACK);
        
        circles = new Circle[V];
        lines = new Line[E];
        mapping = new HashMap<String, Integer> ();
        
        // init circles
        for (int v=0; v<G.V(); v++) 
            circles[v] = new Circle(px[v], py[v]);
        
        // init lines
        int count = 0;
        for (DirectedEdge e: G.edges()) {
            int v = e.from();
            int w = e.to();
            mapping.put(v + "-" + w, count);
            lines[count++] = new Line(v, w, e.weight());
        }
        
        // set tigger
        StdDraw.setOutsideHandle(new Tigger());
        // begin
        StdDraw.enableDoubleBuffering();
        show();
        
        bellmanFord(0);
    }
    
    public void show() {
        // draw lines
        for (int e=0; e<lines.length; e++) {
            Line l = lines[e];
            if (mapping.containsKey(l.w + "-" + l.v))
                DrawTinyDG.drawDirectedEdgeBetweenTwoOffsetCircle(
                        circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                        offset, false, xSc, ySc, cRadius, l.radius, l.color);
            else {
                if (l.weight > 0)
                    DrawTinyDG.drawDirectedEdgeBetweenTwoCircle(
                            circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                            xSc, ySc, cRadius, l.radius, l.color);
                else
                    DrawTinyDG.drawDirectedDashEdgeBetweenTwoCircle(
                            circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                            xSc, ySc, cRadius, l.radius, l.color);
            }
        }
        
        // reset pen radius
        StdDraw.setPenRadius(pNorRadius);
        
        // fill color
        for (int v=0; v<circles.length; v++) {
            StdDraw.setPenColor(circles[v].color);
            StdDraw.filledCircle(circles[v].x, circles[v].y, cRadius);
        }
        
        // draw circles
        for (int v=0; v<circles.length; v++) {
            StdDraw.setPenColor(circles[v].penColor);
            StdDraw.circle(circles[v].x, circles[v].y, cRadius);
            StdDraw.text(circles[v].x, circles[v].y, v+"");
        }
        
        StdDraw.show();
    }
    
    private double[] distTo;                // length of path to v
    private DirectedEdge[] edgeTo;          // last edge on path to v
    private boolean[] onQ;                  // Is this vertex on the queue?
    private Queue<Integer> queue;           // vertices being relaxed
    
    private void bellmanFord(int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        onQ = new boolean[G.V()];
        queue = new Queue<Integer>();
        for (int v = 0; v < G.V(); v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;
        queue.enqueue(s);
        onQ[s] = true;
    }
    
    private void relax(EdgeWeightedDigraph G, int v) {
        // 恢复圆的黑色画笔
        for (int i=0; i<V; i++)
            circles[i].penColor = StdDraw.BLACK;
        // 队列节点圈红
        for (Integer i: queue) {
            circles[i].color = StdDraw.WHITE;
            circles[i].penColor = StdDraw.RED;
        }
        
        circles[v].color = StdDraw.WHITE;
        circles[v].penColor = StdDraw.RED;
        
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            Integer vertex = mapping.get(v + "-" + w);
            Line line = lines[vertex];
            
            if (distTo[w] > distTo[v] + e.weight()) {
                line.color = StdDraw.BLACK;
                
                if (edgeTo[w] != null && edgeTo[w] != e) {
                    vertex = mapping.get(edgeTo[w].from() + "-" + edgeTo[w].to());
                    Line replaced = lines[vertex];
                    replaced.color = StdDraw.GRAY;
                }
                
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                
                if (!onQ[w]) {
                    queue.enqueue(w);
                    onQ[w] = true;
                }
            }
            
//            if (cost++ % G.V() == 0)
//                findNegativeCycle();
        }
    }
    
    class Tigger implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!queue.isEmpty()) {
                StdDraw.clear();
                int v = queue.dequeue();
                onQ[v] = false;
                relax(G, v);
                
                show();
            } else {
                // 恢复圆的黑色画笔
                for (int i=0; i<V; i++)
                    circles[i].penColor = StdDraw.BLACK;
                show();
            }
        }
        
    }
    
    class Line {
        Color color = grey;
        double radius = pNorRadius;
        double weight;
        int v, w;
        public Line(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }
    }
    
    class Circle {
        double x, y;
        Color color = grey;
        Color penColor = StdDraw.BLACK;
        public Circle(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        AnimateBellmanFord ani = new AnimateBellmanFord(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD4.txt")));
    }

}
