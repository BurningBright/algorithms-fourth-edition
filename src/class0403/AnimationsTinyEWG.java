package class0403;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import class0401.StdDraw;
import class0402.DrawTinyDG;
import rlgs4.IndexMinPQ;
import rlgs4.Queue;
import stdlib.In;
import stdlib.StdOut;

/**
 * @Description 4.3.27
 *      最小生成树动画， 书上的例子有点看不懂
 *      灰色节点        未标记节点
 *      白色节点        已标记节点
 *      黑色细边        未探索边
 *      黑色粗边        MST
 *      红色细边        可能的最小权边，权在pq里
 *      红色粗边        当前找到的MST队尾
 *      灰色细边        非MST
 *      
 *      书上的灰色边有点奇怪
 * @author Leon
 * @date 2017-12-27 10:05:00
 */
public class AnimationsTinyEWG {
    
    int V;
    int E;
    
    double pBigRadius = .02;    // 画笔大小
    double pNorRadius = .01;
    Color grey = new Color(189, 189, 191);
    
    double xSc = 300;           // 缩放比例
    double ySc = 300;
    
    double cRadius = 15;
    // 预设坐标
    double[] px = {132, 120, 191, 191, 45, 45, 270, 108};
    double[] py = {153, 80, 130, 89, 185, 101, 185, 122};
    
    Circle[] circles;
    Line[] lines;
    
    // v-w to lines 边到线的映射
    Map<String, Integer> mapping;
    
    public AnimationsTinyEWG(EdgeWeightedGraph G) {
        
        this.V = G.V();
        this.E = G.E();
        
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
        for (Edge e: G.edges()) {
            int v = e.either();
            int w = e.other(v);
            mapping.put(v + "-" + w, count);
            lines[count++] = new Line(v, w, e.weight());
        }
        
        // begin
        StdDraw.enableDoubleBuffering();
        show();
        StdDraw.pause(300);
        
        primMST(G);
    }
    
    public void show() {
        // draw lines
        for (int e=0; e<lines.length; e++) {
            Line l = lines[e];
            DrawTinyDG.drawNodirectedEdgeBetweenTwoCircle(
                    circles[l.v].x, circles[l.v].y, circles[l.w].x, circles[l.w].y, 
                    cRadius, l.radius, l.color);
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
    
    
    
    
    private Edge[] edgeTo;          // shortest edge from tree vertex
    private double[] distTo;        // distTo[w] = edgeTo[w].weight()
    private boolean[] marked;       // true if v on tree
    private IndexMinPQ<Double> pq;  // eligible crossing edges
    private Queue<Line> mst = new Queue<Line>();
    
    private void primMST(EdgeWeightedGraph G) {
        edgeTo = new Edge[V];
        distTo = new double[V];
        marked = new boolean[V];
        for (int v = 0; v < V; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        pq = new IndexMinPQ<Double>(V);
        distTo[0] = 0.0;
        pq.insert(0, 0.0);          // Initialize pq with 0, weight 0.
//        int count = 1;
        while (!pq.isEmpty()) {
//            StdDraw.save("pic/4.3/ani_prim_" + count++ + ".png");
            StdDraw.clear();
            StdDraw.show();
            
            int min = pq.delMin();
            StdOut.println(min);
            visit(G, min);  // Add closest vertex to tree.
            
            show();
            StdDraw.pause(3000);    // delay
        }
//        StdDraw.save("pic/4.3/ani_prim_" + count++ + ".png");
    }

    private void visit(EdgeWeightedGraph G, int v) { // Add v to tree; update
                                                     // data structures.
        marked[v] = true;
        circles[v].color = StdDraw.WHITE;
        for (Edge e : G.adj(v)) {
            int w = e.other(v);
            
            int index = mapping.get(e.either() + "-" + e.other(e.either()));
            Line l = lines[index];
            l.color = StdDraw.RED;          // 邻接的边变红色
            
            
            if (marked[w]) {
                l.color = grey;             // MST也变
                continue;                   // v-w is ineligible.
            }
            if (e.weight() < distTo[w]) {   // Edge e is new best connection from
                                            // tree to w.
                
                // 被更新的边置成灰色
                if (pq.contains(w)) {
                    Edge old = edgeTo[w];
                    Integer i = mapping.get(old.either() + "-" + old.other(old.either()));
                    Line oldL = lines[i];
                    oldL.color = grey;
                }
                
                edgeTo[w] = e;
                distTo[w] = e.weight();
                if (pq.contains(w))
                    pq.changeKey(w, distTo[w]);
                else
                    pq.insert(w, distTo[w]);
            }
//            if (!pq.contains(w))
//                l.color = grey;
        }
        /*
        for (Edge e: edgeTo)
            StdOut.println(e);
        StdOut.println();
        */
        if (!pq.isEmpty()) {
            /*
            for (int i: pq)
                StdOut.print(i + " ");
            StdOut.println();
            */
            int next = pq.minIndex();
            Edge e = edgeTo[next];
            Line l = lines[mapping.get(e.either() + "-" + e.other(e.either()))];
            // 加入最小生成树的边变粗
            l.radius = pBigRadius;
            l.color = StdDraw.RED;
            
            // MST在探索过程中部分变grey，重置回black
            for (Line old: mst)
                old.color = StdDraw.BLACK;
            
            mst.enqueue(l);
        } else {
            for (Line old: mst)
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
        AnimationsTinyEWG ani = new AnimationsTinyEWG(
                new EdgeWeightedGraph(new In("resource/4.3/tinyEWG.txt")));
        
        /*
        StdDraw.setPenRadius(.03);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(.2, .2, .1);
        StdDraw.circle(.6, .6, .1);
        DrawTinyDG.drawNodirectedEdgeBetweenTwoCircle(.2, .2, .6, .6, .1, .03, StdDraw.GRAY);
        */
    }

}
