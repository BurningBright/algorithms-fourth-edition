package class0404;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import class0401.StdDraw;
import class0402.DrawTinyDG;
import rlgs4.IndexMinPQ;
import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.4.46
 *      draw Dijkstra
 *      灰色节点        未标记节点
 *      白色节点        已标记节点
 *      黑色细箭头        未探索边
 *      黑色粗箭头        MST
 *      红色细箭头        可能的最小权边，权在pq里
 *      红色粗箭头        当前找到的SPT队尾
 *      灰色箭头        非SPT[被替代边/已探索节点边]
 *      
 * @author Leon
 * @date 2018-01-22 09:53:00
 */
public class AnimateDijkstra {
    int V;
    int E;
    
    double pBigRadius = .015;    // 画笔大小
    double pNorRadius = .006;
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
    
    public AnimateDijkstra(EdgeWeightedDigraph G) {

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
        
        Dijkstra(0);
    }
    
    public void show() {
        // draw lines
        for (int e=0; e<lines.length; e++) {
            Line l = lines[e];
            if(mapping.containsKey(l.w + "-" + l.v))
                DrawTinyDG.drawDirectedEdgeBetweenTwoOffsetCircle(
                        circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                        offset, false, xSc, ySc, cRadius, l.radius, l.color);
            else
                DrawTinyDG.drawDirectedEdgeBetweenTwoCircle(
                        circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                        xSc, ySc, cRadius, l.radius, l.color);
        }
        
        // reset pen radius
        StdDraw.setPenRadius(pNorRadius);
        
        // fill color
        for (int v=0; v<circles.length; v++) {
            StdDraw.setPenColor(circles[v].color);
            StdDraw.filledCircle(circles[v].x, circles[v].y, cRadius);
        }
        
        // draw circles
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int v=0; v<circles.length; v++) {
            StdDraw.circle(circles[v].x, circles[v].y, cRadius);
            StdDraw.text(circles[v].x, circles[v].y, v+"");
        }
        
        StdDraw.show();
    }
    
    
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;
    private Queue<Line> spt = new Queue<Line>();
    
    private void Dijkstra(int s) {
        edgeTo = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        
        pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) 
            distTo[v] = Double.POSITIVE_INFINITY;
        
        distTo[s] = 0.0;
        
        pq.insert(s, 0.0);
        /*
        while (!pq.isEmpty()) {
            int min = pq.delMin();
            StdOut.println(min);
            relax(G, min);
            
            show();
            StdDraw.pause(3000);
        }
        */
    }
    
    class Tigger implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (!pq.isEmpty()) {
                StdDraw.clear();
                int min = pq.delMin();
                StdOut.println(min);
                relax(G, min);
                
                show();
            }
        }
        
    }
    
    private void relax(EdgeWeightedDigraph G, int v) {
        circles[v].color = StdDraw.WHITE;
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            
            int index = mapping.get(v + "-" + w);
            Line l = lines[index];
            l.color = StdDraw.RED;          // 邻接的边变红色
            
            if (distTo[w] > distTo[v] + e.weight()) {
                
                // 被更新的边置成灰色
                if (pq.contains(w)) {
                    DirectedEdge old = edgeTo[w];
                    Integer i = mapping.get(old.from() + "-" + old.to());
                    Line oldL = lines[i];
                    oldL.color = grey;
                }
                
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            } else {
                l.color = grey;
            }
        }
        
        if (!pq.isEmpty()) {
            int next = pq.minIndex();
            DirectedEdge e = edgeTo[next];
            
            Line l = lines[mapping.get(e.from() + "-" + e.to())];
            // 加入最小生成树的边变粗
            l.radius = pBigRadius;
            l.color = StdDraw.RED;
            
            // SPT在探索过程中部分变grey，重置回black
            for (Line old: spt)
                old.color = StdDraw.BLACK;
            
            spt.enqueue(l);
        } else {
            for (Line old: spt)
                old.color = StdDraw.BLACK;
        }
    }
    
    class Line {
        Color color = StdDraw.BLACK;
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
        public Circle(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public static void main(String[] args) {
        @SuppressWarnings("unused")
        AnimateDijkstra ani = new AnimateDijkstra(
                new EdgeWeightedDigraph(new In("resource/4.4/tinyEWD.txt")));
    }

}
